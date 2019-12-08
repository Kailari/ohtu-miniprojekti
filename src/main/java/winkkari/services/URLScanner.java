package winkkari.services;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class URLScanner implements URLSearchService {
    private Optional<URLInfo> urlInfo;

    @Override
    public Optional<URLInfo> find(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            String desc;
            try {
                desc = doc.select("meta[name=description]").first().attr("content");
            } catch (NullPointerException ex) {
                // Page has no description
                desc = "";
            }
            urlInfo = Optional.ofNullable(new URLInfo(url, title, desc));
        } catch (IOException ex) {
            ex.printStackTrace();
            urlInfo = Optional.empty();
        } catch (IllegalArgumentException ex) {
            // Malformatted URL
            urlInfo = Optional.empty();
        }
        return urlInfo;
    }
}