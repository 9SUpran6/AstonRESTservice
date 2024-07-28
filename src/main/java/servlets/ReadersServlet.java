package servlets;

import DaO.impl.ReadersDAOImpl;
import logic.Logic;
import model.Readers;

import service.ReadersService;
import service.impl.ReadersServiceImpl;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ReadersServlet extends HttpServlet {
    private ReadersService readersService;

    @Override
    public void init() throws ServletException {
        Connection connection = new Logic().getConnection();
        readersService = new ReadersServiceImpl(new ReadersDAOImpl(connection));// Замените на вашу реализацию DAO
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";  // значение по умолчанию
        }
        try {
            switch (action) {
                case "list":
                    listReaders(response);
                    break;
                case "get":
                    getReader(request, response);
                    break;
                case "info":
                    getReaderInfo(request, response);
                    break;
                default:
                    listReaders(response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    private void listReaders(HttpServletResponse response) throws SQLException, IOException {
        List<Readers> readersList = readersService.getAllReaders();
        PrintWriter out = response.getWriter();
        for (Readers reader : readersList) {
            out.println("Reader ID: " + reader.getId() + ", Name: " + reader.getName());
        }
    }

    private void getReader(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        Readers reader = readersService.getByReaderId(readerId);
        PrintWriter out = response.getWriter();
        if (reader != null) {
            out.println("Reader ID: " + reader.getId() + ", Name: " + reader.getName());
        } else {
            out.println("Reader not found.");
        }
    }

    private void getReaderInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int readerId = Integer.parseInt(request.getParameter("readerId"));
        String info = readersService.getReaderInfo(readerId);
        PrintWriter out = response.getWriter();
        out.println(info);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Readers reader = new Readers();
            String readerId = request.getParameter("readerId");
            String readerName = request.getParameter("readerName");
            if (readerId != null && readerName != null) {
                reader.setId(Integer.parseInt(readerId));
                reader.setName(readerName);
                readersService.addReader(reader);
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("Reader added successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Missing readerId or readerName.");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }



    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Readers reader = new Readers();
            String readerId = request.getParameter("readerId");
            String readerName = request.getParameter("readerName");
            if (readerId != null && readerName != null) {
                reader.setId(Integer.parseInt(readerId));
                reader.setName(readerName);
                readersService.updateReader(reader);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().println("Reader updated successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Missing readerId or readerName.");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String readerId = request.getParameter("readerId");
            if (readerId != null) {
                readersService.deleteReader(Integer.parseInt(readerId));
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().println("Reader deleted successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Missing readerId.");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}