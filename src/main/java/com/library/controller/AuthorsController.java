package com.library.controller;

import com.library.model.Author;
import com.library.model.AuthorDAOInterface;

public class AuthorsController {

  private AuthorDAOInterface authorDAOInterface;

  public AuthorsController(AuthorDAOInterface authorDAOInterface) {
    this.authorDAOInterface = authorDAOInterface;
  }

  public int addAuthor(Author author) {
    int idAuthor = authorDAOInterface.addAuthor(author);
    return idAuthor;
  }
}
