package com.library.model;

import java.util.List;

public interface GenreDAOInterface {
  List<Genre> getGenres();

  int addGenre(Genre genre);

  int findGenreByName(String genre);
}
