package winkkari.data;

import java.util.Collection;
import java.util.Optional;

public interface TipDAO {
    void add(Tip tip);

    Optional<Tip> get(String id);

    Collection<Tip> getAll();

    void delete(String id);
}
