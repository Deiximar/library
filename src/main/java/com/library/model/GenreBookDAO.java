package com.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;

public class GenreBookDAO implements GenreBookDAOInterface {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public boolean addGenreBook(int genreId, int bookId) {
    boolean state = false;

    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String insertQuery = "INSERT INTO genres_books (id_book, id_genre) VALUES (?,?)";
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

  public int deleteGenresBookByBookId(int id) {
    String query = "DELETE FROM genres_books WHERE id_book = ?";
    int resultRowsDeleted = 0;
    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(query);

      preparedStatement.setInt(1, id);
      resultRowsDeleted = preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return resultRowsDeleted;
  }

  public int deleteGenresBookByBookId(int idBook, int idGenre) {
    String query = "DELETE FROM genres_books WHERE id_book = ? AND id_genre = ?";
    int resultRowsDeleted = 0;
    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(query);

      preparedStatement.setInt(1, idBook);
      preparedStatement.setInt(2, idGenre);
      resultRowsDeleted = preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return resultRowsDeleted;
  }

  public List<Genre> getGenresByBookId(int bookId) {
    List<Genre> genres = new ArrayList<>();
    ResultSet resultSet = null;
    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String selectQuery = "SELECT * FROM genres g JOIN genres_books gb ON gb.id_genre = g.id_genre WHERE gb.id_book = ?";
        preparedStatement = connect.prepareStatement(selectQuery);
        preparedStatement.setInt(1, bookId);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          Genre genre = new Genre();
          genre.setIdGenre(resultSet.getInt("id_genre"));
          genre.setGenre(resultSet.getString("genre"));
          genres.add(genre);
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
    return genres;

  }
}