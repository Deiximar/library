package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthorDAO {

  public int addAuthor(Author author) {
    int authorId = findAuthorByName(author.getName(), author.getLastName());
    if (authorId != -1) {
      return authorId;
    }

    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;

    try {
      connect = connection.setConnection();
      if (connect != null) {
        String insertQuery = "INSERT INTO authors (name, last_name) VALUES (?,?)";
        preparedStatement = connect.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, author.getName());
        preparedStatement.setString(2, author.getLastName());
        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
          ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
          if (generatedKeys.next()) {
            authorId = generatedKeys.getInt(1);
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
    return authorId;
  }

  public int findAuthorByName(String name, String lastName) {
    int authorId = -1;
    Cconnection connection = new Cconnection();
    Connection connect = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connect = connection.setConnection();
      if (connect != null) {
        String selectQuery = "SELECT id_author FROM authors WHERE name = ? AND last_name = ?";
        preparedStatement = connect.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          authorId = resultSet.getInt("id_author");
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
    return authorId;
  }

}
