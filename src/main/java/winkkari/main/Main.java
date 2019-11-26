package winkkari.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winkkari.app.Winkkari;
import winkkari.data.DatabaseDAO;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.trace("Starting...");
        final var dbDAO = new DatabaseDAO();
        final var winkkari = new Winkkari(dbDAO);

        LOG.trace("Running...");
        winkkari.run();
        LOG.trace("Finished.");
    }
}
