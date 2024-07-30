package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


import com.library.config.DBManager;

public class EditFunction {
    private Connection connect;
    private PreparedStatement preparedStatement;



public void run() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
    
            System.out.println("Seleccione una opción para editar el libro:");
            System.out.print("1. Mostrar todos los libros");
            System.out.println("2. Editar por título");
            System.out.println("3. Editar por autor");
            System.out.println("4.Editar por género");
            System.out.println("5.Editar por ID");
            System.out.println("6.Editar por ISBN");
            System.out.println("7. Salir");

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 7) {
                System.out.println("Saliendo del programa...");
                break;
            }

            boolean found = false;
            int count = 0;
            switch (option) {
                case 1:
                    count = showAllBooks(scanner);
                    found = count > 0;
                    break;
                case 2:
                    count = editBookByTitle(scanner);
                    found = count > 0;
                    break;
                case 3:
                    count = editBookByAuthor(scanner);
                    found = count > 0;
                    break;
                case 4:
                    count = editBookByGenre(scanner);
                    found = count > 0;
                    break;
                case 5:
                    count = editBookById(scanner);
                    found = count > 0;
                    break;
                case 6:
                    count = editBookByISBN(scanner);
                    found = count > 0;
                    break;
                default:
                    System.out.println("Esta opción no existe: Selecciona la opción de nuevo del 1 al 7:\n");
                    continue;
            }

            if (found) {
                System.out.println("Se han encontrado " + count + " libro(s) con los criterios especificados.");
            } else {
                System.out.println("No se han encontrado libros con los criterios especificados.");
            }
        }
        scanner.close();
    }


    private int showAllBooks(Scanner scanner) {
        return search("SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author", null, true);
    }

    private int editBookByTitle(Scanner scanner) {
        System.out.println("¿Cuál es el título del libro que quiere editar?:");
        String bookTitle = scanner.nextLine();
        return search("SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE b.title = ?", bookTitle, true);
    }

    private int editBookByAuthor(Scanner scanner) {
        System.out.println("¿Quién es el autor del libro que quiere editar?:");
        String authorFullName = scanner.nextLine();
        String[] nameParts = authorFullName.split(" ");
        String authorName = nameParts[0];
        String authorLastName = nameParts.length > 1 ? nameParts[1] : "";
        return search("SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "INNER JOIN authors_books ab ON b.id_book = ab.id_book " +
                "INNER JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE a.name = ? AND a.last_name = ?", authorName, authorLastName, true);
    }

     private int editBookByGenre(Scanner scanner) {
        System.out.println("¿Cuál es el género del libro que quiere editar?:");
        String bookGenre = scanner.nextLine();
        return search("SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "INNER JOIN genders_books gb ON b.id_book = gb.id_book " +
                "INNER JOIN genders g ON gb.id_gender = g.id_gender " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE g.gender = ?", bookGenre, true);
    }

    private int editBookById(Scanner scanner) {
        System.out.println("¿Cuál es el ID del libro que quiere editar?:");
        String bookId = scanner.nextLine();
        return search("SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE b.id_book = ?", bookId, true);
    }


    private int editBookByISBN(Scanner scanner) {
        System.out.println("¿Cuál es el Id del libro que quiere editar?:");
        String bookIsbn = scanner.nextLine();
        return search("SELECT b.id_book, b.title, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "INNER JOIN genders_books gb ON b.id_book = gb.id_book " +
                "INNER JOIN genders g ON gb.id_gender = g.id_gender " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE g.gender = ?", bookIsbn, true);
    }


    private int search(String query, String parameter, boolean includeDescription) {
        int count = 0;
        String format;
        if (includeDescription) {
            format = "| %-10d | %-30s | %-30s | %-40s | %-15s |%n";
        } else {
            format = "| %-10d | %-30s | %-30s | %-15s |%n";
        }

        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            if (parameter != null) {
                preparedStatement.setString(1, parameter);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            // Imprimir encabezado de tabla
            System.out.format(
                    "+------------+--------------------------------+--------------------------------+------------------------------------------+-----------------+%n");
            if (includeDescription) {
                System.out.format(
                        "| ID         | Título                         | Autor                          | Descripción                              | ISBN            |%n");
            } else {
                System.out.format(
                        "| ID         | Título                         | Autor                          | ISBN            |%n");
            }
            System.out.format(
                    "+------------+--------------------------------+--------------------------------+------------------------------------------+-----------------+%n");

            while (resultSet.next()) {
                count++;
                int id = resultSet.getInt("id_book");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn_code");

                // Obtener la descripción solo si está incluida en la consulta
                String description = includeDescription ? resultSet.getString("description") : "";

                if (includeDescription) {
                    System.out.format(format, id, title, author, truncate(description, 40), isbn);
                } else {
                    System.out.format(format, id, title, author, isbn);
                }
            }

            System.out.format(
                    "+------------+--------------------------------+--------------------------------+------------------------------------------+-----------------+%n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Cierre de recursos
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return count;
    }

    private String truncate(String value, int length) {
        if (value.length() <= length) {
            return value;
        } else {
            return value.substring(0, length - 3) + "...";
        }
    }
}