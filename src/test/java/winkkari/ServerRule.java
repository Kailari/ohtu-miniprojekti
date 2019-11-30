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

public class ServerRule extends ExternalResource {
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() {
        Spark.port(port);

        var bookTipDAO = new TestBookTipDAO();
        bookTipDAO.add(new BookTip("This is a BookTip", "Test Author", "1234567891011"));
        var linkTipDAO = new TestLinkTipDAO();
        linkTipDAO.add(new LinkTip("This is a LinkTip", "http://www.thishopefullypointsnowhere.com", "WOW! --> CLICK THIS <--"));
        var videoTipDAO = new TestVideoTipDAO();
        videoTipDAO.add(new VideoTip("This is a VideoTip", "https://youtu.be/dQw4w9WgXcQ", "A comment"));
        Winkkari winkkari = new Winkkari(bookTipDAO, linkTipDAO, videoTipDAO);
        winkkari.run();
    }

    @Override
    protected void after() {
        Spark.stop();
    }

}
