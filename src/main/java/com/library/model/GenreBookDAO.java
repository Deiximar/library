package com.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.library.config.DBManager;

public class GenreBookDAO implements GenreBookDAOInterface {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public boolean addGenreBook(int genreId, int bookId) {
    boolean state = false;

    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String insertQuery = "INSERT INTO genders_books (id_book, id_gender) VALUES (?,?)";
        preparedStatement = connect.prepareStatement(insertQuery);
        preparedStatement.setInt(1, bookId);
        preparedStatement.setInt(2, genreId);
        int rowsAffected = preparedStatement.executeUpdate();
        state = rowsAffected > 0;
      } else {
        System.out.println("Conexión a la base de datos fallida");
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());

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