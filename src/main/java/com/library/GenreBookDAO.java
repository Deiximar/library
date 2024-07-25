package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GenreBookDAO {

  public boolean addGenreBook(int genreId, int bookId) {
    boolean state = false;
    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;

    try {
      connect = connection.setConnection();
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