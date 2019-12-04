package winkkari.services;

import java.util.Optional;

public interface URLSearchService {
    Optional<URLInfo> find(String url);
}
