package DaO;

import model.Books;

import java.sql.SQLException;
import java.util.List;

public interface BooksDAO{

    //Create
    void addBook(Books book) throws SQLException;
    //Read
    List<Books> getAllBooks() throws SQLException;
    List<Books> getBooksInSet(int setId)throws SQLException;
    Books getByBookId(int bookId) throws SQLException;
    //Update
    void updateBook(Books book) throws SQLException;
    //Delete
    void deleteBook(int bookId) throws SQLException;

}
