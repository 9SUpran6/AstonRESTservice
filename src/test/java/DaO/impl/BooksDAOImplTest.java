package DaO.impl;

import logic.Logic;
import model.BookSet;
import model.Books;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class BooksDAOImplTest {

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

    @AfterAll
    static void afterAll() {
        mySql.stop();
    }

    @Test
    void addBook() throws SQLException {
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("RedHat");
        booksDAO.addBook(book);
        assertNotNull(book.getBookId());
    }

    @Test
    void getAllBooks() throws SQLException{
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("RedHat");
        booksDAO.addBook(book);
        List<Books> books = booksDAO.getAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    void getByIdBook() throws SQLException {
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("War and World");
        booksDAO.addBook(book);
        Books bookOut;
        bookOut = booksDAO.getByBookId(1);
        assertNotNull(bookOut);
        assertEquals("War and World", bookOut.getTitle());
    }

    @Test
    void getBookInSet() throws SQLException{
        Books book = new Books();
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        bookSetDAO.addBookSet(bookSet);
        book.setBookId(1);
        book.setTitle("War and World");
        booksDAO.addBook(book);
        book.setBookId(2);
        book.setTitle("Pusher");
        booksDAO.addBook(book);
        bookSetDAO.addBookToSet(1,1);
        bookSetDAO.addBookToSet(1,2);
        List<Books> booksInSet =  booksDAO.getBooksInSet(1);
        assertEquals(2, booksInSet.size());
    }

    @Test
    void updateBook() throws SQLException {
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("War and World");
        booksDAO.addBook(book);
        book.setBookId(1);
        book.setTitle("Pusher");
        booksDAO.updateBook(book);
        assertEquals("Pusher", book.getTitle());
    }

    @Test
    void deleteBook() throws SQLException{
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("War and World");
        booksDAO.addBook(book);
        book.setBookId(2);
        book.setTitle("Pusher");
        booksDAO.addBook(book);
        booksDAO.deleteBook(1);
        List<Books> books = booksDAO.getAllBooks();
        assertEquals(1, books.size());
    }
}