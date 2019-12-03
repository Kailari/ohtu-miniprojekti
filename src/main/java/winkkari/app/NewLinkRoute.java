package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.LinkTip;
import winkkari.data.TipDAO;

import java.util.Map;

public class NewLinkRoute implements PageRoute {
    private static final Logger LOG = LoggerFactory.getLogger(NewLinkRoute.class);

    private final TipDAO<LinkTip> linkTipDAO;

    public NewLinkRoute(TipDAO<LinkTip> linkTipDAO) {
        this.linkTipDAO = linkTipDAO;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.ofEntries(
                // nothing
        ), "newlink");
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

        linkTipDAO.add(new LinkTip(title, url, comment));
        res.redirect("/list");
        return res;
    }
}
