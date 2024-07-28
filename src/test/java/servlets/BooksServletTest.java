package servlets;

import model.Books;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BooksService;

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

class BooksServletTest {

    @Mock
    private BooksService booksService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private BooksServlet booksServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoGet() throws ServletException, IOException, SQLException {
        Books book1 = new Books();
        book1.setBookId(1);
        book1.setTitle("Title1");
        Books book2 = new Books();
        book2.setBookId(2);
        book2.setTitle("Title2");
        List<Books> booksList = Arrays.asList(book1, book2);
        when(booksService.getAllBooks()).thenReturn(booksList);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        booksServlet.doGet(request, response);
        verify(booksService, times(1)).getAllBooks();
        String actualOutput = stringWriter.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        String expectedOutput = "Book ID: 1, Title: Title1\nBook ID: 2, Title: Title2";
        assertEquals(expectedOutput, actualOutput);
//     assertEquals("[{\"bookId\":1,\"title\":\"Title1\"},{\"bookId\":2,\"title\":\"Title2\"}]", stringWriter.toString().trim());
    }



    @Test
    void testDoPost() throws ServletException, IOException, SQLException {
        when(request.getParameter("bookId")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("Title1");
        doNothing().when(booksService).addBook(any(Books.class));
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        booksServlet.doPost(request, response);

        verify(booksService, times(1)).addBook(any(Books.class));
        assertEquals("Book added successfully.", stringWriter.toString().trim());
    }

    @Test
    void testDoPut() throws ServletException, IOException, SQLException {
        when(request.getParameter("bookId")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("Updated Title");
        doNothing().when(booksService).updateBook(any(Books.class));
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        booksServlet.doPut(request, response);

        verify(booksService, times(1)).updateBook(any(Books.class));
        assertEquals("Book updated successfully.", stringWriter.toString().trim());
    }

    @Test
    void testDoDelete() throws ServletException, IOException, SQLException {
        when(request.getParameter("bookId")).thenReturn("1");
        doNothing().when(booksService).deleteBook(anyInt());
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        booksServlet.doDelete(request, response);

        verify(booksService, times(1)).deleteBook(1);
        assertEquals("Book deleted successfully.", stringWriter.toString().trim());
    }
}
