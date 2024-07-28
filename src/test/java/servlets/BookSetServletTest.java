package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BooksSetService;
import model.BookSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookSetServletTest {

    @Mock
    private BooksSetService booksSetService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private BookSetServlet bookSetServlet;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(printWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDoGet() throws ServletException, IOException, SQLException {
        BookSet bookSet1 = new BookSet();
        bookSet1.setSetId(1);
        bookSet1.setSetName("Set1");
        BookSet bookSet2 = new BookSet();
        bookSet2.setSetId(2);
        bookSet2.setSetName("Set2");
        List<BookSet> bookSetsList = Arrays.asList(bookSet1, bookSet2);
        when(booksSetService.getAllBookSets()).thenReturn(bookSetsList);
        bookSetServlet.doGet(request, response);
        verify(booksSetService, times(1)).getAllBookSets();
        String actualOutput = stringWriter.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        String expectedOutput = "Set ID: 1, Name: Set1\nSet ID: 2, Name: Set2";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testDoPost() throws ServletException, IOException, SQLException {
        when(request.getParameter("setId")).thenReturn("1");
        when(request.getParameter("setName")).thenReturn("Set1");
        doNothing().when(booksSetService).addBookSet(any(BookSet.class));
        bookSetServlet.doPost(request, response);
        verify(booksSetService, times(1)).addBookSet(any(BookSet.class));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPut() throws ServletException, IOException, SQLException {
        when(request.getParameter("setId")).thenReturn("1");
        when(request.getParameter("setName")).thenReturn("Updated Set");
        doNothing().when(booksSetService).updateBookSet(any(BookSet.class));
        bookSetServlet.doPut(request, response);
        verify(booksSetService, times(1)).updateBookSet(any(BookSet.class));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoDelete() throws ServletException, IOException, SQLException {
        when(request.getParameter("setId")).thenReturn("1");
        doNothing().when(booksSetService).deleteBookSet(anyInt());
        bookSetServlet.doDelete(request, response);
        verify(booksSetService, times(1)).deleteBookSet(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
