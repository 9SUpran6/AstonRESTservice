package DaO.impl;

import model.BookSet;
import model.Readers;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import service.impl.BookSetServiceImpl;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class ReadersDAOImplTest {

    @Container
    static MySQLContainer<?> mySql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testDb")
            .withUsername("testUser")
            .withPassword("testPass");
    private ReadersDAOImpl readersDAO;
    private BookSetDAOImpl bookSetDAO;

    @BeforeAll
    public static void setUp() throws Exception {
        mySql.start();
        String jdbcUrl = mySql.getJdbcUrl();
        String username = mySql.getUsername();
        String password = mySql.getPassword();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS readers (reader_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, reader_name VARCHAR(100))");
            statement.execute("CREATE TABLE IF NOT EXISTS book_sets (set_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            statement.execute("CREATE TABLE IF NOT EXISTS reader_book_sets (reader_id INT, set_id INT, PRIMARY KEY (reader_id, set_id), FOREIGN KEY (reader_id) REFERENCES readers(reader_id), FOREIGN KEY (set_id) REFERENCES book_sets(set_id))");
        }
    }
    @BeforeEach
    public void setUpEach() throws SQLException {
        String jdbcUrl = mySql.getJdbcUrl();
        String username = mySql.getUsername();
        String password = mySql.getPassword();
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        readersDAO = new ReadersDAOImpl(connection);
        bookSetDAO = new BookSetDAOImpl(connection);
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");

            statement.execute("DELETE FROM reader_book_sets");
            statement.execute("DELETE FROM readers");
            statement.execute("DELETE FROM book_sets");

            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @AfterAll
    static void afterAll() {
        mySql.stop();
    }

    @Test
    void addReader() throws SQLException{
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        readersDAO.addReader(reader);
        assertEquals(1,reader.getId());
    }

    @Test
    void assignBookSetToReader() throws SQLException{
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        readersDAO.addReader(reader);
        BookSet bookset = new BookSet();
        bookset.setSetId(1);
        bookset.setSetName("Fantastic");
        bookSetDAO.addBookSet(bookset);
        readersDAO.assignBookSetToReader(1,1);
        assertEquals(1, bookset.getSetId());
        assertEquals(1, reader.getId());
    }

    @Test
    void getAllReaders() throws SQLException{
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        readersDAO.addReader(reader);
        List<Readers> readers = readersDAO.getAllReaders();
        assertEquals(1, readers.size());
    }

    @Test
    void getByIdReader() throws SQLException{
        Readers reader = new Readers();
        reader.setId(2);
        reader.setName("Brandon");
        readersDAO.addReader(reader);
        Readers readerOut;
        readerOut = readersDAO.getByReaderId(2);
        Assertions.assertNotNull(readerOut);
        assertEquals("Brandon", readerOut.getName());
    }

    @Test
    void getReaderInfo() throws SQLException{
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        readersDAO.addReader(reader);
        BookSet bookset = new BookSet();
        bookset.setSetId(1);
        bookset.setSetName("Fantastic");
        bookSetDAO.addBookSet(bookset);
        readersDAO.assignBookSetToReader(1,1);
        readersDAO.getReaderInfo(1);
        assertEquals("Reader Name: " + "Alex" + ", Book Set: " + "Fantastic", "Reader Name: " + reader.getName() + ", Book Set: " + bookset.getSetName());
    }

    @Test
    void updateReader() throws SQLException {
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        readersDAO.addReader(reader);
        assertEquals("Alex", reader.getName());
        reader.setId(1);
        reader.setName("Brandon");
        readersDAO.updateReader(reader);
        assertEquals("Brandon", reader.getName());
    }

    @Test
    void deleteReader() throws SQLException {
        Readers reader = new Readers();
        reader.setId(1);
        reader.setName("Alex");
        readersDAO.addReader(reader);
        List<Readers> readers = readersDAO.getAllReaders();
        assertEquals(1, readers.size());
        readersDAO.deleteReader(1);
        readers = readersDAO.getAllReaders();
        assertEquals(0, readers.size());
    }
}