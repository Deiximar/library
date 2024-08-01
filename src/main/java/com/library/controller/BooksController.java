package com.library.controller;

import java.util.List;
import com.library.model.Book;
import com.library.model.BookDAOInterface;

public class BooksController {
  private BookDAOInterface bookDAOInterface;

  public BooksController(BookDAOInterface bookDAOInterface) {
    this.bookDAOInterface = bookDAOInterface;
  }

  public List<Book> getAllBooks(){
    List<Book> books = bookDAOInterface.getAllBooks();
    return books;
  }

  public int addBook(Book book) {
    int idBook = bookDAOInterface.addBook(book);
    return idBook;
  }

  public void deleteBookById(int idBook) {
    bookDAOInterface.deleteBookById(idBook);
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
