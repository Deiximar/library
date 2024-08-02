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
      System.out.println("\033[31mNo existe autores para este libro.\n\033[0m");
      return;
    } else {

      String tableFormat = "| %-3s | %-30s | %-30s | %n";
      String line = "+-----+--------------------------------+--------------------------------+";
      System.out.println(line);
      System.out.printf(tableFormat, "ID", "Nombre", "Apellido");
      System.out.println(line);
      for (Author author : authors) {
        System.out.printf(tableFormat, author.getIdAuthor(), author.getName(), author.getLastName());
        System.out.println(line);
      }
    }
  }
}