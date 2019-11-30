package winkkari.data;

public class LinkTip extends Tip {
    private final String url;
    private final String comment;

    public LinkTip(String id, String title, String url, String comment, boolean check) {
        super(Type.LINK, id, title, check);
        this.url = url;
        this.comment = comment;
    }

    public LinkTip(String title, String url, String comment) {
        super(Type.LINK, title);
        this.url = url;
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public String getComment() {
        return comment;
    }
}
