package com.library.model;

import java.util.List;

public interface GenreBookDAOInterface {
  boolean addGenreBook(int genreId, int bookId);

  int deleteGenresBookByBookId(int id);

  int deleteGenresBookByBookId(int bookId, int genreId);

  List<Genre> getGenresByBookId(int bookId);
}
