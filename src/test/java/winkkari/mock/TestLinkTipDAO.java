package winkkari.mock;

import winkkari.data.LinkTip;
import winkkari.data.TipDAO;

import java.util.*;

public class TestLinkTipDAO implements TipDAO<LinkTip> {
    private final Map<String, LinkTip> tips = new HashMap<>();

    @Override
    public void add(LinkTip tip) {
        var id = UUID.randomUUID().toString();
        tips.put(id, new LinkTip(id, tip.getTitle(), tip.getUrl(), tip.getComment()));
    }

    @Override
    public Optional<LinkTip> get(String id) {
        return Optional.ofNullable(tips.get(id));
    }

    @Override
    public Collection<LinkTip> getAll() {
        return tips.values();
    }

    @Override
    public void delete(String id) {
        tips.remove(id);
    }
}
