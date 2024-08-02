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
      String authorName = scanner.nextLine();
      System.out.print("Apellido del autor: ");
      String authorLastname = scanner.nextLine();
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

  public List<Author> updateAuthorsByBook(Scanner scanner, int bookId) {
    boolean ask = true;
    List<Author> authors = new ArrayList<>();
    System.out.println(
        "\nEstos son los autores vinculados a este libro.");
    while (ask) {
      authors = authorsBooksController.getAuthorsByBookId(bookId);
      authorBookView.showAuthorsByBookId(authors);
      System.out.println("\033[34m\nElija la opción que quiera realizar para el o los autores de este libro: \033[0m");
      System.out.println(" 1. Agregar nuevo autor\n 2. Desvincular autor\n 3. Salir\n");

      int authorOption = scanner.nextInt();
      scanner.nextLine();

      switch (authorOption) {
        case 1:
          List<Author> newAuthors = getAuthors(scanner);
          addAuthors(newAuthors, bookId);
          System.out.println("\033[32m\nAgregando author...\033[0m");
          System.out.println(
              "\033[32m\nEstos son los autores actualizados vinculados a este libro.\033[0m");
          break;
        case 2:
          boolean foundAuthor = false;
          while (!foundAuthor) {
            System.out.println("\033[33m\nIntroduzca el id del autor a desvincular\033[0m");
            int idAuthor = scanner.nextInt();
            foundAuthor = authors.stream().anyMatch(author -> author.getIdAuthor() == idAuthor);
            if (foundAuthor) {
              System.out.println("\033[32m\nDesvinculando author...\033[0m");
              authorsBooksController.deleteAuthorBookByBookId(bookId, idAuthor);
              System.out.println(
                  "\033[32m\nEstos son los autores actualizados vinculados a este libro .\033[0m");
            } else {
              System.out.println("\033[31m\nNúmero de id incorrecto.\033[0m");
            }
          }
          break;
        case 3:
          ask = false;
          return authors;
        default:
          System.out.println("\033[31m\nOpción incorrecta. Intente de nuevo. \033[0m");
      }
    }
    return authors;
  }
}