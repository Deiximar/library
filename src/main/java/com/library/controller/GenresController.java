package com.library.controller;

import com.library.model.Genre;
import com.library.model.GenreDAOInterface;

public class GenresController {
  private GenreDAOInterface genreDAOInterface;

  public GenresController(GenreDAOInterface genreDAOInterface) {
    this.genreDAOInterface = genreDAOInterface;
  }

  public int addGenre(Genre genre) {
    int idGenre = genreDAOInterface.addGenre(genre);
    return idGenre;
  }
}