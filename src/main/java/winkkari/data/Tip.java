package winkkari.data;

/**
 * Abstract base for different tip types, to allow conveniently passing collections of different
 * types of tips between the front- and the back end.
 */
public abstract class Tip {
    private final String id;
    private final String title;

    /** Allows the frontend to differentiate between types of tips. */
    private transient final Type type;

    /**
     * DAO-constructor. Should not be called directly from non-DAO -classes. Used for constructing
     * tips from data from DB.
     *
     * @param type  The type of this tip. Subclasses should set this accordingly
     * @param id    The id of this tip
     * @param title The title of this tip
     */
    protected Tip(Type type, String id, String title) {
        this.type = type;
        this.id = id;
        this.title = title;
    }

    /**
     * Default API-facing constructor. Use this to construct temporary instances for {@link
     * TipDAO#add(Tip)}
     *
     * @param type  The type of this tip. Subclasses should set this accordingly
     * @param title The title of this tip
     */
    protected Tip(Type type, String title) {
        this.type = type;
        this.id = null;
        this.title = title;
    }

    /**
     * Type of this tip. Frontend should be able to differentiate between tip types using this.
     *
     * @return the tip type
     */
    public Type getType() {
        return type;
    }

    /**
     * Unique identifier for this tip. These are unique only on per-type basis, e.g. an instance of
     * {@link BookTip} and a {@link LinkTip} may have the same ID.
     *
     * @return the id of this tip
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the title of this tip. e.g. for books the title of the book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    public enum Type {
        BOOK,
        LINK,
        VIDEO,
    }
}
