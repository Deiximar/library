package com.library.model;

import java.sql.Statement;

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
   public boolean updateBook(Book book) {
        boolean state = false;
        try {
          Connection connection = DBManager.initConnection();

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
        try {
          Connection connection = DBManager.initConnection();
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
