package winkkari.mock;

import winkkari.data.BookTip;
import winkkari.data.TipDAO;

import java.util.*;

public class TestBookTipDAO implements TipDAO<BookTip> {
    private final Map<String, BookTip> tips = new HashMap<>();

    @Override
    public void add(BookTip tip) {
        var id = UUID.randomUUID().toString();
        tips.put(id, new BookTip(id, tip.getTitle(), tip.getAuthor(), tip.getIsbn()));
    }

    @Override
    public Optional<BookTip> get(String id) {
        return Optional.ofNullable(tips.get(id));
    }

    @Override
    public Collection<BookTip> getAll() {
        return tips.values();
    }

    @Override
    public void delete(String id) {
        tips.remove(id);
    }
}
