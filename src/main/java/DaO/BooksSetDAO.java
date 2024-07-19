package DaO;

import model.BookSets;

import java.sql.SQLException;
import java.util.List;

public interface BooksSetDAO {

    //Create
    void addBookSet(BookSets bookSet) throws SQLException;
    void addBookToSet(int setId, int bookId) throws  SQLException;
    //Read
    List<BookSets> getAllBookSets() throws SQLException;
    BookSets getByIdBookSet(int setId) throws SQLException;
    //Update
    void updateBookSet(BookSets bookSet) throws SQLException;
    //Delete
    void deleteBookSet(BookSets bookSet) throws SQLException;

}
