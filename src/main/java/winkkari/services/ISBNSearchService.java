package winkkari.services;

import java.util.Optional;

public interface ISBNSearchService {
    Optional<BookInfo> find(String isbn);
}
