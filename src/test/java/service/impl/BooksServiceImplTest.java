package service.impl;

import model.Books;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import DaO.BooksDAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BooksServiceImplTest {

    @Mock
    private BooksDAO booksDAO;

    @InjectMocks
    private BooksServiceImpl booksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBook() throws SQLException {
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("Red Hat");
        doNothing().when(booksDAO).addBook(book);
        booksService.addBook(book);
        verify(booksDAO, times(1)).addBook(book);
    }

    @Test
    void testGetAllBooks() throws SQLException {
        Books book1 = new Books();
        book1.setBookId(1);
        book1.setTitle("Red Hat");
        Books book2 = new Books();
        book2.setBookId(2);
        book2.setTitle("Pusher");
        List<Books> expectedBooks = Arrays.asList(book1, book2);
        when(booksDAO.getAllBooks()).thenReturn(expectedBooks);
        List<Books> actualBooks = booksService.getAllBooks();
        assertEquals(expectedBooks, actualBooks);
        verify(booksDAO, times(1)).getAllBooks();
    }

    @Test
    void testGetByBookId() throws SQLException {
        Books expectedBook = new Books();
        expectedBook.setBookId(1);
        expectedBook.setTitle("Red Hat");
        when(booksDAO.getByBookId(1)).thenReturn(expectedBook);
        Books actualBook = booksService.getByBookId(1);
        assertEquals(expectedBook, actualBook);
        verify(booksDAO, times(1)).getByBookId(1);
    }

    @Test
    void testUpdateBook() throws SQLException {
        Books book = new Books();
        book.setBookId(1);
        book.setTitle("Red Hat");
        doNothing().when(booksDAO).updateBook(book);
        booksService.updateBook(book);
        verify(booksDAO, times(1)).updateBook(book);
    }

    @Test
    void testDeleteBook() throws SQLException {
        doNothing().when(booksDAO).deleteBook(1);
        booksService.deleteBook(1);
        verify(booksDAO, times(1)).deleteBook(1);
    }
}
