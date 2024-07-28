package DaO.impl;

import DaO.BooksSetDAO;
import model.BookSet;
import logic.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookSetDAOImpl extends Logic implements BooksSetDAO {

    Connection connection = getConnection();
    public BookSetDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBookSet(BookSet bookSet) throws SQLException {
        String sql = "INSERT INTO book_sets (set_id, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, bookSet.getSetId());
            preparedStatement.setString(2, bookSet.getSetName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void addBookToSet(int setId, int bookId) throws SQLException {
        String sql = "INSERT INTO book_set_books (set_id, book_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, setId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<BookSet> getAllBookSets() throws SQLException {
        List<BookSet> bookSetsList = new ArrayList<>();
        String sql = "SELECT set_id, name FROM book_sets";
        try (Statement statement = connection.createStatement()){
           try (ResultSet resultSet = statement.executeQuery(sql)){
                while (resultSet.next()) {
                BookSet bookSets = new BookSet();
                bookSets.setSetId(resultSet.getInt("set_id"));
                bookSets.setSetName(resultSet.getString("name"));
                bookSetsList.add(bookSets);
                }
           }
        }
        return bookSetsList;
    }

    @Override
    public BookSet getByIdBookSet(int setId) throws SQLException {
        String sql = "SELECT set_id, name FROM book_sets WHERE set_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, setId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    BookSet bookSet= new BookSet();
                    bookSet.setSetId(resultSet.getInt("set_id"));
                    bookSet.setSetName(resultSet.getString("name"));
                    return bookSet;
                } else {
                    return null; // Если набор не найдена, возвращаем null
                }
            }
        }
    }

    @Override
    public void updateBookSet(BookSet bookSet) throws SQLException {
        String sql = "UPDATE book_sets SET name=? WHERE set_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, bookSet.getSetName());
            preparedStatement.setInt(2, bookSet.getSetId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteBookSet(int bookSetId) throws SQLException {
        String sql = "DELETE FROM book_sets WHERE set_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, bookSetId);
            preparedStatement.executeUpdate();
        }
    }
}

