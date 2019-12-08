package winkkari.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winkkari.app.Winkkari;
import winkkari.data.BookDatabaseDAO;
import winkkari.data.LinkDatabaseDAO;
import winkkari.data.VideoDatabaseDAO;
import winkkari.services.BookInfo;
import winkkari.services.ISBNScanner;
import winkkari.services.URLScanner;
import winkkari.services.ISBNSearchService;
import winkkari.services.URLSearchService;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.trace("Starting...");
        final var bookDatabaseDAO = new BookDatabaseDAO();
        final var linkDatabaseDAO = new LinkDatabaseDAO();
        final var videoDatabaseDAO = new VideoDatabaseDAO();
        final ISBNScanner isbnSearch = new ISBNScanner();
        final URLSearchService urlSearch = new URLScanner();
        final var winkkari = new Winkkari(bookDatabaseDAO, linkDatabaseDAO, videoDatabaseDAO, isbnSearch, urlSearch);

        LOG.trace("Running...");
        winkkari.run();
        LOG.trace("Finished.");
    }
}
