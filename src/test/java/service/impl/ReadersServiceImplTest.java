package service.impl;

import model.Readers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.impl.ReadersServiceImpl;
import DaO.ReadersDAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadersServiceImplTest {

    @Mock
    private ReadersDAO readersDAO;

    @InjectMocks
    private ReadersServiceImpl readersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReader() throws SQLException {
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("John Doe");
        doNothing().when(readersDAO).addReader(reader);
        readersService.addReader(reader);
        verify(readersDAO, times(1)).addReader(reader);
    }

    @Test
    void testGetAllReaders() throws SQLException {
        Readers reader1 = new Readers();
        reader1.setId(1);
        reader1.setName("Alex");
        Readers reader2 = new Readers();
        reader2.setId(2);
        reader2.setName("Brandon");
        List<Readers> expectedReaders = Arrays.asList(reader1, reader2);
        when(readersDAO.getAllReaders()).thenReturn(expectedReaders);
        List<Readers> actualReaders = readersService.getAllReaders();
        assertEquals(expectedReaders, actualReaders);
        verify(readersDAO, times(1)).getAllReaders();
    }

    @Test
    void testGetByReaderId() throws SQLException {
        Readers expectedReader = new Readers();
        expectedReader.setId(1);
        expectedReader.setName("Alex");
        when(readersDAO.getByReaderId(1)).thenReturn(expectedReader);
        Readers actualReader = readersService.getByReaderId(1);
        assertEquals(expectedReader, actualReader);
        verify(readersDAO, times(1)).getByReaderId(1);
    }

    @Test
    void testGetReaderInfo() throws SQLException {
        String expectedInfo = "Reader Name: Alex, Book Set: Classic";
        when(readersDAO.getReaderInfo(1)).thenReturn(expectedInfo);
        String actualInfo = readersService.getReaderInfo(1);
        assertEquals(expectedInfo, actualInfo);
        verify(readersDAO, times(1)).getReaderInfo(1);
    }

    @Test
    void testUpdateReader() throws SQLException {
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        doNothing().when(readersDAO).updateReader(reader);
        readersService.updateReader(reader);
        verify(readersDAO, times(1)).updateReader(reader);
    }

    @Test
    void testDeleteReader() throws SQLException {
        int readerId = 1;
        doNothing().when(readersDAO).deleteReader(readerId);
        readersService.deleteReader(readerId);
        verify(readersDAO, times(1)).deleteReader(readerId);
    }
}
