package winkkari;

import winkkari.data.Tip;
import winkkari.data.TipDAO;

import java.util.Collection;
import java.util.Optional;

class TestTipDAO implements TipDAO {
    @Override
    public void add(Tip tip) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Optional<Tip> get(String id) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Collection<Tip> getAll() {
        throw new IllegalStateException("Not implemented");
    }
}
