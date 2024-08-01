package com.library.controller;

import java.util.List;

import com.library.model.Author;
import com.library.model.AuthorBookDAOInterface;

public class AuthorsBooksController {

  private AuthorBookDAOInterface authorBookDAOInterface;

  public AuthorsBooksController(AuthorBookDAOInterface authorBookDAOInterface) {
    this.authorBookDAOInterface = authorBookDAOInterface;
  }

  public boolean addAuthorBook(int authorId, int bookId) {
    boolean state = authorBookDAOInterface.addAuthorBook(authorId, bookId);
    return state;
  }

  public List<Author> getAuthorsByBookId(int bookId) {
    List<Author> authors = authorBookDAOInterface.getAuthorsByBookId(bookId);
    return authors;
  }

  public void deleteAuthorBookByBookId(int id) {
    authorBookDAOInterface.deleteAuthorBookByBookId(id);
  }
}
