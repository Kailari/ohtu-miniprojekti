package winkkari.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.BookTip;
import winkkari.data.TipDAO;
import winkkari.services.BookInfo;
import winkkari.services.ISBNSearchService;

import java.util.Map;
import java.util.Optional;

public class NewBookRoute implements PageRoute {
    private static final Logger LOG = LoggerFactory.getLogger(NewBookRoute.class);

    private final TipDAO<BookTip> bookTipDAO;
    private final ISBNSearchService isbnSearch;

    public NewBookRoute(TipDAO<BookTip> bookTipDAO, ISBNSearchService isbnSearch) {
        this.bookTipDAO = bookTipDAO;
        this.isbnSearch = isbnSearch;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        BookInfo defaultValues = Optional.ofNullable(req.queryParams("isbnSearch"))
                                         .map(isbnSearch::find)
                                         .filter(Optional::isPresent)
                                         .map(Optional::get)
                                         .orElseGet(() -> new BookInfo("", "", ""));

        return new ModelAndView(Map.ofEntries(
                Map.entry("isbn", defaultValues.getIsbn()),
                Map.entry("author", defaultValues.getAuthor()),
                Map.entry("title", defaultValues.getTitle())
        ), "newbook");
    }

    @Override
    public Object post(Request req, Response res) {
        final String author = req.queryParams("author");
        final String title = req.queryParams("title");

        if (author == null || author.isEmpty()) {
            LOG.warn("Error adding a new tip, author was null or empty!");
            res.redirect("/list");
            return res;
        }

        if (title == null || title.isEmpty()) {
            LOG.warn("Error adding a new tip, title was null or empty!");
            res.redirect("/list");
            return res;
        }

        // TODO: ISBN
        NewBookRoute.this.bookTipDAO.add(new BookTip(title, author, ""));

        res.redirect("/list");
        return res;
    }
}
