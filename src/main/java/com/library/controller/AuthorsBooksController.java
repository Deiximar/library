package com.library.controller;

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

  public void deleteAuthorBookByBookId(int id) {
    authorBookDAOInterface.deleteAuthorBookByBookId(id);  
}
}
