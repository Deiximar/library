package com.library.model;

import java.util.List;

public interface BookDAOInterface {
  Book getBookById(int bookId);

  List<Book> getAllBooks();

  int addBook(Book book);

  boolean updateBookByField(String field, String fieldValue, int bookId);

  int deleteBookById(int id);

  List<Book> searchBooksByTitle(String title);

  List<Book> searchBooksByAuthor(String authorName, String authorLastName);

  List<Book> searchBooksByGenre(String genre);
}
