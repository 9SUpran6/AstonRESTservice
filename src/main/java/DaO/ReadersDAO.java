package DaO;

import model.Readers;

import java.sql.SQLException;
import java.util.List;

public interface ReadersDAO {

    //Create
    void addReader(Readers reader) throws SQLException;
    void assignBookSetToReader(int readerId, int setId) throws SQLException;
    //Read
    List<Readers> getAllReaders() throws SQLException;
    Readers getByReaderId(int readerId) throws SQLException;
    String getReaderInfo(int readerId) throws SQLException;
    //Update
    void updateReader(Readers reader)throws SQLException;
    //Delete
    void deleteReader(Readers reader)throws SQLException;
}
