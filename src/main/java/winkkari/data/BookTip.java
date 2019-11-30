package winkkari.data;

/**
 * Reading tip for books. Special fields for book-related information.
 */
public class BookTip extends Tip {
    private final String author;
    private final String isbn;

    public BookTip(String id, String title, String author, String isbn, boolean check) {
        super(Type.BOOK, id, title, check);
        this.author = author;
        this.isbn = isbn;
    }

    public BookTip(String title, String author, String isbn) {
        super(Type.BOOK, title);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
}
