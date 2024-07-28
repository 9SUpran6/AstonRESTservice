package service.impl;

import DaO.BooksSetDAO;
import model.BookSet;
import service.BooksSetService;

import java.sql.SQLException;
import java.util.List;

public class BookSetServiceImpl implements BooksSetService {

    private final BooksSetDAO bookSetDAO;
    public BookSetServiceImpl(BooksSetDAO bookSetDAO) {this.bookSetDAO = bookSetDAO;}

    @Override
    public void addBookSet(BookSet bookSet) throws SQLException {bookSetDAO.addBookSet(bookSet);}
    @Override
    public void addBookToSet(int setId, int bookId) throws SQLException {bookSetDAO.addBookToSet(setId, bookId);}
    @Override
    public List<BookSet> getAllBookSets() throws SQLException {return bookSetDAO.getAllBookSets();}
    @Override
    public BookSet getByIdBookSet(int setId) throws SQLException {return bookSetDAO.getByIdBookSet(setId);}
    @Override
    public void updateBookSet(BookSet bookSet) throws SQLException {bookSetDAO.updateBookSet(bookSet);}
    @Override
    public void deleteBookSet(int bookSetId) throws SQLException {bookSetDAO.deleteBookSet(bookSetId);}
}
