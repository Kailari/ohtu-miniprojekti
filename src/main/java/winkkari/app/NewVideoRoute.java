package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.TipDAO;
import winkkari.data.VideoTip;

import java.util.Map;

public class NewVideoRoute implements PageRoute {
    private static final Logger LOG = LoggerFactory.getLogger(NewVideoRoute.class);

    private final TipDAO<VideoTip> videoTipDAO;

    public NewVideoRoute(TipDAO<VideoTip> videoTipDao) {
        this.videoTipDAO = videoTipDao;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.ofEntries(
                // nothing
        ), "newvideo");
    }

    @Override
    public Object post(Request req, Response res) {
        final String url = req.queryParams("url");
        final String title = req.queryParams("title");
        final String comment = req.queryParams("comment");

        if (title == null || title.isEmpty()) {
            LOG.warn("Error adding a new tip, title was null or empty!");
            res.redirect("/list");
            return res;
        }

        if (url == null || url.isEmpty()) {
            LOG.warn("Error adding a new tip, URL was null or empty!");
            res.redirect("/list");
            return res;
        }

        if (comment == null || comment.isEmpty()) {
            LOG.warn("Error adding a new tip, comment was null or empty!");
            res.redirect("/list");
            return res;
        }

        videoTipDAO.add(new VideoTip(title, url, comment));
        res.redirect("/list");
        return res;
    }
}
