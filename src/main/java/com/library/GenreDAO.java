package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GenreDAO {

  public int addGenre(Genre genre) {
    int genreId = findGenreByName(genre.getGenre());
    if (genreId != -1) {
      return genreId;
    }

    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;

    try {
      connect = connection.setConnection();
      if (connect != null) {
        String insertQuery = "INSERT INTO genders(gender) VALUES (?)";
        preparedStatement = connect.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, genre.getGenre());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
          ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
          if (generatedKeys.next()) {
            genreId = generatedKeys.getInt(1);
          }
        }
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
    return genreId;
  }

  public int findGenreByName(String genre) {
    int genreId = -1;
    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connect = connection.setConnection();
      if (connect != null) {
        String selectQuery = "SELECT id_gender FROM genders WHERE gender = ?";
        preparedStatement = connect.prepareStatement(selectQuery);
        preparedStatement.setString(1, genre);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          genreId = resultSet.getInt("id_gender");
        }
      } else {
        System.out.println("Conexión a la base de datos fallida");
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());

    } finally {
      try {
        if (resultSet != null) {
          resultSet.close();
        }
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
    return genreId;
  }

}
