package winkkari.data;

public class SortableTipWrapper {
    private final Tip wrapped;

    public SortableTipWrapper(Tip tip) {
        wrapped = tip;
    }

    public Tip getTip() {
        return wrapped;
    }

    public String getId() {
        return wrapped.getId();
    }

    public Tip.Type getType() {
        return wrapped.getType();
    }

    public String getTitle() {
        return wrapped.getTitle();
    }

    public boolean getCheck() {
        return wrapped.getCheck();
    }

    public String getIsbn() {
        return wrapped.getType() == Tip.Type.BOOK
                ? ((BookTip) wrapped).getIsbn()
                : null;
    }

    public String getAuthor() {
        return wrapped.getType() == Tip.Type.BOOK
                ? ((BookTip) wrapped).getAuthor()
                : null;
    }

    public String getUrl() {
        if (wrapped.getType() == Tip.Type.LINK) {
            return ((LinkTip) wrapped).getUrl();
        } else if (wrapped.getType() == Tip.Type.VIDEO) {
            return ((VideoTip) wrapped).getUrl();
        } else {
            return null;
        }

    }

    public String getComment() {
        if (wrapped.getType() == Tip.Type.LINK) {
            return ((LinkTip) wrapped).getComment();
        } else if (wrapped.getType() == Tip.Type.VIDEO) {
            return ((VideoTip) wrapped).getComment();
        } else {
            return null;
        }

    }



}
