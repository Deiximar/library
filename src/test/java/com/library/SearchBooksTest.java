package com.library;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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

    private static MockedStatic<DBManager> mockedDBManager;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        if (mockedDBManager == null) {
            mockedDBManager = mockStatic(DBManager.class);
            mockedDBManager.when(DBManager::initConnection).thenReturn(mockConnection);
        }
    }

    @After
    public void tearDown() {
        if (mockedDBManager != null) {
            mockedDBManager.close();
            mockedDBManager = null;
        }
    }

    private void setupResultSet(String title, String author, String isbn, String description) throws Exception {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id_book")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn(title);
        when(mockResultSet.getString("author")).thenReturn(author);
        when(mockResultSet.getString("isbn_code")).thenReturn(isbn);
        when(mockResultSet.getString("description")).thenReturn(description);
    }

    @Test
    public void testSearchBookByTitle() throws Exception {
        String testTitle = "Japan Book";
        String query = "SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE b.title = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        setupResultSet("Japan Book", "Author Name", "1234569780", "Book to learn Japan");

        Scanner scanner = new Scanner(testTitle);
        int count = searchBooks.searchBookByTitle(scanner);

        assertEquals(1, count);
        verify(mockPreparedStatement).setString(1, testTitle);
    }

    @Test
    public void testSearchBookByAuthor() throws Exception {
        String testAuthor = "J.R.R. Tolkien";
        String[] nameParts = testAuthor.split(" ");
        String authorName = nameParts[0];
        String authorLastName = nameParts.length > 1 ? nameParts[1] : "";

        String query = "SELECT b.id_book, b.title, b.description, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "INNER JOIN authors_books ab ON b.id_book = ab.id_book " +
                "INNER JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE a.name = ? OR a.last_name = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        setupResultSet("Book by J.R.R. Tolkien", "J.R.R. Tolkien", "098765555", null);

        Scanner scanner = new Scanner(testAuthor);
        int count = searchBooks.searchBookByAuthor(scanner);

        assertEquals(1, count);
        verify(mockPreparedStatement).setString(1, authorName);
        verify(mockPreparedStatement).setString(2, authorLastName);
    }

   @Test
    public void testSearchBookByGenre() throws Exception {
        String testGenre = "Comic";

        String query = "SELECT b.id_book, b.title, b.isbn_code, a.name || ' ' || a.last_name AS author " +
                "FROM books b " +
                "INNER JOIN genres_books gb ON b.id_book = gb.id_book " +
                "INNER JOIN genres g ON gb.id_genre = g.id_genre " +
                "LEFT JOIN authors_books ab ON b.id_book = ab.id_book " +
                "LEFT JOIN authors a ON ab.id_author = a.id_author " +
                "WHERE g.genre = ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        setupResultSet("Comic Book", "Author Name", "11223355894", null);

        Scanner scanner = new Scanner(testGenre);
        int count = searchBooks.searchBookByGenre(scanner);

        assertEquals(1, count);
        verify(mockPreparedStatement).setString(1, testGenre);
    }
}