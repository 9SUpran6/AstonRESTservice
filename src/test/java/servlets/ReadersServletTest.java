package servlets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ReadersService;
import model.Readers;

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

class ReadersServletTest {

    @Mock
    private ReadersService readersService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private ReadersServlet readersServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDoGet() throws ServletException, IOException, SQLException {
        Readers reader1 = new Readers();
        reader1.setId(1);
        reader1.setName("Reader1");
        Readers reader2 = new Readers();
        reader2.setId(2);
        reader2.setName("Reader2");
        List<Readers> readersList = Arrays.asList(reader1, reader2);
        when(readersService.getAllReaders()).thenReturn(readersList);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        readersServlet.doGet(request, response);
        verify(readersService, times(1)).getAllReaders();
        String actualOutput = stringWriter.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        String expectedOutput = "Reader ID: 1, Name: Reader1\nReader ID: 2, Name: Reader2";
        assertEquals(expectedOutput, actualOutput);
//        assertEquals("Reader ID: 1, Name: Reader1\nReader ID: 2, Name: Reader2", stringWriter.toString().trim());
    }

    @Test
    void testDoPost() throws ServletException, IOException, SQLException {
        when(request.getParameter("readerId")).thenReturn("1");
        when(request.getParameter("readerName")).thenReturn("Reader1");
        doNothing().when(readersService).addReader(any(Readers.class));
        readersServlet.doPost(request, response);
        verify(readersService, times(1)).addReader(any(Readers.class));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPut() throws ServletException, IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("readerId")).thenReturn("1");
        when(request.getParameter("readerName")).thenReturn("Updated Reader");
        doNothing().when(readersService).updateReader(any(Readers.class));
        readersServlet.doPut(request, response);
        verify(readersService, times(1)).updateReader(any(Readers.class));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
        printWriter.flush();
    }



    @Test
    void testDoDelete() throws ServletException, IOException, SQLException {
        when(request.getParameter("readerId")).thenReturn("1");
        doNothing().when(readersService).deleteReader(anyInt());
        readersServlet.doDelete(request, response);
        verify(readersService, times(1)).deleteReader(1);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
