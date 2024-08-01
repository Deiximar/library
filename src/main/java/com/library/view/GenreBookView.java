package com.library.view;

import com.library.controller.GenresBooksController;

public class GenreBookView {
  private GenresBooksController genresBooksController;

  public GenreBookView(GenresBooksController genresBooksController) {
    this.genresBooksController = genresBooksController;
  }

  public boolean addGenreBook(int GenreId, int bookId) {
    if (!genresBooksController.addGenreBook(GenreId, bookId)) {
      System.out.println("Fallo al asociar género con el libro.");
      return false;
    }
    return true;
  }

  public void deleteGenresBookByBookId(int id) {
    genresBooksController.deleteGenresBookByBookId(id);
  }
}
