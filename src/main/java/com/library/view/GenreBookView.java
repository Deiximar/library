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
      System.out.println("\033[31mNo existe autores para este libro.\n\033[0m");
      return;
    } else {
      String tableFormat = "| %-3s | %-30s | %n";
      String line = "+-----+--------------------------------+";
      System.out.println(line);
      System.out.printf(tableFormat, "ID", "Género");
      System.out.println(line);
      for (Genre genre : genres) {
        System.out.printf(tableFormat, genre.getIdGenre(), genre.getGenre());
        System.out.println(line);
      }
    }
  }
}