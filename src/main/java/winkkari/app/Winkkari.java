package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import winkkari.data.*;
import winkkari.data.Tip.Type;

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

        Spark.get("/newbook", (req, res) -> new ModelAndView(Map.ofEntries(
                // nothing
        ), "newbook"), new ThymeleafTemplateEngine());

        Spark.get("/editbook/:id", (req, res) -> new ModelAndView(Map.ofEntries(
            Map.entry("tip", genericDAO.get(Type.BOOK, req.params(":id")).get())
        ), "editbook"), new ThymeleafTemplateEngine());

        Spark.get("/editlink/:id", (req, res) -> new ModelAndView(Map.ofEntries(
            Map.entry("tip", genericDAO.get(Type.LINK, req.params(":id")).get())
        ), "editlink"), new ThymeleafTemplateEngine());

        Spark.get("/editvideo/:id", (req, res) -> new ModelAndView(Map.ofEntries(
            Map.entry("tip", genericDAO.get(Type.VIDEO, req.params(":id")).get())
        ), "editvideo"), new ThymeleafTemplateEngine());

        Spark.post("/api/tip/newbook", (req, res) -> {
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

        Spark.get("/newlink", (req, res) -> new ModelAndView(Map.ofEntries(
                // nothing
        ), "newlink"), new ThymeleafTemplateEngine());

        Spark.post("/api/tip/newlink", (req, res) -> {
            final String url = req.queryParams("url");
            final String title = req.queryParams("title");
            final String comment = req.queryParams("comment");

            if (title == null || title.isEmpty()) {
                LOG.warn("Error adding a new tip, title was null or empty!");
                res.redirect("/list");
                return res;
            }

            if (url == null || url.isEmpty()) {
                LOG.warn("Error adding a new tip, URL was null or empty!");
                res.redirect("/list");
                return res;
            }

            if (comment == null || comment.isEmpty()) {
                LOG.warn("Error adding a new tip, comment was null or empty!");
                res.redirect("/list");
                return res;
            }

            linkTipDAO.add(new LinkTip(title,url,comment));
            res.redirect("/list");
            return res;
        });

        Spark.get("/newvideo", (req, res) -> new ModelAndView(Map.ofEntries(
                // nothing
        ), "newvideo"), new ThymeleafTemplateEngine());

        Spark.post("/api/tip/newvideo", (req, res) -> {
            final String url = req.queryParams("url");
            final String title = req.queryParams("title");
            final String comment = req.queryParams("comment");

            if (title == null || title.isEmpty()) {
                LOG.warn("Error adding a new tip, title was null or empty!");
                res.redirect("/list");
                return res;
            }

            if (url == null || url.isEmpty()) {
                LOG.warn("Error adding a new tip, URL was null or empty!");
                res.redirect("/list");
                return res;
            }

            if (comment == null || comment.isEmpty()) {
                LOG.warn("Error adding a new tip, comment was null or empty!");
                res.redirect("/list");
                return res;
            }

            videoTipDAO.add(new VideoTip(title,url,comment));
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

        Spark.post("/api/tip/edit/:id", (req, res) -> {
            String title = req.queryParams("title");
            String author = req.queryParams("author");
            String check = req.queryParams("checked");
            boolean checked = Boolean.parseBoolean(check);
            genericDAO.update(Type.BOOK, new BookTip(req.params(":id"), title, author, "", checked));
            res.redirect("/list");
            return res;
        });

        Spark.post("/api/tip/editbook/:id", (req, res) -> {
            String title = req.queryParams("title");
            String author = req.queryParams("author");
            String check = req.queryParams("checked");
            boolean checked = Boolean.parseBoolean(check);
            genericDAO.update(Type.BOOK, new BookTip(req.params(":id"), title, author, "", checked));
            res.redirect("/list");
            return res;
        });

        Spark.post("/api/tip/editlink/:id", (req, res) -> {
            String title = req.queryParams("title");
            String url = req.queryParams("url");
            String comment = req.queryParams("comment");
            String check = req.queryParams("checked");
            boolean checked = Boolean.parseBoolean(check);
            genericDAO.update(Type.LINK, new LinkTip(req.params(":id"), title, url, comment, checked));
            res.redirect("/list");
            return res;
        });

        Spark.post("/api/tip/editvideo/:id", (req, res) -> {
            String title = req.queryParams("title");
            String url = req.queryParams("url");
            String comment = req.queryParams("comment");
            String check = req.queryParams("checked");
            boolean checked = Boolean.parseBoolean(check);
            genericDAO.update(Type.VIDEO, new VideoTip(req.params(":id"), title, url, comment, checked));
            res.redirect("/list");
            return res;
        });

        LOG.info("Winkkari initialization finished.");

        // Block until Spark is finished
        Spark.awaitStop();
    }
}
