package com.library.model;

import java.util.List;

public interface AuthorBookDAOInterface {
  List<Author> getAuthorsByBookId(int bookId);

  boolean addAuthorBook(int authorId, int bookId);

  int deleteAuthorBookByBookId(int id);

  int deleteAuthorBookByBookId(int bookId, int authorId);

}
