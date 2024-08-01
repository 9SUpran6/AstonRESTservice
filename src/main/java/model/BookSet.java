package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookSet {

    private int setId;
    private String setName;
    private List<Books> books = new ArrayList<>();

    public BookSet(){}

    public int getSetId() {
        return setId;
    }
    public void setSetId(int setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }
    public void setSetName(String setName) {
        this.setName = setName;
    }

    public List<Books> getBooks() {return books;}
    public void setBooks(List<Books> books) {this.books = books;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookSet bookSet)) return false;
        return setId == bookSet.setId && Objects.equals(setName, bookSet.setName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setId, setName);
    }
}
