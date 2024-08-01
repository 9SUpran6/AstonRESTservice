package DaO.impl;

import DaO.BooksDAO;
import DaO.BooksSetDAO;
import model.BookSet;
import model.Books;
import logic.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksDAOImpl extends Logic implements BooksDAO {

    Connection connection = getConnection();

   public BooksDAOImpl(Connection connection) {this.connection = connection;}

        @Override
    public void addBook(Books book) throws SQLException {
        String sql = "INSERT INTO books (book_id, title) VALUES (?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public List<Books> getAllBooks() throws SQLException {
        List<Books> booksList = new ArrayList<>();
        String sql = "SELECT book_id, title FROM books";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Books book = new Books();
                    book.setBookId(resultSet.getInt("book_id"));
                    book.setTitle(resultSet.getString("title"));
                    booksList.add(book);
                }
            }
        }
        return booksList;
    }

    @Override
    public Books getByBookId(int bookId) throws SQLException {
        String sql = "SELECT book_id, title FROM books WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Books book = new Books();
                    book.setBookId(resultSet.getInt("book_id"));
                    book.setTitle(resultSet.getString("title"));
                    return book;
                } else {
                    return null; // Если книга не найдена, возвращаем null
                }
            }
        }
    }
    @Override
    public List<Books> getBooksInSet(int setId) throws SQLException {
        List<Books> booksInSet = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title FROM books b JOIN book_set_books bs ON b.book_id = bs.book_id WHERE bs.set_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, setId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                Books book = new Books();
                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                booksInSet.add(book);
            }
           }
        }
        return booksInSet;
    }

    @Override
    public void updateBook(Books book) throws SQLException {
        String sql = "UPDATE books SET title=? WHERE book_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getBookId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM books WHERE book_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,bookId);
            preparedStatement.executeUpdate();
        }
    }
}
