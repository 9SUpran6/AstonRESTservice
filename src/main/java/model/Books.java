package model;

import java.util.Objects;

public class Books {
    private int bookId;
    private String title;


    public Books() {}

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int id) {
        this.bookId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return bookId == books.bookId && Objects.equals(title, books.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title);
    }
}
