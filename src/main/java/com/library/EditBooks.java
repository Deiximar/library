package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.library.config.DBManager;

public class EditBooks {
    private Connection connect;
    private PreparedStatement preparedStatement;



public void EditBooks() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción para editar el libro:");
            System.out.print("1. Mostrar todos los libros");
            System.out.println("2. Editar por título");
            System.out.println("3. Editar por autor");
            System.out.println("4.Editar por género");
            System.out.println("5.Editar por id");
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
    }






       }
}


    

