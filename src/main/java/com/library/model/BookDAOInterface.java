package com.library.model;

import java.util.List;

public interface BookDAOInterface {

  List<Book> getAllBooks();

  int addBook(Book book);

  int deleteBookById(int id);

  boolean updateBookByField(String field, String fieldValue, int bookId);

  Book getBookById(int bookId);
}
