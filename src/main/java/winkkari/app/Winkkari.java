package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import winkkari.data.Tip;
import winkkari.data.TipDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
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

        Optional.ofNullable(System.getProperty("PORT"))
                .map(Integer::parseInt)
                .ifPresent(Spark::port);

        Spark.staticFiles.location("/public");

        Spark.get("/", (req, res) -> new ModelAndView(Map.ofEntries(
                Map.entry("message", "Cats are dogs")
        ), "index"), new ThymeleafTemplateEngine());

        Spark.get("/list", (req, res) -> new ModelAndView(Map.ofEntries(
                Map.entry("tips", tipDAO.getAll())
        ), "list"), new ThymeleafTemplateEngine());

/*         Spark.get("/database", (req, res) -> {
            Connection conn = DriverManager.getConnection("jdbc:h2:~/winkkari", "", "");
            conn.prepareStatement("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(24));");
            conn.close();
            Map<String,String> map = new HashMap<>();
            map.put("test", "asd");
            return new ModelAndView(map, "database");
        }, new ThymeleafTemplateEngine()); */

        LOG.info("Winkkari initialization finished.");
    }
}
