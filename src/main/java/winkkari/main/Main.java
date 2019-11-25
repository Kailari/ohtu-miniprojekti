package winkkari.main;

import spark.Spark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winkkari.app.Winkkari;
import winkkari.data.DatabaseDAO;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        LOG.trace("Starting...");
        final var dbDAO = new DatabaseDAO();
        final var winkkari = new Winkkari(dbDAO);

        LOG.trace("Running...");
        winkkari.run();
        LOG.trace("Finished.");
    }
}
