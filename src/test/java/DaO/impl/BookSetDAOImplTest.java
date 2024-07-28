package DaO.impl;

import DaO.BooksDAO;
import model.BookSet;
import model.Books;
import model.Readers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class BookSetDAOImplTest {

    private static final Logger log = LoggerFactory.getLogger(BookSetDAOImplTest.class);
    @Container
    static MySQLContainer<?> mySql = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testDb")
            .withUsername("testUser")
            .withPassword("testPass");
    private BooksDAOImpl booksDAO;
    private BookSetDAOImpl bookSetDAO;

    @BeforeAll
    public static void setUp() throws Exception {
        mySql.start();
        String jdbcUrl = mySql.getJdbcUrl();
        String username = mySql.getUsername();
        String password = mySql.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS books (book_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title VARCHAR(100))");
            statement.execute("CREATE TABLE IF NOT EXISTS book_sets (set_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))");
            statement.execute("CREATE TABLE book_set_books (set_id INT, book_id INT, PRIMARY KEY (set_id, book_id), FOREIGN KEY (set_id) REFERENCES book_sets(set_id), FOREIGN KEY (book_id) REFERENCES books(book_id))");
        }
    }
    @BeforeEach
    public void setUpEach() throws SQLException {
        String jdbcUrl = mySql.getJdbcUrl();
        String username = mySql.getUsername();
        String password = mySql.getPassword();
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        booksDAO = new BooksDAOImpl(connection);
        bookSetDAO = new BookSetDAOImpl(connection);
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");

            statement.execute("DELETE FROM book_set_books");
            statement.execute("DELETE FROM book_sets");
            statement.execute("DELETE FROM books");

            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @Test
    void addBookSet() throws SQLException{
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        assertEquals(1,bookSet.getSetId());
    }

    @Test
    void addBookToSet() throws  SQLException{
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("War and World");
        booksDAO.addBook(book);
        bookSetDAO.addBookToSet(1,1);
        List<Books> bookInSet = booksDAO.getBooksInSet(1);
        assertEquals(1, bookInSet.size());
    }

    @Test
    void getAllBookSets() throws SQLException{
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        List<BookSet> bookSets = bookSetDAO.getAllBookSets();
        assertEquals(1, bookSets.size());
    }

    @Test
    void getByIdBookSet() throws SQLException{
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        BookSet bookSetOut;
        bookSetOut = bookSetDAO.getByIdBookSet(1);
        assertNotNull(bookSetOut);
        assertEquals("Classic", bookSetOut.getSetName());
    }

    @Test
    void updateBookSet() throws SQLException{
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        bookSet.setSetId(1);
        bookSet.setSetName("Fantastic");
        bookSetDAO.updateBookSet(bookSet);
        assertEquals("Fantastic", bookSet.getSetName());
    }

    @Test
    void deleteBookSet() throws SQLException{
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        bookSet.setSetId(2);
        bookSet.setSetName("Fantastic");
        bookSetDAO.addBookSet(bookSet);
        bookSetDAO.deleteBookSet(1);
        List<BookSet> bookSets = bookSetDAO.getAllBookSets();
        assertEquals(1, bookSets.size());
    }
}