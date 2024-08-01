package com.library.controller;

import com.library.model.GenreBookDAOInterface;

public class GenresBooksController {
  private GenreBookDAOInterface genreBookDAOInterface;

  public GenresBooksController(GenreBookDAOInterface genreBookDAOInterface) {
    this.genreBookDAOInterface = genreBookDAOInterface;
  }

  public boolean addGenreBook(int genreId, int bookId) {
    boolean state = genreBookDAOInterface.addGenreBook(genreId, bookId);
    return state;
  }

  public void deleteGenresBookByBookId(int id) {
    genreBookDAOInterface.deleteGenresBookByBookId(id);
  }
}
