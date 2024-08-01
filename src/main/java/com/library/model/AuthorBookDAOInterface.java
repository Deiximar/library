package com.library.model;

import java.util.List;

public interface AuthorBookDAOInterface {
  boolean addAuthorBook(int authorId, int bookId);

  int deleteAuthorBookByBookId(int id);

  List<Author> getAuthorsByBookId(int bookId);

  int deleteAuthorBookByBookId(int idBook, int idAuthor);

}
