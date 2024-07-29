package com.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\nBiblioteca de todos\n¿Qué desea realizar? (Selecione un número)");

        int option;

        do {
            System.out.println(
                    "\n 1. Mostrar todos los libros\n 2. Añadir un libro\n 3. Editar un libro\n 4. Eliminar un libro\n 5. Realizar una búsqueda\n 6. Salir\n");

            option = scanner.nextInt();
            if (option < 1 || option > 6) {
                System.out.println("Seleccione un número del 1 - 6\n");

            } else {
                switch (option) {
                    case 1:
                        // showAllBooks();
                        break;
                    case 2:
                        addBook();
                        break;
                    case 3:
                        // editBook();
                        break;
                    case 4:
                        // deleteBook();
                        break;
                    case 5:
                        SearchBooks searchBooks = new SearchBooks();
                        searchBooks.searchBook();
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        System.exit(0);
                }
            }
        } while (option != 6);

        scanner.close();
    }

    public static void addBook() {
        scanner.nextLine();
        BookDAO bookModel = new BookDAO();
        AuthorDAO authorModel = new AuthorDAO();
        GenreDAO genreModel = new GenreDAO();
        AuthorBookDAO authorBookModel = new AuthorBookDAO();
        GenreBookDAO genreBookModel = new GenreBookDAO();

        System.out.println("\nPara añadir un libro ingrese los siguientes campos: ");

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Descripción: ");
        String description = scanner.nextLine();

        System.out.print("Código ISBN: ");
        String codeISBN = scanner.nextLine();

        Book book = new Book(title, description, codeISBN);
        int bookId = bookModel.addBook(book);

        if (bookId > 0) {
            List<Author> authors = new ArrayList<>();
            List<Genre> genres = new ArrayList<>();

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

            do {
                System.out.print("Género: ");
                String genreBook = scanner.nextLine();

                genres.add(new Genre(genreBook));

                System.out.print("¿Desea agregar otro género? (s/n): ");
                addMore = scanner.nextLine();
            } while (addMore.equalsIgnoreCase("s"));

            boolean success = true;

            for (Author author : authors) {
                int authorId = authorModel.addAuthor(author);
                if (authorId > 0) {
                    if (!authorBookModel.addAuthorBook(authorId, bookId)) {
                        success = true;
                        System.out.println("Fallo al asociar autor con el libro.");
                    }
                } else {
                    success = true;
                    System.out.println("Fallo al añadir autor.");
                }
            }

            for (Genre genre : genres) {
                int genreId = genreModel.addGenre(genre);
                if (genreId > 0) {
                    if (!genreBookModel.addGenreBook(genreId, bookId)) {
                        success = true;
                        System.out.println("Fallo al asociar género con el libro.");
                    }
                } else {
                    success = true;
                    System.out.println("Fallo al añadir género.");
                }
            }

            if (success) {
                System.out.println("Se ha añadido un libro correctamente!");
            } else {
                System.out.println(
                        "Se ha añadido un libro, pero la no se asociaron los autores o géneros correctamente!");
            }

        } else {
            System.out.println("Fallo al añadir libro.");
        }
    }

}
