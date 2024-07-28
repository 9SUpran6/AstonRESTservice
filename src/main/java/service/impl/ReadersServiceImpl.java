package service.impl;

import DaO.ReadersDAO;
import model.Readers;
import service.ReadersService;

import java.sql.SQLException;
import java.util.List;

public class ReadersServiceImpl implements ReadersService {

    private final ReadersDAO readersDAO;
    public ReadersServiceImpl(ReadersDAO readersDAO) {this.readersDAO = readersDAO;}

    @Override
    public void addReader(Readers reader) throws SQLException {readersDAO.addReader(reader);}
    @Override
    public void assignBookSetToReader(int readerId, int setId) throws SQLException {readersDAO.assignBookSetToReader(readerId, setId);}
    @Override
    public List<Readers> getAllReaders() throws SQLException {return readersDAO.getAllReaders();}
    @Override
    public Readers getByReaderId(int readerId) throws SQLException {return readersDAO.getByReaderId(readerId);}
    @Override
    public String getReaderInfo(int readerId) throws SQLException {return readersDAO.getReaderInfo(readerId);}
    @Override
    public void updateReader(Readers reader) throws SQLException {readersDAO.updateReader(reader);}
    @Override
    public void deleteReader(int readerId) throws SQLException {readersDAO.deleteReader(readerId);}

}
