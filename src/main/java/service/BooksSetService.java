package service;

import model.BookSet;

import java.sql.SQLException;
import java.util.List;

public interface BooksSetService {

    //Create
    void addBookSet(BookSet bookSet) throws SQLException;
    void addBookToSet(int setId, int bookId) throws  SQLException;
    //Read
    List<BookSet> getAllBookSets() throws SQLException;
    BookSet getByIdBookSet(int setId) throws SQLException;
    //Update
    void updateBookSet(BookSet bookSet) throws SQLException;
    //Delete
    void deleteBookSet(int bookSetId) throws SQLException;

}
