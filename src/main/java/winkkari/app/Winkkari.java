package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import winkkari.data.*;
import winkkari.services.ISBNSearchService;

import java.util.Map;
import java.util.Optional;

public class Winkkari {
    private static final Logger LOG = LoggerFactory.getLogger(Winkkari.class);

    private final TipDAO<BookTip> bookTipDAO;
    private final TipDAO<LinkTip> linkTipDAO;
    private final TipDAO<VideoTip> videoTipDAO;
    private final AllTipsDAO genericDAO;
    private ISBNSearchService isbnSearch;

    public Winkkari(
            TipDAO<BookTip> bookTipDAO,
            TipDAO<LinkTip> linkTipDAO,
            TipDAO<VideoTip> videoTipDAO,
            ISBNSearchService isbnSearch
    ) {
        this.isbnSearch = isbnSearch;
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

        var engine = new ThymeleafTemplateEngine();
        Spark.get("/", (req, res) -> new ModelAndView(Map.ofEntries(
                // nothing
        ), "index"), engine);

        var listPage = new ListPage(this.genericDAO);
        Spark.get("/list", listPage::get, engine);

        var newBook = new NewBookRoute(this.bookTipDAO, this.isbnSearch);
        Spark.get("/newbook", newBook::get, engine);
        Spark.post("/api/tip/newbook", newBook::post);

        var newLink = new NewLinkRoute(this.linkTipDAO);
        Spark.get("/newlink", newLink::get, engine);
        Spark.post("/api/tip/newlink", newLink::post);

        var newVideo = new NewVideoRoute(this.videoTipDAO);
        Spark.get("/newvideo", newVideo::get, engine);
        Spark.post("/api/tip/newvideo", newVideo::post);

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
