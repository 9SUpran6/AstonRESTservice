package DaO.impl;

import DaO.BooksSetDAO;
import model.BookSets;
import logic.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookSetDAOImpl extends Logic implements BooksSetDAO {

    Connection connection = getConnection();

    @Override
    public void addBookSet(BookSets bookSet) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO book_sets (set_id, name) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookSet.getSetId());
            preparedStatement.setString(2, bookSet.getSetName());
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
    public void addBookToSet(int setId, int bookId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO book_set_books (set_id, book_id) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, setId);
            statement.setInt(2, bookId);
            statement.executeUpdate();
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
    public List<BookSets> getAllBookSets() throws SQLException {
        List<BookSets> bookSetsList = new ArrayList<>();
        String sql = "SELECT reader_id, name FROM book_sets";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                BookSets bookSets = new BookSets();
                bookSets.setSetId(resultSet.getInt("set_id"));
                bookSets.setSetName(resultSet.getString("name"));
                bookSetsList.add(bookSets);
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
        return bookSetsList;
    }

    @Override
    public BookSets getByIdBookSet(int setId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT set_id, name FROM book_sets WHERE set_id=?";
        BookSets bookSets = new BookSets();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, setId);
            ResultSet resultSet = preparedStatement.executeQuery();

            bookSets.setSetId(resultSet.getInt("set_id"));
            bookSets.setSetName(resultSet.getString("name"));

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
        return bookSets;
    }

    @Override
    public void updateBookSet(BookSets bookSet) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE book_sets SET name=? WHERE set_id=?";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, bookSet.getSetName());
            preparedStatement.setInt(2, bookSet.getSetId());

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
    public void deleteBookSet(BookSets bookSet) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM book_sets WHERE set_id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookSet.getSetId());

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

