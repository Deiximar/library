package com.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;

public class AuthorBookDAO implements AuthorBookDAOInterface {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public boolean addAuthorBook(int authorId, int bookId) {
    boolean state = false;

    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String insertQuery = "INSERT INTO authors_books (id_book, id_author) VALUES (?,?)";
        preparedStatement = connect.prepareStatement(insertQuery);
        preparedStatement.setInt(1, bookId);
        preparedStatement.setInt(2, authorId);
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

  public int deleteAuthorBookByBookId(int id) {
    String query = "DELETE FROM authors_books WHERE id_book = ?";
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

  public int deleteAuthorBookByBookId(int bookId, int authorId) {
    String query = "DELETE FROM authors_books WHERE id_book = ? AND id_author = ?";
    int resultRowsDeleted = 0;
    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(query);

      preparedStatement.setInt(1, bookId);
      preparedStatement.setInt(2, authorId);
      resultRowsDeleted = preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return resultRowsDeleted;
  }

  public List<Author> getAuthorsByBookId(int bookId) {
    List<Author> authors = new ArrayList<>();

    ResultSet resultSet = null;
    try {
      connect = DBManager.initConnection();
      if (connect != null) {
        String selectQuery = "SELECT * FROM authors a JOIN authors_books ab ON ab.id_author = a.id_author WHERE ab.id_book = ?";
        preparedStatement = connect.prepareStatement(selectQuery);
        preparedStatement.setInt(1, bookId);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          Author author = new Author();
          author.setIdAuthor(resultSet.getInt("id_author"));
          author.setName(resultSet.getString("name"));
          author.setLastName(resultSet.getString("last_name"));
          authors.add(author);
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
    return authors;

  }
}