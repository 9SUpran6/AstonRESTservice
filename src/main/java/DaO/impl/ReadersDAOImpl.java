package DaO.impl;

import DaO.ReadersDAO;
import model.BookSets;
import model.Readers;
import logic.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadersDAOImpl extends Logic implements ReadersDAO {

    Connection connection = getConnection();

    @Override
    public void addReader(Readers reader) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO reader (reader_id, name) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reader.getId());
            preparedStatement.setString(2, reader.getName());
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
    public void assignBookSetToReader(int readerId, int setId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO reader_book_sets (reader_id, set_id) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerId);
            preparedStatement.setInt(2, setId);
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
    public List<Readers> getAllReaders() throws SQLException {
        List<Readers> readerList = new ArrayList<>();
        String sql = "SELECT reader_id, name FROM reader";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Readers reader = new Readers();
                reader.setId(resultSet.getInt("reader_id"));
                reader.setName(resultSet.getString("name"));
                readerList.add(reader);
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
        return readerList;
    }

    @Override
    public Readers getByReaderId(int readerId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT reader_id, name FROM reader WHERE reader_id=?";
        Readers reader = new Readers();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reader.setId(resultSet.getInt("reader_id"));
            reader.setName(resultSet.getString("name"));
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
        return reader;
    }
    @Override
    public String getReaderInfo(int readerId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String a = "0";
        String sql = "SELECT r.name AS reader_name, b.name AS set_name FROM readers r JOIN reader_book_sets rb " +
                "ON r.reader_id = rb.reader_id JOIN book_sets b ON rb.set_id = b.set_id WHERE r.reader_id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, readerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String readerName = resultSet.getString("reader_name");
                String setName = resultSet.getString("set_name");
                a = "Reader Name: " + readerName + ", Book Set: " + setName;
            } else {
                a = "Reader not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return a;
    }


    @Override
    public void updateReader(Readers reader) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE reader SET name=? WHERE reader_id=?";

        try{
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, reader.getName());
            preparedStatement.setInt(2, reader.getId());

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
    public void deleteReader(Readers reader) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM reader WHERE reader_id=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, reader.getId());

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