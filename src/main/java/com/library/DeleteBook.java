package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import com.library.config.DBManager;

public class DeleteBook {
    private Connection connect;
    private PreparedStatement preparedStatement;

    public void deleteBook(Scanner scanner) {
        ArrayList<Integer> bookIdList = showAllBooks();
        Boolean shouldAskId = true;
        int idBook;

        while (shouldAskId) {
            System.out.println("Escribe el ID del libro que deseas eliminar");
            idBook = scanner.nextInt();
            scanner.nextLine();
            if (idBook > 0 && bookIdList.contains(idBook)) {
                shouldAskId = false;
                System.out.println("El ID es válido");
                deleteAuthorBookByBookId(idBook);
                deleteGenresBookByBookId(idBook);
                deleteBookById(idBook);
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

    public int deleteBookById(int id) {
        String query = "DELETE FROM books WHERE id_book = ?";
        int resultRowsDeleted = 0;
        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setInt(1, id);
            resultRowsDeleted = preparedStatement.executeUpdate();
            if (resultRowsDeleted > 0) {
                System.out.println("Su libro se ha eliminado exitosamente!");
            } else {
                System.out.println("El libro no se ha podido eliminar.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultRowsDeleted;
    }

    public int deleteAuthorBookByBookId(int id) {
        String query = "DELETE FROM authors_books WHERE id_book = ?";
        int resultRowsDeleted = 0;
        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setInt(1, id);
            resultRowsDeleted = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultRowsDeleted;
    }

    public int deleteGenresBookByBookId(int id) {
        String query = "DELETE FROM genres_books WHERE id_book = ?";
        int resultRowsDeleted = 0;
        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setInt(1, id);
            resultRowsDeleted = preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultRowsDeleted;
    }
}
