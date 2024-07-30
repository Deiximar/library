package com.library;

import java.util.Scanner;

import com.library.controller.BooksController;
import com.library.model.BookDAO;
import com.library.model.BookDAOInterface;
import com.library.view.BookView;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        BookDAOInterface bookDao = new BookDAO();
        BooksController booksController = new BooksController(bookDao);
        BookView bookView = new BookView(booksController);

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
                        bookView.addBook(scanner);
                        break;
                    case 3:
                        // editBook();
                        break;
                    case 4:
                        DeleteBook deleteBook = new DeleteBook();
                        deleteBook.deleteBook(scanner);
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
}
