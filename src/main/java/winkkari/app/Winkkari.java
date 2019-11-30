package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import winkkari.data.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Winkkari {
    private static final Logger LOG = LoggerFactory.getLogger(Winkkari.class);

    private final TipDAO<BookTip> bookTipDAO;
    private final TipDAO<LinkTip> linkTipDAO;
    private final TipDAO<VideoTip> videoTipDAO;
    private final AllTipsDAO genericDAO;

    public Winkkari(
            TipDAO<BookTip> bookTipDAO,
            TipDAO<LinkTip> linkTipDAO,
            TipDAO<VideoTip> videoTipDAO
    ) {
        this.bookTipDAO = bookTipDAO;
        this.linkTipDAO = linkTipDAO;
        this.videoTipDAO = videoTipDAO;
        this.genericDAO = new AllTipsDAO(bookTipDAO, linkTipDAO, videoTipDAO);
    }

    public void run() {
        LOG.info("Winkkari booting up...");

        Optional.ofNullable(System.getenv("PORT"))
                .map(Integer::parseInt)
                .ifPresent(Spark::port);

        Spark.staticFiles.location("/public");

        Spark.get("/", (req, res) -> new ModelAndView(Map.ofEntries(
                // nothing
        ), "index"), new ThymeleafTemplateEngine());

        Spark.get("/list", (req, res) -> new ModelAndView(Map.ofEntries(
                Map.entry("tips", genericDAO.getAll())
        ), "list"), new ThymeleafTemplateEngine());

        Spark.get("/list/", (req, res) -> {
            String searchStr = req.queryParams("search");

            if (searchStr.equals("all")) return new ModelAndView(Map.ofEntries(
                    Map.entry("tips", genericDAO.getAll())
            ), "list");

            return new ModelAndView(Map.ofEntries(
                    Map.entry("tips", genericDAO.getAll()
                            .stream()
                            .filter(x -> x.getType() == Tip.Type.valueOf(searchStr))
                            .collect(Collectors.toList()))), "list");
        }, new ThymeleafTemplateEngine());

        Spark.get("/new", (req, res) -> new ModelAndView(Map.ofEntries(
                // nothing
        ), "new"), new ThymeleafTemplateEngine());


        Spark.post("/api/tip/new", (req, res) -> {
            final String author = req.queryParams("author");
            final String title = req.queryParams("title");

            if (author == null || author.isEmpty()) {
                LOG.warn("Error adding a new tip, author was null or empty!");
                res.redirect("/list");
                return res;
            }

            if (title == null || title.isEmpty()) {
                LOG.warn("Error adding a new tip, title was null or empty!");
                res.redirect("/list");
                return res;
            }

            // TODO: ISBN
            bookTipDAO.add(new BookTip(title, author, ""));

            res.redirect("/list");
            return res;
        });

        Spark.post("/api/tip/delete/:id", (req, res) -> {
            genericDAO.delete(Tip.Type.valueOf(req.queryParams("type")), req.params(":id"));
            res.redirect("/list");
            return res;
        });

        Spark.post("/api/tip/check/:id", (req, res) -> {
            String check = req.queryParams("check");
            boolean checked = Boolean.parseBoolean(check);
            LOG.info("Checking: {} ({})", check, checked);
            genericDAO.check(Tip.Type.valueOf(req.queryParams("type")), req.params(":id"), checked);
            res.redirect("/list");
            return res;
        });

        LOG.info("Winkkari initialization finished.");

        // Block until Spark is finished
        Spark.awaitStop();
    }
}
