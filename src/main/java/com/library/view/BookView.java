package com.library.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

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

  public void showAllBooks() {
    List<Book> books = booksController.getAllBooks();
    String tableFormat = "| %-3s | %-40s | %-40s| %-40s | %15sn | %n";
    String line = "+-----+------------------------------------------+-----------------------------------------+------------------------------------------+------------------+";
    System.out.println(line);
    System.out.printf(tableFormat, "ID", "Título", "Autores", "Géneros", "código ISBN");

    for (Book book : books) {
      List<Author> authors = authorsBookController.getAuthorsByBookId(book.getIdBook());
      List<Genre> genres = genresBooksController.getGenresByBookId(book.getIdBook());
      StringJoiner authorNamesJoiner = new StringJoiner(", ");
      StringJoiner genreNamesJoiner = new StringJoiner(", ");
      for (Author author : authors) {
        authorNamesJoiner.add(author.getName().trim() + " " + author.getLastName().trim());
      }
      for (Genre genre : genres) {
        genreNamesJoiner.add(genre.getGenre());
      }
      String authorNames = authorNamesJoiner.toString();
      String genreNames = genreNamesJoiner.toString();
      System.out.println(line);
      System.out.printf(tableFormat, book.getIdBook(), book.getTitle(), authorNames, genreNames, book.getCodeISBN());
    }
    System.out.println(line);
  }

  public void addBook(Scanner scanner) {
    scanner.nextLine();
    System.out.println("\033[34m\nPara añadir un libro ingrese los siguientes campos: \n\033[0m");
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
        System.out.println("\nAñadiendo un libro nuevo...");
        System.out.println("\033[32m\nSe ha añadido un libro correctamente!\033[0m");
        showBookById(bookId);
      } else {
        System.out.println(
            "\033[33m\nSe ha añadido un libro, pero no se asociaron los autores o géneros correctamente!");
      }
    } else {
      System.out.println("\031[31m\nFallo al añadir libro.\033[0m");
    }
  };

  public void editBook(Scanner scanner) {
    List<Book> books = booksController.getAllBooks();
    Boolean shouldAskId = true;
    if (books.isEmpty()) {
      System.out.println("\033[33mNo existe libros para modificar.\n\033[0m");
      return;
    } else {
      showAllBooks();
    }
    System.out.println("\033[34m\nSelección según el Id del libro que quieras eliminar (ver listado arriba)\033[0m");
    boolean existBook = false;
    while (shouldAskId) {
      final int bookId = scanner.nextInt();
      scanner.nextLine();
      existBook = books.stream().anyMatch(book -> book.getIdBook() == bookId);
      if (bookId > 0 && existBook) {
        shouldAskId = false;
        boolean editing = true;
        while (editing) {
          showBookById(bookId);
          System.out.println("\n¿Qué campo desea modificar?");
          System.out.println(
              " 1. Título\n 2. Descripción\n 3. Autores\n 4. Código ISBN\n 5. Género\n 6. Finalizar cambios\n");
          int choice = scanner.nextInt();
          String field = null;
          scanner.nextLine();
          switch (choice) {
            case 1:
              System.out.println("Ingrese el nuevo título:");
              String newTitle = scanner.nextLine();
              field = "title";
              booksController.updateBookField(field, newTitle, bookId);
              System.out.println("\033[32m\nActualizando título...\033[0m");
              break;
            case 2:
              System.out.println("Ingrese la nueva descripción:");
              String newDescription = scanner.nextLine();
              field = "description";
              booksController.updateBookField(field, newDescription, bookId);
              System.out.println("\033[32m\nActualizando descripción...\033[0m");
              break;
            case 3:
              authorView.updateAuthorsByBook(scanner, bookId);
              break;
            case 4:
              System.out.println("Ingrese el nuevo código ISBN:");
              String newCodeISBN = scanner.nextLine();
              field = "isbn_code";
              booksController.updateBookField(field, newCodeISBN, bookId);
              System.out.println("\033[32m\nActualizando código ISBN...\033[0m");
              break;
            case 5:
              genreView.updateGenresByBook(scanner, bookId);
              break;
            case 6:
              editing = false;
              break;
            default:
              System.out.println("\033[31mOpción no válida.\033[0m");
          }
        }
      } else {
        System.out.println("\033[31mEl ID del libro introducido no es válido\033[0m");
        System.out
            .println("\033[34m\nSelección según el Id del libro que quieras eliminar (ver listado arriba)\033[0m");
        shouldAskId = true;
      }
    }
  }

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
        System.out.println("\033[32mEl ID es válido\n" + RESET);
        genreBookView.deleteGenresBookByBookId(idBook);
        authorBookView.deleteAuthorBookByBookId(idBook);
        booksController.deleteBookById(idBook);
      } else {
        System.out.println("\n\033[31mEl ID introducido no es válido" + RESET);
        shouldAskId = false;
      }
    }
  }

  public void searchBook(Scanner scanner) {
    while (true) {
      System.out.println("Seleccione una opción para buscar el libro:");
      System.out.println("1. Buscar por título");
      System.out.println("2. Buscar por autor");
      System.out.println("3. Buscar por género");
      System.out.println("4. Salir");

      int option = scanner.nextInt();
      scanner.nextLine();

      if (option == 4) {
        System.out.println("Saliendo de la búsqueda...");
        break;
      }

      List<Book> books;
      switch (option) {
        case 1:
          books = searchBookByTitle(scanner);
          break;
        case 2:
          books = searchBookByAuthor(scanner);
          break;
        case 3:
          books = searchBookByGenre(scanner);
          break;
        default:
          System.out.println("Opción no válida.");
          continue;
      }

      System.out.println("Se han encontrado " + books.size() + " libro(s) con los criterios especificados.");
      displayBooks(books, option);
    }
  }

  private List<Book> searchBookByTitle(Scanner scanner) {
    System.out.println("¿Cuál es el título del libro que quiere buscar?:");
    String bookTitle = scanner.nextLine();
    return booksController.searchBooksByTitle(bookTitle);
  }

  private List<Book> searchBookByAuthor(Scanner scanner) {
    System.out.println("¿Quién es el autor del libro que quiere buscar?:");
    String authorFullName = scanner.nextLine();
    String[] nameParts = authorFullName.split(" ");
    String authorName = nameParts[0];
    String authorLastName = nameParts.length > 1 ? nameParts[1] : "";
    return booksController.searchBooksByAuthor(authorName, authorLastName);
  }

  private List<Book> searchBookByGenre(Scanner scanner) {
    System.out.println("¿Cuál es el género del libro que quiere buscar?:");
    String bookGenre = scanner.nextLine();
    return booksController.searchBooksByGenre(bookGenre);
  }

  private void displayBooks(List<Book> books, int searchOption) {
    if (books.isEmpty()) {
      System.out.println("No se encontraron libros con los criterios especificados.");
    } else {
      if (searchOption == 3) {
        // Formato para búsqueda por género (sin descripción)
        String format = "| %-10d | %-30s | %-30s | %-15s |%n";
        System.out.format(
            "+------------+--------------------------------+--------------------------------+-----------------+%n");
        System.out.format(
            "| ID         | Título                         | Autor                          | ISBN            |%n");
        System.out.format(
            "+------------+--------------------------------+--------------------------------+-----------------+%n");

        for (Book book : books) {
          System.out.format(format,
              book.getIdBook(),
              book.getTitle(),
              book.getAuthor(),
              book.getCodeISBN());
        }
        System.out.format(
            "+------------+--------------------------------+--------------------------------+-----------------+%n");
      } else {
        // Formato para otras búsquedas (con descripción)
        String format = "| %-10d | %-30s | %-30s | %-40s | %-15s |%n";
        System.out.format(
            "+------------+--------------------------------+--------------------------------+------------------------------------------+-----------------+%n");
        System.out.format(
            "| ID         | Título                         | Autor                          | Descripción                              | ISBN            |%n");
        System.out.format(
            "+------------+--------------------------------+--------------------------------+------------------------------------------+-----------------+%n");

        for (Book book : books) {
          System.out.format(format,
              book.getIdBook(),
              book.getTitle(),
              book.getAuthor(),
              truncate(book.getDescription(), 40),
              book.getCodeISBN());
        }
        System.out.format(
            "+------------+--------------------------------+--------------------------------+------------------------------------------+-----------------+%n");
      }
    }
  }

  private String truncate(String value, int length) {
    if (value.length() <= length) {
      return value;
    } else {
      return value.substring(0, length - 3) + "...";
    }
  }

  public void showBookById(int bookID) {
    Book book = booksController.getBookById(bookID);
    List<Author> authors = authorsBookController.getAuthorsByBookId(bookID);
    List<Genre> genres = genresBooksController.getGenresByBookId(bookID);
    StringJoiner authorNamesJoiner = new StringJoiner(", ");
    StringJoiner genreNamesJoiner = new StringJoiner(", ");

    for (Author author : authors) {
      authorNamesJoiner.add(author.getName().trim() + " " + author.getLastName().trim());
    }
    for (Genre genre : genres) {
      genreNamesJoiner.add(genre.getGenre());
    }
    String authorNames = authorNamesJoiner.toString();
    String genreNames = genreNamesJoiner.toString();

    int titleLength = 30;
    int authorLength = 40;
    int descriptionLength = 60;
    int genreLength = 40;
    int isbnLength = 15;

    List<String> wrappedTitle = wrapText(book.getTitle(), titleLength);
    List<String> wrappedAuthors = wrapText(authorNames, authorLength);
    List<String> wrappedDescription = wrapText(book.getDescription(), descriptionLength);
    List<String> wrappedGenres = wrapText(genreNames, genreLength);
    List<String> wrappedISBN = wrapText(book.getCodeISBN(), isbnLength);

    int maxLines = Math.max(Math.max(wrappedTitle.size(), wrappedAuthors.size()),
        Math.max(wrappedDescription.size(),
            Math.max(wrappedGenres.size(), wrappedISBN.size())));

    String tableFormat = "| %-3s | %-30s | %-28s | %-62s | %-23s | %15sn | %n";
    String line = "+-----+--------------------------------+------------------------------+----------------------------------------------------------------+-------------------------+------------------+";
    System.out.println(line);
    System.out.printf(tableFormat, "ID", "Título", "Autores", "Descripción", "Géneros", "Código ISBN");
    System.out.println(line);
    for (int i = 0; i < maxLines; i++) {
      System.out.printf(tableFormat,
          (i == 0 ? book.getIdBook() : ""),
          (i < wrappedTitle.size() ? wrappedTitle.get(i) : ""),
          (i < wrappedAuthors.size() ? wrappedAuthors.get(i) : ""),
          (i < wrappedDescription.size() ? wrappedDescription.get(i) : ""),
          (i < wrappedGenres.size() ? wrappedGenres.get(i) : ""),
          (i < wrappedISBN.size() ? wrappedISBN.get(i) : ""));
    }
    System.out.println(line);
  }

  private static List<String> wrapText(String text, int length) {
    List<String> wrappedText = new ArrayList<>();
    String[] words = text.split(" ");
    StringBuilder line = new StringBuilder();

    for (String word : words) {
      if (line.length() + word.length() > length) {
        wrappedText.add(line.toString());
        line = new StringBuilder();
      }
      if (line.length() > 0) {
        line.append(" ");
      }
      line.append(word);
    }
    wrappedText.add(line.toString());
    return wrappedText;
  }
}