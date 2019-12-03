package winkkari.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winkkari.app.Winkkari;
import winkkari.data.BookDatabaseDAO;
import winkkari.data.LinkDatabaseDAO;
import winkkari.data.VideoDatabaseDAO;
import winkkari.services.BookInfo;
import winkkari.services.ISBNSearchService;

import java.util.Optional;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.trace("Starting...");
        final var bookDatabaseDAO = new BookDatabaseDAO();
        final var linkDatabaseDAO = new LinkDatabaseDAO();
        final var videoDatabaseDAO = new VideoDatabaseDAO();
        final ISBNSearchService isbnSearch = isbn -> Optional.of(new BookInfo("1234567890123", "Stub Author", "Stub Title"));
        final var winkkari = new Winkkari(bookDatabaseDAO, linkDatabaseDAO, videoDatabaseDAO, isbnSearch);

        LOG.trace("Running...");
        winkkari.run();
        LOG.trace("Finished.");
    }
}
