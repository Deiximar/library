package com.library.model;

import java.util.List;

public interface GenreBookDAOInterface {
  boolean addGenreBook(int genreId, int bookId);

  List<Genre> getGenresByBookId(int bookId);

  int deleteGenresBookByBookId(int id);

  int deleteGenresBookByBookId(int bookId, int genreId);
}