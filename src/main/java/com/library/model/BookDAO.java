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

public class BookDAO implements BookDAOInterface {
  private Connection connect;
  private PreparedStatement preparedStatement;

  public List<Book> getAllBooks() {
    List<Book> books = new ArrayList<>();

    // String query = "SELECT "
    // + "b.id AS book_id, "
    // + "b.title, "
    // + "b.isbn_code, "
    // + "a.name AS author_name, "
    // + "a.last_name AS author_last_name, "
    // + "g.genre "
    // + "FROM "
    // + "public.books b "
    // + "LEFT JOIN "
    // + "public.authors_books ab ON b.id = ab.id_book "
    // + "LEFT JOIN "
    // + "public.authors a ON ab.id_author = a.id "
    // + "LEFT JOIN "
    // + "public.genres_books gb ON b.id = gb.id_book "
    // + "LEFT JOIN "
    // + "public.genres g ON gb.id_genre = g.id;";

    String query = "SELECT b.id, b.title, b.description, b.isbn_code " +
        "FROM books b ";

    try {
      connect = DBManager.initConnection();
      preparedStatement = connect.prepareStatement(query);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        Book book = new Book();
        book.setIdBook(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setDescription(resultSet.getString("description"));
        book.setCodeISBN(resultSet.getString("isbn_code"));

        // Author author = new Author();
        // author.setIdAuthor(resultSet.getInt("id_author"));
        // author.setName(resultSet.getString("author_name"));
        // author.setLastName(resultSet.getString("last_name"));
        // book.setAuthor(author);

        // Genre genre = new Genre();
        // genre.setIdGenre(resultSet.getInt("id_genre"));
        // genre.setGenre(resultSet.getString("genre"));
        // book.setGenre(genre);

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
