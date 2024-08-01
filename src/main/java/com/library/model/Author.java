package com.library.model;

public class Author {
  private int idAuthor;
  private String name;
  private String lastName;

  public Author() {
  }

  public Author(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public int getIdAuthor() {
    return this.idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
