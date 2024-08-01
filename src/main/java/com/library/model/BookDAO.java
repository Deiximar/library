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
import java.sql.SQLException;

public class BookDAO implements BookDAOInterface {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public List<Book> getAllBooks() {
    List<Book> books = new ArrayList<>();

    String query = "SELECT * FROM books";
    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(query);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Book book = new Book();
        book.setIdBook(resultSet.getInt("id_book"));
        book.setTitle(resultSet.getString("title"));
        book.setDescription(resultSet.getString("description"));
        book.setCodeISBN(resultSet.getString("isbn_code"));
        books.add(book);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return books;
  }

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

  public boolean updateBookByField(String field, String fieldValue, int bookId) {
    boolean state = false;
    try {
      Connection connection = DBManager.initConnection();

      if (connection != null) {
        String updateQuery = "UPDATE books SET " + field + " = ? WHERE id_book = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
          preparedStatement.setString(1, fieldValue);
          preparedStatement.setInt(2, bookId);

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

  public Book getBookById(int bookId) {
    Book book = null;
    try {
      Connection connection = DBManager.initConnection();
      if (connection != null) {
        String selectQuery = "SELECT * FROM books WHERE id_book = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
          preparedStatement.setInt(1, bookId);
          try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
              String title = resultSet.getString("title");
              String description = resultSet.getString("description");
              String isbnCode = resultSet.getString("isbn_code");
              book = new Book(title, description, isbnCode);
              book.setIdBook(bookId);
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
