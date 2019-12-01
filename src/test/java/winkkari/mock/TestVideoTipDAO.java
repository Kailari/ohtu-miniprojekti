package winkkari.mock;

import winkkari.data.TipDAO;
import winkkari.data.VideoTip;

import java.util.*;

public class TestVideoTipDAO implements TipDAO<VideoTip> {
    private final Map<String, VideoTip> tips = new HashMap<>();

    @Override
    public void add(VideoTip tip) {
        var id = UUID.randomUUID().toString();
        tips.put(id, new VideoTip(id, tip.getTitle(), tip.getUrl(), tip.getComment(), false));
    }

    @Override
    public Optional<VideoTip> get(String id) {
        return Optional.ofNullable(tips.get(id));
    }

    @Override
    public Collection<VideoTip> getAll() {
        return tips.values();
    }

    @Override
    public void delete(String id) {
        tips.remove(id);
    }

    @Override
    public void check(String id, boolean check) {
        var tip = tips.get(id);
        tips.put(id, new VideoTip(id, tip.getTitle(), tip.getUrl(), tip.getComment(), !tip.getCheck()));
    }

    @Override
    public void update(VideoTip tip) {
        tips.replace(tip.getId(), tip);
    }
}
