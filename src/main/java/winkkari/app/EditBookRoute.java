package winkkari.app;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import winkkari.data.BookTip;
import winkkari.data.TipDAO;

import java.util.Map;

public class EditBookRoute implements PageRoute {
    private final TipDAO<BookTip> bookTipDAO;

    public EditBookRoute(TipDAO<BookTip> bookTipDAO) {
        this.bookTipDAO = bookTipDAO;
    }

    @Override
    public ModelAndView get(Request req, Response res) {
        return new ModelAndView(Map.ofEntries(
            Map.entry("tip", bookTipDAO.get(req.params(":id")).get())
        ), "editbook");
    }

    @Override
    public Object post(Request req, Response res) {
        final String title = req.queryParams("title");
        final String author = req.queryParams("author");
        final String check = req.queryParams("checked");
        boolean checked = Boolean.parseBoolean(check);
        bookTipDAO.update(new BookTip(req.params(":id"), title, author, "", checked));
        res.redirect("/list");
        return res;
    }
}
