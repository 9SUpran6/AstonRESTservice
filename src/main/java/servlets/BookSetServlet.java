package servlets;

import DaO.impl.BookSetDAOImpl;
import logic.Logic;
import model.BookSet;
import service.BooksSetService;
import service.impl.BookSetServiceImpl;

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

//@WebServlet("/booksets/*")
public class BookSetServlet extends HttpServlet {
    private BooksSetService bookSetService;

    @Override
    public void init() throws ServletException {
        Connection connection = new Logic().getConnection();
        bookSetService = new BookSetServiceImpl(new BookSetDAOImpl(connection));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action != null) ? action : "list";
        try {
            switch (action) {
                case "list":
                    listBookSets(response);
                    break;
                case "get":
                    getBookSet(request, response);
                    break;
                default:
                    listBookSets(response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    private void listBookSets(HttpServletResponse response) throws SQLException, IOException {
        List<BookSet> bookSetsList = bookSetService.getAllBookSets();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        for (BookSet bookSet : bookSetsList) {
            out.println("Set ID: " + bookSet.getSetId() + ", Name: " + bookSet.getSetName());
        }
    }

    private void getBookSet(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int setId = Integer.parseInt(request.getParameter("setId"));
        BookSet bookSet = bookSetService.getByIdBookSet(setId);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (bookSet != null) {
            out.println("Set ID: " + bookSet.getSetId() + ", Name: " + bookSet.getSetName());
        } else {
            out.println("Book set not found.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BookSet bookSet = new BookSet();
            bookSet.setSetId(Integer.parseInt(request.getParameter("setId")));
            bookSet.setSetName(request.getParameter("setName"));
            bookSetService.addBookSet(bookSet);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().println("Book set added successfully.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BookSet bookSet = new BookSet();
            bookSet.setSetId(Integer.parseInt(request.getParameter("setId")));
            bookSet.setSetName(request.getParameter("setName"));
            bookSetService.updateBookSet(bookSet);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().println("Book set updated successfully.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int setId = Integer.parseInt(request.getParameter("setId"));
            bookSetService.deleteBookSet(setId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            response.getWriter().println("Book set deleted successfully.");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
