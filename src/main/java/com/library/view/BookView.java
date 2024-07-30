package com.library.view;

import java.util.List;
import java.util.Scanner;

import com.library.controller.AuthorsController;
import com.library.controller.BooksController;
import com.library.model.Author;
import com.library.model.AuthorDAO;
import com.library.model.AuthorDAOInterface;
import com.library.model.Book;
import com.library.model.Genre;
import com.library.model.GenreDAO;
import com.library.model.GenreDAOInterface;
import com.library.controller.GenresController;

public class BookView {
  AuthorDAOInterface authorDAO = new AuthorDAO();
  AuthorsController authorsController = new AuthorsController(authorDAO);
  AuthorView authorView = new AuthorView(authorsController);

  GenreDAOInterface genreDAO = new GenreDAO();
  GenresController genresController = new GenresController(genreDAO);
  GenreView genreView = new GenreView(genresController);

  private BooksController booksController;

  public BookView(BooksController booksController) {
    this.booksController = booksController;
  }

  public void addBook(Scanner scanner) {
    scanner.nextLine();

    System.out.println("\nPara añadir un libro ingrese los siguientes campos: \n");
    System.out.print("Título: ");
    String title = scanner.nextLine();
    System.out.print("Descripción: ");
    String description = scanner.nextLine();
    System.out.print("Código ISBN: ");
    String codeISBN = scanner.nextLine();

    Book book = new Book(title, description, codeISBN);
    int bookId = booksController.addBook(book);

    if (bookId > 0) {
      List<Author> authors = authorView.getAuthors(scanner);
      List<Genre> genres = genreView.getGenres(scanner);
      boolean success = true;
      success = authorView.addAuthors(authors, bookId);
      success = genreView.addGenres(genres, bookId);
      if (success) {
        System.out.println("Se ha añadido un libro correctamente!");
      } else {
        System.out.println(
            "Se ha añadido un libro, pero no se asociaron los autores o géneros correctamente!");
      }
    } else {
      System.out.println("Fallo al añadir libro.");
    }
  }
}
