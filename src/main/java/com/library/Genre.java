package com.library;

public class Genre {
  private int idGenre;
  private String genre;

  public Genre(String genre) {
    this.genre = genre;
  }

  public int getIdGenre() {
    return this.idGenre;
  }

  public String getGenre() {
    return this.genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
