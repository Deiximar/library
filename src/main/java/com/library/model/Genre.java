package com.library.model;

public class Genre {
  private int idGenre;
  private String genre;

  public Genre() {
  }

  public Genre(String genre) {
    this.genre = genre;
  }

  public Genre() {
  }

  public int getIdGenre() {
    return this.idGenre;
  }

  public void setIdGenre(int idGenre) {
    this.idGenre = idGenre;
  }

  public String getGenre() {
    return this.genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
