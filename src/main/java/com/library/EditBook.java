package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import com.library.config.DBManager;

public class EditBook {
    private Connection connect;
    private PreparedStatement preparedStatement;

    public void editBook(Scanner scanner) {
        ArrayList<Integer> bookIdList = showAllBooks();
        Boolean shouldAskId = true;
        int idBook;

        while (shouldAskId) {
            System.out.println("Escribe el ID del libro que deseas editar");
            idBook = scanner.nextInt();
            scanner.nextLine();
            if (idBook > 0 && bookIdList.contains(idBook)) {
                shouldAskId = false;
                System.out.println("El ID es válido");

                // Pedir los nuevos datos
                System.out.println("Escribe el nuevo título del libro:");
                String newTitle = scanner.nextLine();
                
                System.out.println("Escribe la nueva descripción del libro:");
                String newDescription = scanner.nextLine();
                
                System.out.println("Escribe el nuevo ISBN del libro:");
                String newIsbn = scanner.nextLine();
                
                System.out.println("Escribe el nuevo nombre del autor:");
                String newAuthorName = scanner.nextLine();
                
                System.out.println("Escribe el nuevo apellido del autor:");
                String newAuthorLastName = scanner.nextLine();

                // Actualizar el libro
                updateBookById(idBook, newTitle, newDescription, newIsbn, newAuthorName, newAuthorLastName);
            } else {
                System.out.println("El ID introducido no es válido");
            }
        }
    }

    public ArrayList<Integer> showAllBooks() {
        ArrayList<Integer> idList = new ArrayList<>();

        String query = "SELECT * FROM books";
        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_book");
                String title = resultSet.getString("title");
                idList.add(id);

                System.out.println("ID: " + id + ", Título: " + title);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return idList;
    }

    public int updateBookById(int id, String title, String description, String isbn, String authorName, String authorLastName) {
        String query = "UPDATE books b " +
                       "INNER JOIN authors_books ab ON b.id_book = ab.id_book " +
                       "INNER JOIN authors a ON ab.id_author = a.id_author " +
                       "SET b.title = ?, b.description = ?, b.isbn_code = ?, a.name = ?, a.last_name = ? " +
                       "WHERE b.id_book = ?";
        int rowsUpdated = 0;
        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, isbn);
            preparedStatement.setString(4, authorName);
            preparedStatement.setString(5, authorLastName);
            preparedStatement.setInt(6, id);

            rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("El libro se ha actualizado exitosamente!");
            } else {
                System.out.println("No se ha podido actualizar el libro.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rowsUpdated;
    }
}
