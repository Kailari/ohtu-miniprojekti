package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.VideoTip;
import winkkari.data.TipDAO;

import java.util.Map;

public class EditVideoRoute implements PageRoute {
    private final TipDAO<VideoTip> videoTipDAO;

    public EditVideoRoute(TipDAO<VideoTip> videoTipDAO) {
        this.videoTipDAO = videoTipDAO;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.ofEntries(
            Map.entry("tip", videoTipDAO.get(req.params(":id")).get())
        ), "editvideo");
    }

    @Override
    public Object post(Request req, Response res) {
        final String title = req.queryParams("title");
        final String url = req.queryParams("url");
        final String comment = req.queryParams("comment");
        final String check = req.queryParams("checked");
        boolean checked = Boolean.parseBoolean(check);
        videoTipDAO.update(new VideoTip(req.params(":id"), title, url, comment, checked));
        res.redirect("/list");
        return res;
    }
}
