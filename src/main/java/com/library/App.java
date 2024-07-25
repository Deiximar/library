package com.library;

import java.util.Scanner;



public class App {
    public static void main(String[] args) {

      ////  BookDAO model = new BookDAO();

       // Book book = new Book("Una historia de Shimotsuma",
            //    "Shimotsuma Monogatari sigue a Miko, una chica lolita, y Yuichi, un joven común, en Shimotsuma. A pesar de sus diferencias, desarrollan una amistad profunda que les ayuda a crecer y comprenderse.",
            //    "9788494746017");

//model.addBook(book);

Scanner scanner = new Scanner(System.in);
BookDAO bookDAO = new BookDAO();


        System.out.println("\nBiblioteca de todos\n¿Qué desea realizar? (Selecione un número)");
        System.out.println(
                " 1. Mostrar todos los libros\n 2. Añadir un libro\n 3. Editar un libro\n 4. Eliminar un libro\n 5. Realizar una búsqueda\n 6.Salir\n");

        int option = scanner.nextInt();

        while (option > 6 || option < 1) {
            switch (option) {
                case 1:
                    // showAllBooks();
                    break;

                case 2:
                    // addBook();
                    break;

                case 3:
                editBook(bookDAO, scanner);
                    break;

                case 4:
                    // deleteBook();
                    break;

                case 5:
                    // searchBook();
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
        }
        
    

 // Implementación de editBook usando bookDAO
 private static void editBook(BookDAO bookDAO, Scanner scanner) {
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
    boolean updated = bookDAO.updateBook(bookToUpdate);

    // Informar al usuario si la actualización fue exitosa o no
    if (updated) {
        System.out.println("Libro actualizado exitosamente.");
    } else {
        System.out.println("No se pudo actualizar el libro.");
    }
}
}
