package com.library;

public class Author {
  private int idAuthor;
  private String name;
  private String lastName;

  public Author(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public int getIdAuthor() {
    return this.idAuthor;
  }

  public String getName() {
    return this.name;
  }

  public void getName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
