package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.LinkTip;
import winkkari.data.TipDAO;
import winkkari.services.URLInfo;
import winkkari.services.URLSearchService;

import java.util.Map;
import java.util.Optional;

public class NewLinkRoute implements PageRoute {
    private static final Logger LOG = LoggerFactory.getLogger(NewLinkRoute.class);

    private final TipDAO<LinkTip> linkTipDAO;
    private final URLSearchService urlSearch;

    public NewLinkRoute(TipDAO<LinkTip> linkTipDAO, URLSearchService urlSearch) {
        this.linkTipDAO = linkTipDAO;
        this.urlSearch = urlSearch;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        URLInfo defaultValues = Optional.ofNullable(req.queryParams("urlSearch"))
                                        .map(urlSearch::find)
                                        .filter(Optional::isPresent)
                                        .map(Optional::get)
                                        .orElseGet(() -> new URLInfo("", "", ""));

        return new ModelAndView(Map.ofEntries(
                Map.entry("url", defaultValues.getUrl()),
                Map.entry("title", defaultValues.getTitle()),
                Map.entry("comment", defaultValues.getDescription())
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
