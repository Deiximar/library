package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                    // editBook();
                    break;
                case 4:
                    // deleteBook();
                    break;
                case 5:
                    // searchBook();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Seleccione un numero del 1 - 6\n");
                    option = scanner.nextInt();
            }
        }

        scanner.close();
        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        String bdName = "library";
        String url = "jdbc:postgresql://localhost:5432/" + bdName;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conectado a la base de datos de PostgreSQL");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            while (resultSet.next()) {
                int id = resultSet.getInt("id_book");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String ISBN_code = resultSet.getString("isbn_code");

                System.out.println("id: " + id + ", titulo: " + title + ", descripcion: " + description
                        + ", codigo ISBN: " + ISBN_code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
