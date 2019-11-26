package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import winkkari.data.Tip;
import winkkari.data.TipDAO;

import java.util.Map;
import java.util.Optional;

public class Winkkari {
    private static final Logger LOG = LoggerFactory.getLogger(Winkkari.class);

    private final TipDAO tipDAO;

    public Winkkari(TipDAO tipDAO) {
        this.tipDAO = tipDAO;
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
                Map.entry("tips", tipDAO.getAll())
        ), "list"), new ThymeleafTemplateEngine());

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

            tipDAO.add(new Tip(title, author));

            res.redirect("/list");
            return res;
        });

        Spark.post("/api/tip/delete/:id", (req, res) -> {
            tipDAO.delete(req.params(":id"));
            res.redirect("/list");
            return res;
        });

        LOG.info("Winkkari initialization finished.");

        // Block until Spark is finished
        Spark.awaitStop();
    }
}
