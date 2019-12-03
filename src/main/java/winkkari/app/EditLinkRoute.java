package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.LinkTip;
import winkkari.data.TipDAO;

import java.util.Map;

public class EditLinkRoute implements PageRoute {
    private final TipDAO<LinkTip> linkTipDAO;

    public EditLinkRoute(TipDAO<LinkTip> linkTipDAO) {
        this.linkTipDAO = linkTipDAO;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.ofEntries(
            Map.entry("tip", linkTipDAO.get(req.params(":id")).get())
        ), "editlink");
    }

    @Override
    public Object post(Request req, Response res) {
        final String title = req.queryParams("title");
        final String url = req.queryParams("url");
        final String comment = req.queryParams("comment");
        final String check = req.queryParams("checked");
        boolean checked = Boolean.parseBoolean(check);
        linkTipDAO.update(new LinkTip(req.params(":id"), title, url, comment, checked));
        res.redirect("/list");
        return res;
    }
}
