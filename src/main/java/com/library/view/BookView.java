package com.library.view;

//import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.library.controller.AuthorsBooksController;
import com.library.controller.AuthorsController;
import com.library.controller.BooksController;
import com.library.controller.GenresBooksController;
import com.library.model.Author;
import com.library.model.AuthorBookDAO;
import com.library.model.AuthorBookDAOInterface;
import com.library.model.AuthorDAO;
import com.library.model.AuthorDAOInterface;
import com.library.model.Book;
import com.library.model.Genre;
import com.library.model.GenreBookDAO;
import com.library.model.GenreBookDAOInterface;
import com.library.model.GenreDAO;
import com.library.model.GenreDAOInterface;
import com.library.controller.GenresController;

public class BookView {
  private AuthorDAOInterface authorDAO = new AuthorDAO();
  private AuthorsController authorsController = new AuthorsController(authorDAO);
  private AuthorView authorView = new AuthorView(authorsController);

  private GenreDAOInterface genreDAO = new GenreDAO();
  private GenresController genresController = new GenresController(genreDAO);
  private GenreView genreView = new GenreView(genresController);

  private AuthorBookDAOInterface authorBookDAO = new AuthorBookDAO();
  private AuthorsBooksController authorsBookController = new AuthorsBooksController(authorBookDAO);
  private AuthorBookView authorBookView = new AuthorBookView(authorsBookController);

  private GenreBookDAOInterface genreBookDAO = new GenreBookDAO();
  private GenresBooksController genresBooksController = new GenresBooksController(genreBookDAO);
  private GenreBookView genreBookView = new GenreBookView(genresBooksController);

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
  };

  public void deleteBook(Scanner scanner) {
        List<Book> books = booksController.getAllBooks();
        Boolean shouldAskId = true;
        String RESET = "\033[0m";
        int idBook;
        boolean found = false;

        for (Book book : books) {
          System.out.println("ID: " + book.getIdBook() + ", Título: " + book.getTitle());
        }

        while (shouldAskId) {
            if (books.isEmpty()) {
                System.out.println("\n\033[31mNo existen libros para eliminar" + RESET);
                break;
            }
            System.out.println("\n\033[33mEscribe el ID del libro que deseas eliminar o escribe 0 para cancelar\n");
            idBook = scanner.nextInt();
            scanner.nextLine();
            final int finalIdBook = idBook;
            found = books.stream().anyMatch(book -> book.getIdBook() == finalIdBook);
            if (idBook > 0 && found) {
                shouldAskId = false;
                System.out.println("\033[32mEl ID es válido\n"+ RESET);
                genreBookView.deleteGenresBookByBookId(idBook);
                authorBookView.deleteAuthorBookByBookId(idBook);
                booksController.deleteBookById(idBook);
            } else {
                System.out.println("\n\033[31mEl ID introducido no es válido" + RESET);
                shouldAskId = false;
            }
        }
  }
}
