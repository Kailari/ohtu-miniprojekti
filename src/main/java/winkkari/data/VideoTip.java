package winkkari.data;

public class VideoTip extends Tip {
    private final String url;
    private final String comment;

    public VideoTip(String id, String title, String url, String comment, boolean check) {
        super(Type.VIDEO, id, title, check);
        this.url = url;
        this.comment = comment;
    }

    public VideoTip(String title, String url, String comment) {
        super(Type.VIDEO, title);
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
