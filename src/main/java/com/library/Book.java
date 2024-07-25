package com.library;

public class Book {
  private int idBook;
  private String title;
  private String description;
  private String codeISBN;
  

  public Book(String title, String description, String codeISBN) {
    this.title = title;
    this.description = description;
    this.codeISBN = codeISBN;
    this.idBook = 0;
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

  public void getTitle(String title) {
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

}
