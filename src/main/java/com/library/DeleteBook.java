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
        String RESET = "\033[0m";
        int idBook;

        while (shouldAskId) {
            if (bookIdList.isEmpty()) {
                System.out.println("\n\033[31mNo existen libros para eliminar" + RESET);
                break;
            }
            System.out.println("\n\033[33mEscribe el ID del libro que deseas eliminar o escribe 0 para cancelar\n");
            idBook = scanner.nextInt();
            scanner.nextLine();
            if (idBook > 0 && bookIdList.contains(idBook)) {
                shouldAskId = false;
                System.out.println("\033[32mEl ID es válido\n"+ RESET);
                deleteAuthorBookByBookId(idBook);
                deleteGenresBookByBookId(idBook);
                deleteBookById(idBook);
            } else {
                System.out.println("\n\033[31mEl ID introducido no es válido" + RESET);
                shouldAskId = false;
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
        String RESET = "\033[0m";
        try {
            connect = DBManager.initConnection();
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setInt(1, id);
            resultRowsDeleted = preparedStatement.executeUpdate();
            if (resultRowsDeleted > 0) {
                System.out.println("\n\033[32mSu libro se ha eliminado exitosamente!" + RESET);
            } else {
                System.out.println("\n\033[31mEl libro no se ha podido eliminar.");
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
