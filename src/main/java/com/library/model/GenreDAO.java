package com.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.library.config.DBManager;

public class GenreDAO implements GenreDAOInterface {

  private Connection connect;
  private PreparedStatement preparedStatement;

  public int addGenre(Genre genre) {
    int genreId = findGenreByName(genre.getGenre());
    if (genreId != -1) {
      return genreId;
    }

    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String insertQuery = "INSERT INTO genres(genre) VALUES (?)";
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
    ResultSet resultSet = null;

    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String selectQuery = "SELECT id_genre FROM genres WHERE genre = ?";
        preparedStatement = connect.prepareStatement(selectQuery);
        preparedStatement.setString(1, genre);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          genreId = resultSet.getInt("id_genre");
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
