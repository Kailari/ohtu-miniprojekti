package winkkari;

import winkkari.data.Tip;
import winkkari.data.TipDAO;

import java.util.*;

class TestTipDAO implements TipDAO {
    private final Map<String, Tip> tips = new HashMap<>();

    @Override
    public void add(Tip tip) {
        var id = UUID.randomUUID().toString();
        tips.put(id, new Tip(id, tip.getTitle(), tip.getAuthor()));
    }

    @Override
    public Optional<Tip> get(String id) {
        return Optional.ofNullable(tips.get(id));
    }

    @Override
    public Collection<Tip> getAll() {
        return tips.values();
    }

    @Override
    public void delete(String id) {
        tips.remove(id);
    }
}
