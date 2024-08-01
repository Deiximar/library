package com.library.model;

public interface GenreBookDAOInterface {
  boolean addGenreBook(int genreId, int bookId);
  int deleteGenresBookByBookId(int id);
}
