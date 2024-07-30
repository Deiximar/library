package com.library.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.library.controller.AuthorsBooksController;
import com.library.controller.AuthorsController;
import com.library.model.Author;
import com.library.model.AuthorBookDAO;
import com.library.model.AuthorBookDAOInterface;

public class AuthorView {
  private AuthorBookDAOInterface authorBookDAO = new AuthorBookDAO();
  private AuthorsBooksController authorsBooksController = new AuthorsBooksController(authorBookDAO);
  private AuthorBookView authorBookView = new AuthorBookView(authorsBooksController);

  private AuthorsController authorsController;

  public AuthorView(AuthorsController authorsController) {
    this.authorsController = authorsController;
  }

  public List<Author> getAuthors(Scanner scanner) {
    List<Author> authors = new ArrayList<>();
    String addMore;
    do {
      System.out.print("Nombre del autor: ");
      String authorName = scanner.nextLine().trim();
      System.out.print("Apellido del autor: ");
      String authorLastname = scanner.nextLine().trim();
      authors.add(new Author(authorName, authorLastname));
      System.out.print("¿Desea agregar otro autor? (s/n): ");
      addMore = scanner.nextLine();
    } while (addMore.equalsIgnoreCase("s"));
    return authors;
  }

  public boolean addAuthors(List<Author> authors, int bookId) {
    boolean success = true;
    for (Author author : authors) {
      int authorId = authorsController.addAuthor(author);
      if (authorId > 0) {
        success = authorBookView.addAuthorBook(authorId, bookId);
      } else {
        success = false;
        System.out.println("Fallo al añadir autor.");
      }
    }
    return success;
  }
}
