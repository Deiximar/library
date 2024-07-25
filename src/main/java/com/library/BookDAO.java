package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO {

  public boolean addBook(Book book) {
    boolean state = false;
    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;
    try {
      connect = connection.setConnection();

      if (connect != null) {
        String insertQuery = "INSERT INTO books (title, description, isbn_code) VALUES (?,?,?)";

        preparedStatement = connect.prepareStatement(insertQuery);

        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getDescription());
        preparedStatement.setString(3, book.getCodeISBN());

        int rowsAffected = preparedStatement.executeUpdate();

        state = rowsAffected > 0;
        // System.out.println("Filas insertadas: " + rowsAffected);

        // String selectQuery = "SELECT * FROM books";
        // ResultSet resultSet = connect.createStatement().executeQuery(selectQuery);

        // while (resultSet.next()) {
        // System.out.println("ID: " + resultSet.getInt("id"));
        // System.out.println("Title: " + resultSet.getString("title"));
        // System.out.println("Description: " + resultSet.getString("description"));
        // System.out.println("ISBN Code: " + resultSet.getString("isbn_code"));
        // }

      } else {
        System.out.println("Conexión a la base de datos fallida");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      ;
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connect != null) {
          connect.close();
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());

      }
    }

    return state;
  }

   public boolean updateBook(Book book) {
        boolean state = false;
        try (Connection connection = new Cconnection().setConnection()) {
            if (connection != null) {
                String updateQuery = "UPDATE books SET title = ?, description = ?, isbn_code = ? WHERE id_book = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, book.getTitle());
                    preparedStatement.setString(2, book.getDescription());
                    preparedStatement.setString(3, book.getCodeISBN());
                    preparedStatement.setInt(4, book.getIdBook());
                    int rowsAffected = preparedStatement.executeUpdate();
                    state = rowsAffected > 0;
                }
            } else {
                System.out.println("Conexión a la base de datos fallida");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }

    public Book getBookById(int id) {
        Book book = null;
        try (Connection connection = new Cconnection().setConnection()) {
            if (connection != null) {
                String selectQuery = "SELECT * FROM books WHERE id_book = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                    preparedStatement.setInt(1, id);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String title = resultSet.getString("title");
                            String description = resultSet.getString("description");
                            String isbnCode = resultSet.getString("isbn_code");
                            book = new Book(title, description, isbnCode);
                            book.setIdBook(id);
                        }
                    }
                }
            } else {
                System.out.println("Conexión a la base de datos fallida");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
  }



