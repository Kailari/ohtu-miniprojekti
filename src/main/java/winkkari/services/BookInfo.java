package winkkari.services;

public class BookInfo {
    private final String isbn, author, title;

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public BookInfo(String isbn, String author, String title) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
    }
}
