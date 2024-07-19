package service.impl;

import DaO.BooksDAO;
import DaO.BooksSetDAO;
import model.BookSets;
import model.Books;
import service.BooksSetService;

import java.sql.SQLException;
import java.util.List;

public class BookSetsServiceimpl implements BooksSetService {

    private final BooksSetDAO booksSetDAO;
    public BookSetsServiceimpl(BooksSetDAO booksSetDAO) {this.booksSetDAO = booksSetDAO;}

    @Override
    public void addBookSet(BookSets bookSet) throws SQLException {booksSetDAO.addBookSet(bookSet);}
    @Override
    public void addBookToSet(int setId, int bookId) throws SQLException {booksSetDAO.addBookToSet(setId, bookId);}
    @Override
    public List<BookSets> getAllBookSets() throws SQLException {return booksSetDAO.getAllBookSets();}
    @Override
    public BookSets getByIdBookSet(int setId) throws SQLException {return booksSetDAO.getByIdBookSet(setId);}
    @Override
    public void updateBookSet(BookSets bookSet) throws SQLException {booksSetDAO.updateBookSet(bookSet);}
    @Override
    public void deleteBookSet(BookSets bookSet) throws SQLException {booksSetDAO.deleteBookSet(bookSet);}
}
