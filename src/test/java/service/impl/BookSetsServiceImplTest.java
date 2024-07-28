package service.impl;

import DaO.BooksSetDAO;
import model.BookSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookSetsServiceImplTest {

    @Mock
    private BooksSetDAO booksSetDAO;

    @InjectMocks
    private BookSetServiceImpl bookSetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBookSet() throws SQLException {
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        doNothing().when(booksSetDAO).addBookSet(bookSet);
        bookSetService.addBookSet(bookSet);
        verify(booksSetDAO, times(1)).addBookSet(bookSet);
    }

    @Test
    void testAddBookToSet() throws SQLException {
          doNothing().when(booksSetDAO).addBookToSet(1, 2);
        bookSetService.addBookToSet(1, 2);
        verify(booksSetDAO, times(1)).addBookToSet(1, 2);
    }

    @Test
    void testGetAllBookSets() throws SQLException {
        BookSet bookSet1 = new BookSet();
        bookSet1.setSetId(1);
        bookSet1.setSetName("Classic");
        BookSet bookSet2 = new BookSet();
        bookSet2.setSetId(2);
        bookSet2.setSetName("Fantastic");
        List<BookSet> expectedBookSets = Arrays.asList(bookSet1, bookSet2);
        when(booksSetDAO.getAllBookSets()).thenReturn(expectedBookSets);
        List<BookSet> actualBookSets = bookSetService.getAllBookSets();
        assertEquals(expectedBookSets, actualBookSets);
        verify(booksSetDAO, times(1)).getAllBookSets();
    }

    @Test
    void testGetByIdBookSet() throws SQLException {
        BookSet expectedBookSet = new BookSet();
        expectedBookSet.setSetId(1);
        expectedBookSet.setSetName("Classic");
        when(booksSetDAO.getByIdBookSet(1)).thenReturn(expectedBookSet);
        BookSet actualBookSet = bookSetService.getByIdBookSet(1);
        assertEquals(expectedBookSet, actualBookSet);
        verify(booksSetDAO, times(1)).getByIdBookSet(1);
    }

    @Test
    void testUpdateBookSet() throws SQLException {
        BookSet bookSet = new BookSet();
        bookSet.setSetId(1);
        bookSet.setSetName("Classic");
        doNothing().when(booksSetDAO).updateBookSet(bookSet);
        bookSetService.updateBookSet(bookSet);
        verify(booksSetDAO, times(1)).updateBookSet(bookSet);
    }

    @Test
    void testDeleteBookSet() throws SQLException {
        doNothing().when(booksSetDAO).deleteBookSet(1);
        bookSetService.deleteBookSet(1);
        verify(booksSetDAO, times(1)).deleteBookSet(1);
    }
}