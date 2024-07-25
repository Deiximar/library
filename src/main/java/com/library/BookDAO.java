package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;

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
}
