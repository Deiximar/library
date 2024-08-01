package com.library.controller;

import java.util.List;

import com.library.model.Author;
import com.library.model.AuthorDAOInterface;

public class AuthorsController {

  private AuthorDAOInterface authorDAOInterface;

  public AuthorsController(AuthorDAOInterface authorDAOInterface) {
    this.authorDAOInterface = authorDAOInterface;
  }

  public List<Author> getAllAuthors() {
    List<Author> authors = authorDAOInterface.getAllAuthors();
    return authors;
  }

  public int addAuthor(Author author) {
    int idAuthor = authorDAOInterface.addAuthor(author);
    return idAuthor;
  }
}
