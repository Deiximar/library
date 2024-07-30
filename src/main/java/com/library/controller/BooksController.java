package com.library.controller;

import com.library.model.Book;
import com.library.model.BookDAOInterface;

public class BooksController {
  private BookDAOInterface bookDAOInterface;

  public BooksController(BookDAOInterface bookDAOInterface) {
    this.bookDAOInterface = bookDAOInterface;
  }

  public int addBook(Book book) {
    int idBook = bookDAOInterface.addBook(book);
    return idBook;
  }
}
