package com.library.controller;

import java.util.List;

import com.library.model.Genre;
import com.library.model.GenreBookDAOInterface;

public class GenresBooksController {
  private GenreBookDAOInterface genreBookDAOInterface;

  public GenresBooksController(GenreBookDAOInterface genreBookDAOInterface) {
    this.genreBookDAOInterface = genreBookDAOInterface;
  }

  public List<Genre> getGenresByBookId(int bookId) {
    List<Genre> genres = genreBookDAOInterface.getGenresByBookId(bookId);
    return genres;
  }

  public boolean addGenreBook(int genreId, int bookId) {
    boolean state = genreBookDAOInterface.addGenreBook(genreId, bookId);
    return state;
  }

  public void deleteGenresBookByBookId(int id) {
    genreBookDAOInterface.deleteGenresBookByBookId(id);
  }

  public void deleteGenresBookByBookId(int bookId, int genreId) {
    genreBookDAOInterface.deleteGenresBookByBookId(bookId, genreId);
  }
}