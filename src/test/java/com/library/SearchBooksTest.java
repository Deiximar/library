package com.library;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.library.config.DBManager;

public class SearchBooksTest {

    @InjectMocks
    private SearchBooks searchBooks;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockStatic(DBManager.class);
        when(DBManager.initConnection()).thenReturn(mockConnection);
    }

 
    @Test
    public void testSearchBookByTitle() throws Exception {
        // Datos de prueba
        String testTitle = "Test Book";
    
        // Consulta SQL
        String query = "SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE b.title = ?";
    
        // Configuración de mocks
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    
        // Configuración del ResultSet para que devuelva los datos esperados
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id_book")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("Test Book");
        when(mockResultSet.getString("author")).thenReturn("Author Name");
        when(mockResultSet.getString("isbn_code")).thenReturn("1234567890");
        when(mockResultSet.getString("description")).thenReturn("This is a test book description");
    
        // Llama al método de prueba
        Scanner scanner = new Scanner(testTitle);
        int count = searchBooks.searchBookByTitle(scanner);
    
        // Verifica el resultado esperado
        assertEquals(1, count);
    
        // Verifica que el parámetro se haya establecido correctamente
        verify(mockPreparedStatement).setString(1, testTitle);
    }

    @Test
    public void testSearchBookByAuthor() throws Exception {
      // Datos de prueba
      String testAuthor = "John Doe";
      String[] nameParts = testAuthor.split(" ");
      String authorName = nameParts[0];
      String authorLastName = nameParts.length > 1 ? nameParts[1] : "";

      // Consulta SQL
      String query = "SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
              "FROM books b " +
              "INNER JOIN authors_books ab ON b.id_book = ab.id_book " +
              "INNER JOIN authors a ON ab.id_author = a.id_author " +
              "WHERE a.name = ? OR a.last_name = ?";

      // Configuración de mocks
      when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
      when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

      // Configuración del ResultSet para que devuelva los datos esperados
      when(mockResultSet.next()).thenReturn(true).thenReturn(false);
      when(mockResultSet.getInt("id_book")).thenReturn(1);
      when(mockResultSet.getString("title")).thenReturn("Book by John Doe");
      when(mockResultSet.getString("author")).thenReturn("John Doe");
      when(mockResultSet.getString("isbn_code")).thenReturn("0987654321");

      // Llama al método de prueba
      Scanner scanner = new Scanner(testAuthor);
      int count = searchBooks.searchBookByAuthor(scanner);

      // Verifica el resultado esperado
      assertEquals(1, count);

      // Verifica que los parámetros se hayan establecido correctamente
      verify(mockPreparedStatement).setString(1, authorName);
      verify(mockPreparedStatement).setString(2, authorLastName);
  }

  @Test
 
  public void testSearchBookByGender() throws Exception {
    // Datos de prueba
    String testGender = "Fiction";

    // Consulta SQL
    String query = "SELECT b.id_book, b.title, b.isbn_code, a.name || ' ' || a.last_name AS author " +
            "FROM books b " +
            "INNER JOIN genders_books gb ON b.id_book = gb.id_book " +
            "INNER JOIN genders g ON gb.id_gender = g.id_gender " +
            "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
            "LEFT JOIN authors a ON ab.id_author = a.id_author " +
            "WHERE g.gender = ?";

    // Configuración de mocks
    when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

    // Configuración del ResultSet para que devuelva los datos esperados
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    when(mockResultSet.getInt("id_book")).thenReturn(1);
    when(mockResultSet.getString("title")).thenReturn("Fiction Book");
    when(mockResultSet.getString("author")).thenReturn("Author Name");
    when(mockResultSet.getString("isbn_code")).thenReturn("1122334455");

    // Llama al método de prueba
    Scanner scanner = new Scanner(testGender);
    int count = searchBooks.searchBookByGenre(scanner);

    // Verifica el resultado esperado
    assertEquals(1, count);

    // Verifica que el parámetro se haya establecido correctamente
    verify(mockPreparedStatement).setString(1, testGender);
}


}