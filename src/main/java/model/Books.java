package model;

import java.util.*;

public class Books {
    private int bookId;
    private String title;
    private List<BookSet> bookSet = new ArrayList<>();


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

    public void setBookSet(List<BookSet> bookSet){this.bookSet = bookSet;}
    public List<BookSet> getBookSet(){return bookSet;}

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
