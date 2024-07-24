/* package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return Cconnection.getConnection();
    }

    // Método para obtener todos los libros
    public List<Book> getAllBooks() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setIdBook(resultSet.getInt("id_book"));
                book.setTitle(resultSet.getString("title"));
                book.setDescription(resultSet.getString("description"));
                book.setIsbnCode(resultSet.getString("isbn_code"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Método para buscar libros en función del tipo de búsqueda
    public List<Book> searchBooks(String searchTerm, String searchType) {
        String query = "";
        
        // Construir la consulta SQL basada en el tipo de búsqueda
        switch (searchType) {
            case "author":
                query = "SELECT b.id_book, b.title, b.description, b.isbn_code " +
                        "FROM books b " +
                        "JOIN authors_books ab ON b.id_book = ab.book_id " +
                        "JOIN authors a ON ab.author_id = a.id_author " +
                        "WHERE a.first_name ILIKE ? OR a.last_name ILIKE ?";
                break;
            case "title":
                query = "SELECT * FROM books WHERE title ILIKE ?";
                break;
            case "gendre":
                query = "SELECT b.id_book, b.title, b.description, b.isbn_code " +
                        "FROM books b " +
                        "JOIN books_genres bg ON b.id_book = bg.book_id " +
                        "JOIN genres g ON bg.genre_id = g.id_genre " +
                        "WHERE g.name ILIKE ?";
                break;
            default:
                System.out.println("Tipo de búsqueda no válido");
                return List.of(); // Retorna una lista vacía en lugar de null
        }

        List<Book> books = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String searchTermWithWildcards = "%" + searchTerm + "%";
            statement.setString(1, searchTermWithWildcards);

            // Si la búsqueda es por autor, añadimos un segundo parámetro
            if (searchType.equals("author")) {
                statement.setString(2, searchTermWithWildcards);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setIdBook(resultSet.getInt("id_book"));
                    book.setTitle(resultSet.getString("title"));
                    book.setDescription(resultSet.getString("description"));
                    book.setIsbnCode(resultSet.getString("isbn_code"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}
 */

 package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Método para buscar libros en función del tipo de búsqueda
    public List<Book> searchBooks(String searchTerm, String searchType) {
        List<Book> books = new ArrayList<>();
        String query = "";

        // Construir la consulta SQL basada en el tipo de búsqueda
        switch (searchType) {
            case "author":
                query = "SELECT b.id_book, b.title, b.description, b.isbn_code " +
                        "FROM books b " +
                        "JOIN authors_books ab ON b.id_book = ab.book_id " +
                        "JOIN authors a ON ab.author_id = a.id_author " +
                        "WHERE a.first_name ILIKE ? OR a.last_name ILIKE ?";
                break;
            case "title":
                query = "SELECT * FROM books WHERE title ILIKE ?";
                break;
            case "gendre": // Nota: Asegúrate de que 'gendre' es la columna correcta, puede que sea 'genre'
                query = "SELECT b.id_book, b.title, b.description, b.isbn_code " +
                        "FROM books b " +
                        "JOIN books_genres bg ON b.id_book = bg.book_id " +
                        "JOIN genres g ON bg.genre_id = g.id_genre " +
                        "WHERE g.name ILIKE ?";
                break;
            default:
                System.out.println("Tipo de búsqueda no válido");
                return books;
        }

        // Ejecutar la consulta SQL
        try (Connection connection = Cconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String searchTermWithWildcards = "%" + searchTerm + "%";
            statement.setString(1, searchTermWithWildcards);

            // Si la búsqueda es por autor, añadimos un segundo parámetro
            if (searchType.equals("author")) {
                statement.setString(2, searchTermWithWildcards);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setIdBook(0);(resultSet.getInt("id_book"));
                    book.setTitle(resultSet.getString("title"));
                    book.setDescription(resultSet.getString("description"));
                    book.setIsbnCode(resultSet.getString("isbn_code"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}
