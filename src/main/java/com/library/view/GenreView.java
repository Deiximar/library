package com.library.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.library.controller.GenresBooksController;
import com.library.controller.GenresController;
import com.library.model.Genre;
import com.library.model.GenreBookDAO;
import com.library.model.GenreBookDAOInterface;

public class GenreView {
  private GenreBookDAOInterface genreBookDAO = new GenreBookDAO();
  private GenresBooksController genresBooksController = new GenresBooksController(genreBookDAO);
  private GenreBookView genreBookView = new GenreBookView(genresBooksController);

  private GenresController genresController;

  public GenreView(GenresController genresController) {
    this.genresController = genresController;
  }

  public List<Genre> getGenres(Scanner scanner) {
    List<Genre> genres = new ArrayList<>();
    String addMore;
    do {
      System.out.print("Género: ");
      String genreBook = scanner.nextLine();
      genres.add(new Genre(genreBook));
      System.out.print("¿Desea agregar otro género? (s/n): ");
      addMore = scanner.nextLine();
    } while (addMore.equalsIgnoreCase("s"));
    return genres;
  }

  public boolean addGenres(List<Genre> genres, int bookId) {
    boolean success = true;
    for (Genre genre : genres) {
      int genreId = genresController.addGenre(genre);
      if (genreId > 0) {
        success = genreBookView.addGenreBook(genreId, bookId);
      } else {
        success = false;
        System.out.println("Fallo al añadir género.");
      }
    }
    return success;
  }
}
