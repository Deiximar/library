package com.library;

import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.library.config.DBManager;
import com.library.controller.BooksController;
import com.library.model.Book;
import com.library.view.BookView;

public class BookViewTest {

    @InjectMocks
    private BookView bookView;

    @Mock
    private BooksController booksController;

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

    @Test
    public void testSearchBookByTitle() throws Exception {
        String testTitle = "Japan Book";
        Book book = new Book("Japan Book", "Book to learn Japan", "1234569780", testTitle);
        List<Book> books = Arrays.asList(book);

        when(booksController.searchBooksByTitle(testTitle)).thenReturn(books);

        String input = "1\nJapan Book\n4\n";  
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        bookView.searchBook(scanner);

        verify(booksController).searchBooksByTitle(testTitle);
    }

    @Test
    public void testSearchBookByAuthor() throws Exception {
        String testAuthor = "J.R.R. Tolkien";
        Book book = new Book("Book by J.R.R. Tolkien", "A book by Tolkien", "098765555", testAuthor);
        List<Book> books = Arrays.asList(book);
        String[] nameParts = testAuthor.split(" ");
        String authorName = nameParts[0];
        String authorLastName = nameParts.length > 1 ? nameParts[1] : "";

        when(booksController.searchBooksByAuthor(authorName, authorLastName)).thenReturn(books);

        String input = "2\nJ.R.R. Tolkien\n4\n";  
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        bookView.searchBook(scanner);

        verify(booksController).searchBooksByAuthor(authorName, authorLastName);
    }

    @Test
    public void testSearchBookByGenre() throws Exception {
        String testGenre = "Comic";
        Book book = new Book("Comic Book", "A comic book", "11223355894", testGenre);
        List<Book> books = Arrays.asList(book);

        when(booksController.searchBooksByGenre(testGenre)).thenReturn(books);

        String input = "3\nComic\n4\n";  
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        bookView.searchBook(scanner);

        verify(booksController).searchBooksByGenre(testGenre);
    }
}
