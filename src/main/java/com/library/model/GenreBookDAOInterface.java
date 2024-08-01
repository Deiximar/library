package com.library.model;

import java.util.List;

public interface GenreBookDAOInterface {
  boolean addGenreBook(int genreId, int bookId);

  int deleteGenresBookByBookId(int id);

  List<Genre> getGenresByBookId(int bookId);
}
