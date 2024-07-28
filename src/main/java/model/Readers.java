package model;

import java.util.Objects;

public class Readers {
    private int readerId;
    private String readerName;

    public Readers(){}

    public int getId() {
        return readerId;
    }

    public void setId(int id) {
        this.readerId = id;
    }

    public String getName() {
        return readerName;
    }

    public void setName(String name) {
        this.readerName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Readers readers = (Readers) o;
        return readerId == readers.readerId && Objects.equals(readerName, readers.readerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerId, readerName);
    }

    @Override
    public String toString() {
        return "Readers{" +
                "readerId=" + readerId +
                ", readerName='" + readerName + '\'' +
                '}';
    }
}
