package com.library.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.config.DBManager;

public class BookDAO implements BookDAOInterface {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public List<Book> getAllBooks() {

    String query = "SELECT * FROM books";
    List<Book> books = new ArrayList<>();

    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(query);

      ResultSet resultSet = preparedStatement.executeQuery();

      // Map<Integer, Book> bookMap = new HashMap<>();

      while (resultSet.next()) {
        Book book = new Book();
        book.setIdBook(resultSet.getInt("id_book"));
        book.setTitle(resultSet.getString("title"));
        book.setDescription(resultSet.getString("description"));
        books.add(book);
      }

      // while (resultSet.next()) {
      // int bookId = resultSet.getInt("id_book");
      // Book book = bookMap.getOrDefault(bookId, new Book());
      // book.setIdBook(bookId);
      // book.setTitle(resultSet.getString("title"));
      // book.setCodeISBN(resultSet.getString("isbn_code"));

      // if (!bookMap.containsKey(bookId)) {
      // book.setAuthors(new ArrayList<>());
      // book.setGenres(new ArrayList<>());
      // bookMap.put(bookId, book);
      // }
      // int authorId = resultSet.getInt("id_author");
      // if (authorId != 0) {
      // Author author = new Author();
      // author.setIdAuthor(authorId);
      // author.setName(resultSet.getString("name"));
      // author.setLastName(resultSet.getString("last_name"));
      // book.getAuthors().add(author);
      // }

      // int genreId = resultSet.getInt("id_genre");
      // if (genreId != 0) {
      // Genre genre = new Genre();
      // genre.setIdGenre(genreId);
      // genre.setGenre(resultSet.getString("genre"));
      // book.getGenres().add(genre);
      // }
      // }
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
}
