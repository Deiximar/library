package com.library;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

       // BookDAO model = new BookDAO();
        SearchBooks searchBooks = new SearchBooks();

        /* Book book = new Book("Una historia de Shimotsuma",
                "Shimotsuma Monogatari sigue a Miko, una chica lolita, y Yuichi, un joven común, en Shimotsuma. A pesar de sus diferencias, desarrollan una amistad profunda que les ayuda a crecer y comprenderse.",
                "9788494746017");

        model.addBook(book); */

        Scanner scanner = new Scanner(System.in);

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
                    //addBook();
                    break;
                case 3:
                    // editBook();
                    break;
                case 4:
                    // deleteBook();
                    break;
                case 5:
                    searchBooks.searchBook();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Seleccione un numero del 1 - 6\n");
                    option = scanner.nextInt();
            }
        } while (option > 6 || option < 1);
        scanner.close();
    }
}
     
            // Statement statement = connection.createStatement();
            // ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            // while (resultSet.next()) {
            // int id = resultSet.getInt("id_book");
            // String title = resultSet.getString("title");
            // String description = resultSet.getString("description");
            // String ISBN_code = resultSet.getString("isbn_code");

            // System.out.println("id: " + id + ", titulo: " + title + ", descripcion: " +
            // description
            // + ", codigo ISBN: " + ISBN_code);
            // }
   