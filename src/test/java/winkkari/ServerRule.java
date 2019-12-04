package winkkari;

import org.junit.rules.ExternalResource;
import spark.Spark;
import winkkari.app.Winkkari;
import winkkari.data.BookTip;
import winkkari.data.LinkTip;
import winkkari.data.VideoTip;
import winkkari.mock.TestBookTipDAO;
import winkkari.mock.TestLinkTipDAO;
import winkkari.mock.TestVideoTipDAO;
import winkkari.services.BookInfo;
import winkkari.services.ISBNSearchService;
import winkkari.services.URLInfo;
import winkkari.services.URLSearchService;

import java.util.Optional;

public class ServerRule extends ExternalResource {
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() {
        Spark.port(port);

        final var bookTipDAO = new TestBookTipDAO();
        bookTipDAO.add(new BookTip("This is a BookTip", "Test Author", "1234567891011"));
        final var linkTipDAO = new TestLinkTipDAO();
        linkTipDAO.add(new LinkTip("This is a LinkTip", "http://www.thishopefullypointsnowhere.com", "WOW! --> CLICK THIS <--"));
        final var videoTipDAO = new TestVideoTipDAO();
        videoTipDAO.add(new VideoTip("This is a VideoTip", "https://youtu.be/dQw4w9WgXcQ", "A comment"));

        final ISBNSearchService testSearchService = isbn -> Optional.of(new BookInfo(isbn, "Test Author", "Test Title"));
        final URLSearchService urlSearchService = url -> Optional.of(new URLInfo(url, "Test Title", "Test Description"));
        Winkkari winkkari = new Winkkari(bookTipDAO, linkTipDAO, videoTipDAO, testSearchService, urlSearchService);
        winkkari.run();
    }

    @Override
    protected void after() {
        Spark.stop();
    }
}
