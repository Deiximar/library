package com.library.view;

import com.library.controller.AuthorsBooksController;

public class AuthorBookView {
  private AuthorsBooksController authorsBooksController;

  public AuthorBookView(AuthorsBooksController authorsBooksController) {
    this.authorsBooksController = authorsBooksController;
  }

  public boolean addAuthorBook(int authorId, int bookId) {
    if (!authorsBooksController.addAuthorBook(authorId, bookId)) {
      System.out.println("Fallo al asociar autor con el libro.");
      return false;
    }
    return true;
  }

  public void deleteAuthorBookByBookId(int id) {
    authorsBooksController.deleteAuthorBookByBookId(id);  
}
}