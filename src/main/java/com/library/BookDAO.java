package com.library;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.ResultSet;

public class BookDAO {

  public int addBook(Book book) {
    int bookId = -1;
    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;
    try {
      connect = connection.setConnection();

      if (connect != null) {
        String insertQuery = "INSERT INTO books (title, description, isbn_code) VALUES (?,?,?)";

        preparedStatement = connect.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getDescription());
        preparedStatement.setString(3, book.getCodeISBN());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
          ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
          if (generatedKeys.next()) {
            bookId = generatedKeys.getInt(1);
          }
        }

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

    return bookId;
  }
}
