package com.library.view;

import java.util.List;

import com.library.controller.GenresBooksController;
import com.library.model.Genre;

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

  public void showGenresByBookId(List<Genre> genres) {
    if (genres.isEmpty()) {
      System.out.println("No existe autores para este libro.\n");
      return;
    } else {
      for (Genre genre : genres) {
        System.out.println("ID: " + genre.getIdGenre() + ", Género: " + genre.getGenre());
      }
    }
  }
}