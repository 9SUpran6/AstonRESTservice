package service.impl;

import DaO.BooksDAO;
import DaO.ReadersDAO;
import DaO.impl.BooksDAOimpl;
import model.Books;
import service.BooksService;

import java.sql.SQLException;
import java.util.List;

public class BooksServiceImpl implements BooksService {

    private final BooksDAO booksDAO;
    public BooksServiceImpl(BooksDAO booksDAO) {this.booksDAO = booksDAO;}

    @Override
    public void addBook(Books book) throws SQLException {booksDAO.addBook(book);}
    @Override
    public List<Books> getAllBooks() throws SQLException {return booksDAO.getAllBooks();}
    @Override
    public Books getByIdBook(int bookId) throws SQLException {return booksDAO.getByIdBook(bookId);}
    @Override
    public void updateBook(Books book) throws SQLException {booksDAO.updateBook(book);}
    @Override
    public void deleteBook(Books book) throws SQLException {booksDAO.deleteBook(book);}
}
