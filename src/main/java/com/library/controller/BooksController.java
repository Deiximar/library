package com.library.controller;

import java.util.List;
import com.library.model.Book;
import com.library.model.BookDAOInterface;

public class BooksController {
  private BookDAOInterface bookDAOInterface;

  public BooksController(BookDAOInterface bookDAOInterface) {
    this.bookDAOInterface = bookDAOInterface;
  }

  public List<Book> getAllBooks() {
    List<Book> books = bookDAOInterface.getAllBooks();
    return books;
  }

  public Book getBookById(int bookId) {
    Book book = bookDAOInterface.getBookById(bookId);
    return book;
  }

  public void updateBookField(String field, String fieldValue, int bookId) {
    bookDAOInterface.updateBookByField(field, fieldValue, bookId);
  }

  public int addBook(Book book) {
    int idBook = bookDAOInterface.addBook(book);
    return idBook;
  }

  public void deleteBookById(int bookId) {
    bookDAOInterface.deleteBookById(bookId);
  }

  public List<Book> searchBooksByTitle(String title) {
    return bookDAOInterface.searchBooksByTitle(title);
  }

  public List<Book> searchBooksByAuthor(String authorName, String authorLastName) {
    return bookDAOInterface.searchBooksByAuthor(authorName, authorLastName);
  }

  public List<Book> searchBooksByGenre(String genre) {
    return bookDAOInterface.searchBooksByGenre(genre);
  }

}
