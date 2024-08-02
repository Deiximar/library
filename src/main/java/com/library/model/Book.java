package com.library.model;

public class Book {
  private int idBook;
  private String title;
  private String description;
  private String codeISBN;
  private String author;

  public Book() {
  }

  public Book(String title, String description, String codeISBN) {
    this.title = title;
    this.description = description;
    this.codeISBN = codeISBN;
  }

  public Book(String title, String description, String codeISBN, String author) {
    this.title = title;
    this.description = description;
    this.codeISBN = codeISBN;
    this.author = author;
  }

  public int getIdBook() {
    return this.idBook;
  }

  public void setIdBook(int idBook) {
    this.idBook = idBook;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCodeISBN() {
    return this.codeISBN;
  }

  public void setCodeISBN(String codeISBN) {
    this.codeISBN = codeISBN;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}