package DaO.impl;

import DaO.BooksDAO;
import model.BookSets;
import model.Books;
import logic.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksDAOimpl extends Logic implements BooksDAO {

    Connection connection = getConnection();

    @Override
    public void addBook(Books book) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO books (book_id, title) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }



    @Override
    public List<Books> getAllBooks() throws SQLException {
        List<Books> booksList = new ArrayList<>();
        String sql = "SELECT book_id, title FROM books";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Books book = new Books();
                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                booksList.add(book);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
        return booksList;
    }

    @Override
    public Books getByIdBook(int bookId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT book_id, title FROM books WHERE book_id=?";
        Books book = new Books();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            book.setBookId(resultSet.getInt("book_id"));
            book.setTitle(resultSet.getString("title"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
        return book;
    }

//    public List<Books> getBooksInSet(int setId) throws SQLException {
//        PreparedStatement preparedStatement = null;
//        List<Books> booksInSet = new ArrayList<>();
//        String sql = "SELECT b.title FROM books b JOIN book_set_books bs ON b.book_id = bs.book_id WHERE bs.set_id = ?";
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, setId);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Books book = new Books();
//                book.setBookId(resultSet.getInt("book_id"));
//                book.setTitle(resultSet.getString("title"));
//                booksInSet.add(book);
//            while (resultSet.next()) {
//                books.append(resultSet.getString("title")).append(", ");
//            }
//            return booksInSet;
//        }
//    }}

    @Override
    public void updateBook(Books book) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE books SET title=? WHERE book_id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getBookId());

            preparedStatement.executeUpdate();
    } catch (SQLException e) {
            e.printStackTrace();
    } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
    }
    }

    @Override
    public void deleteBook(Books book) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM books WHERE book_id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, book.getBookId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }
}
