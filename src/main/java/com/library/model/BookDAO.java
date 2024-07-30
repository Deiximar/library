package com.library.model;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.ResultSet;

public class BookDAO {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public int addBook(Book book) {
    int bookId = -1;

    try {
      connect = DBManager.initConnection();

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

  public List<Book> getBooks() {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM books";
    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(sql);
      ResultSet result = preparedStatement.executeQuery();

      int id = result.getInt("id_book");
      String title = result.getString("title");
      String author = result.getString("author");
      String isbn = result.getString("isbn_code");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      DBManager.closeConnection();
    }
  }

}
