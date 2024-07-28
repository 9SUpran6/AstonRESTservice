package DaO.impl;

import DaO.ReadersDAO;
import model.Readers;
import logic.Logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadersDAOImpl extends Logic implements ReadersDAO {

    Connection connection = getConnection();
    public ReadersDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addReader(Readers reader) throws SQLException{
        String sql = "INSERT INTO readers (reader_id, reader_name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, reader.getId());
            preparedStatement.setString(2, reader.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void assignBookSetToReader(int readerId, int setId) throws SQLException{
        String sql = "INSERT INTO reader_book_sets (reader_id, set_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, readerId);
            preparedStatement.setInt(2, setId);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Readers> getAllReaders() throws SQLException{
        List<Readers> readersList = new ArrayList<>();
        String sql = "SELECT reader_id, reader_name FROM readers";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Readers reader = new Readers();
                    reader.setId(resultSet.getInt("reader_id"));
                    reader.setName(resultSet.getString("reader_name"));
                    readersList.add(reader);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readersList;
    }

    @Override
    public Readers getByReaderId(int readerId) throws SQLException{
        String sql = "SELECT reader_id, reader_name FROM readers WHERE reader_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, readerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    Readers reader = new Readers();
                    reader.setId(resultSet.getInt("reader_id"));
                    reader.setName(resultSet.getString("reader_name"));
                    return reader;
                } else {
                    return null; // Если читатель не найден
                }
            }
        }
    }

    @Override
    public String getReaderInfo(int readerId) throws SQLException{
        String a = "0";
        String sql = "SELECT r.reader_name AS reader_name, b.name AS set_name FROM readers r JOIN reader_book_sets rb " +
                "ON r.reader_id = rb.reader_id JOIN book_sets b ON rb.set_id = b.set_id WHERE r.reader_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
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
        }
        return a;
    }


    @Override
    public void updateReader(Readers reader) throws SQLException {
        String sql = "UPDATE readers SET reader_name=? WHERE reader_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, reader.getName());
            preparedStatement.setInt(2, reader.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReader(int readerId) throws SQLException{
        String sql = "DELETE FROM readers WHERE reader_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, readerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}