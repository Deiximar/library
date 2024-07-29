package com.library.model;

public interface AuthorDAOInterface {
  int addAuthor(Author author);

  int findAuthorByName(String name, String lastName);
}
