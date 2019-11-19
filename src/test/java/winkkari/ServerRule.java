package winkkari;

import org.junit.rules.ExternalResource;
import spark.Spark;
import winkkari.app.Winkkari;
import winkkari.data.TipDAO;

public class ServerRule extends ExternalResource {
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        Spark.port(port);

        TipDAO tipDAO = new TestTipDAO();
        Winkkari winkkari = new Winkkari(tipDAO);
        winkkari.run();
    }

    @Override
    protected void after() {
        Spark.stop();
    }

}
