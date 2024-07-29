package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteBook {

    public void deleteBook() {
        ArrayList<Integer> bookIdList =  showAllBooks();
        Boolean shouldAskId = true;
        Scanner scanner = new Scanner(System.in);

        while(shouldAskId) {
            System.out.println("Escribe el ID del libro que deseas eliminar");
            int idBook = scanner.nextInt();
            if (idBook > 0 && bookIdList.contains(idBook)) {
                shouldAskId = false;
                System.out.println("El ID es válido");
                deleteAuthorBookByBookId(idBook);
                deleteBookById(idBook);
            } else {
                System.out.println("El ID introducido no es válido");
            }
        }
        scanner.close();
    }



    private ArrayList<Integer> showAllBooks() {
        ArrayList<Integer> idList = new ArrayList<>(); 

        String query = "SELECT * FROM books";
        try (Connection connect = new Cconnection().setConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_book");
                String title = resultSet.getString("title");
                idList.add(id);

                System.out.println("ID: " + id + ", Título: " + title );   
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return idList;
    }

    private int deleteBookById(int id) {
        String query = "DELETE FROM books WHERE id_book = ?";
        int resultRowsDeleted = 0;
        try (Connection connect = new Cconnection().setConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultRowsDeleted = preparedStatement.executeUpdate();
            if (resultRowsDeleted > 0) {
                System.out.println("Su libro se ha eliminado exitosamente!");
            } else {
                System.out.println("El libro no se ha podido eliminar.");
            }
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        return resultRowsDeleted;
    }

    private int deleteAuthorBookByBookId(int id) {
        String query = "DELETE FROM authors_books WHERE id_book = ?";
        int resultRowsDeleted = 0;
        try (Connection connect = new Cconnection().setConnection();
        PreparedStatement preparedStatement = connect.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultRowsDeleted = preparedStatement.executeUpdate();
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultRowsDeleted;
    }
}

