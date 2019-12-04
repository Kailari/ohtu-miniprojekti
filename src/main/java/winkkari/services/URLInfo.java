package winkkari.services;

public class URLInfo {
    private final String url;
    private final String title;
    private final String description;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URLInfo(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }
}
