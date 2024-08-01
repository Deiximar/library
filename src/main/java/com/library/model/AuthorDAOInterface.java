package com.library.model;

import java.util.List;

public interface AuthorDAOInterface {
  List<Author> getAllAuthors();

  int addAuthor(Author author);

  int findAuthorByName(String name, String lastName);
}
