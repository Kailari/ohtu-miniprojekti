package winkkari.data;

import java.util.Collection;
import java.util.Optional;

public interface TipDAO<TTip extends Tip> {
    void add(TTip tip);

    Optional<TTip> get(String id);

    Collection<TTip> getAll();

    void delete(String id);

    void check(String id, String check);
    
}
