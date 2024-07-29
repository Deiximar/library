package com.library.model;

public interface GenreDAOInterface {
  int addGenre(Genre genre);

  int findGenreByName(String genre);
}
