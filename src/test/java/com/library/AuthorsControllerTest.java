package com.library;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.library.controller.AuthorsController;
import com.library.model.Author;
import com.library.model.AuthorDAOInterface;

public class AuthorsControllerTest {
    @Mock
    private AuthorDAOInterface authorDAOInterface;

    @InjectMocks
    private AuthorsController authorsController;

    @Before
    public void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        // Given
        Author author1 = new Author("Gabriela", "Wiener");
        Author author2 = new Author("Milan", "Kundera");

        List<Author> expectedAuthors = Arrays.asList(author1, author2);

        when(authorDAOInterface.getAllAuthors()).thenReturn(expectedAuthors);

        // When
        List<Author> actualAuthors = authorsController.getAllAuthors();

        // Then
        assertEquals(actualAuthors, expectedAuthors);
        verify(authorDAOInterface, times(1)).getAllAuthors();
    }

    @Test
    public void testAddAuthor() {
        // Given
        Author newAuthor = new Author("Gabriela", "Wiener");
        int expectedId = 12;

        when(authorDAOInterface.addAuthor(newAuthor)).thenReturn(expectedId);

        // When
        int actualId = authorsController.addAuthor(newAuthor);

        // Then
        assertEquals(expectedId, actualId);
        verify(authorDAOInterface, times(1)).addAuthor(newAuthor);
    }
}
