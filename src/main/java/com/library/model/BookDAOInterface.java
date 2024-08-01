package com.library.model;

 import java.util.List;


public interface BookDAOInterface {

  List<Book> getAllBooks();
  int addBook(Book book);
  int deleteBookById(int id);
  List<Book> searchBooksByTitle(String title);
  List<Book> searchBooksByAuthor(String authorName, String authorLastName);
  List<Book> searchBooksByGenre(String genre);
}
