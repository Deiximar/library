package com.library;

import java.util.Scanner;



public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\nBiblioteca de todos\n¿Qué desea realizar? (Selecione un número)");
        System.out.println(
                " 1. Mostrar todos los libros\n 2. Añadir un libro\n 3. Editar un libro\n 4. Eliminar un libro\n 5. Realizar una búsqueda\n 6.Salir\n");

        int option = scanner.nextInt();

        do {
            switch (option) {
                case 1:
                    // showAllBooks();
                    break;

                case 2:
                    addBook();
                    break;

                case 3:
                editBook();
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
                scanner.close();
                System.exit(0);
                break;

                default:
                    System.out.println("Seleccione un numero del 1 - 6\n");
                    option = scanner.nextInt();
            }
        } while (option > 6 || option < 1);

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

        System.out.print("Nombre del autor: ");
        String authorName = scanner.nextLine();

        System.out.print("Apellido del autor: ");
        String authorLastname = scanner.nextLine();

        System.out.print("Descripción: ");
        String description = scanner.nextLine();

        System.out.print("Código ISBN: ");
        String codeISBN = scanner.nextLine();

        System.out.print("Género: ");
        String genreBook = scanner.nextLine();

        Book book = new Book(title, description, codeISBN);
        Author author = new Author(authorName, authorLastname);
        Genre genre = new Genre(genreBook);

        int bookId = bookModel.addBook(book);
        int authorId = authorModel.addAuthor(author);
        int genreId = genreModel.addGenre(genre);

        if (bookId > 0 && authorId > 0 && genreId > 0) {
            boolean isAuthorBookAdded = authorBookModel.addAuthorBook(authorId, bookId);
            boolean isGenreBookAdded = genreBookModel.addGenreBook(genreId, bookId);
            if (isAuthorBookAdded && isGenreBookAdded) {
                System.out.println("Libro, autor y género asociados correctamente.");
            } else {
                System.out.println("Fallo al asociar libro con autor o género.");
            }
        } else {
            System.out.println("Fallo al añadir libro, autor o género.");
        }
    }
    // Implementación de editBook usando bookDAO
 private static void editBook() {
    BookDAO bookModel = new BookDAO();

    // Solicitar al usuario el ID del libro a editar
    System.out.println("Ingrese el ID del libro a editar:");
    int id = scanner.nextInt();
    scanner.nextLine(); // Consumir el salto de línea restante

    // Solicitar nuevos datos del libro
    System.out.println("Ingrese el nuevo título del libro:");
    String newTitle = scanner.nextLine();
    System.out.println("Ingrese la nueva descripción del libro:");
    String newDescription = scanner.nextLine();
    System.out.println("Ingrese el nuevo código ISBN del libro:");
    String newIsbnCode = scanner.nextLine();

    // Crear un objeto Book con los nuevos datos
    Book bookToUpdate = new Book(newTitle, newDescription, newIsbnCode);
    bookToUpdate.setIdBook(id);

    // Usar bookDAO para actualizar el libro en la base de datos
    boolean updated = bookModel.updateBook(bookToUpdate);

    // Informar al usuario si la actualización fue exitosa o no
    if (updated) {
        System.out.println("Libro actualizado exitosamente.");
    } else {
        System.out.println("No se pudo actualizar el libro.");
    }
}


}
