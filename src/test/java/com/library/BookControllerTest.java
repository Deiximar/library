package com.library;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.library.controller.BooksController;
import com.library.model.Book;
import com.library.model.BookDAOInterface;

public class BookControllerTest {

   
    @Mock
    private BookDAOInterface bookDAOInterface;

    @InjectMocks
    private BooksController booksController;

    @Before
    public void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        // Given
        Book book1 = new Book("Cien años de soledad", "Narra la historia de la familia Buenaventura", "Code ISBN 9780307474728");
        Book book2 = new Book("La Rueda del tiempo: El ojo del mundo", "Narra la vida de Rand Al`Thor y sus amihos", "Code ISBN 978445007006");
        List<Book> expectedBooks = Arrays.asList(book1, book2);

        when(bookDAOInterface.getAllBooks()).thenReturn(expectedBooks);

        // When
        List<Book> actualBooks = booksController.getAllBooks();

        // Then
        assertEquals(expectedBooks, actualBooks);
        verify(bookDAOInterface, times(1)).getAllBooks();
    }

    @Test
    public void testAddBook() {
        // Given
        Book newBook = new Book("Harry potter y el prisionero de Azkaban", "Narra la historia de un mago de 13 años", "Code ISBN 9788419275820");
        int expectedId = 12;

        when(bookDAOInterface.addBook(newBook)).thenReturn(expectedId);

        // When
        int actualId = booksController.addBook(newBook);

        // Then
        assertEquals(expectedId, actualId);
        verify(bookDAOInterface, times(1)).addBook(newBook);
    }

    @Test
    public void testDeleteBookById() {
        // Given
        int idBookToDelete = 3;

        // When
        booksController.deleteBookById(idBookToDelete);

        // Then
        verify(bookDAOInterface, times(1)).deleteBookById(idBookToDelete);
    }
}