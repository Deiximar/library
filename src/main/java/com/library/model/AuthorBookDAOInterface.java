package com.library.model;

public interface AuthorBookDAOInterface {
  boolean addAuthorBook(int authorId, int bookId);
  int deleteAuthorBookByBookId(int id);
}
