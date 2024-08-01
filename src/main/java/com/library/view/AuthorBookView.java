package com.library.view;

import java.util.List;

import com.library.controller.AuthorsBooksController;
import com.library.model.Author;

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

  public void showAuthorsByBookId(List<Author> authors) {
    if (authors.isEmpty()) {
      System.out.println("No existe autores para este libro.\n");
      return;
    } else {
      for (Author author : authors) {
        System.out.println("ID: " + author.getIdAuthor() + ", Autor: " + author.getName() + " " + author.getLastName());
      }
    }
  }
}