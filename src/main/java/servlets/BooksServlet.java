package servlets;

import model.Books;
import service.BooksService;
import service.impl.BooksServiceImpl;
import DaO.impl.BooksDAOImpl;
import logic.Logic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/books/*")
public class BooksServlet extends HttpServlet {
    private BooksService booksService;

    @Override
    public void init() throws ServletException {
        Connection connection = new Logic().getConnection();
        booksService = new BooksServiceImpl(new BooksDAOImpl(connection));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Значение по умолчанию
        }
        try {
            switch (action) {
                case "list":
                    listBooks(response);
                    break;
                case "get":
                    getBook(request, response);
                    break;
                default:
                    listBooks(response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void getBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String bookIdParam = request.getParameter("bookId");
        if (bookIdParam != null) {
            int bookId = Integer.parseInt(bookIdParam);
            Books book = booksService.getByBookId(bookId);
            PrintWriter out = response.getWriter();
            if (book != null) {
                out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle());
            } else {
                out.println("Book not found.");
            }
        } else {
            response.getWriter().println("Book ID is required.");
        }
    }

    private void listBooks(HttpServletResponse response) throws SQLException, IOException {
        List<Books> booksList = booksService.getAllBooks();
        PrintWriter out = response.getWriter();
        for (Books book : booksList) {
            out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Books book = new Books();
            book.setBookId(Integer.parseInt(request.getParameter("bookId")));
            book.setTitle(request.getParameter("title"));
            booksService.addBook(book);
            response.getWriter().println("Book added successfully.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Books book = new Books();
            book.setBookId(Integer.parseInt(request.getParameter("bookId")));
            book.setTitle(request.getParameter("title"));
            booksService.updateBook(book);
            response.getWriter().println("Book updated successfully.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            booksService.deleteBook(bookId);
            response.getWriter().println("Book deleted successfully.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}