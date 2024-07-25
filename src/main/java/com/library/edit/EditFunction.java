package com.library.edit;

import com.library.Book; 
import com.library.BookDAO; 

public class EditFunction {
    private BookDAO bookDAO;

    public EditFunction() {
        bookDAO = new BookDAO();
    }

    public void updateBook(Book book) {
        if (book != null) {
            bookDAO.updateBook(book);
        } else {
            System.out.println("The book to update is null.");
        }
    }

    // Método para actualizar un libro dado su id, título, descripción e ISBN
    public void updateBookDetails(int id, String title, String description, String isbnCode) {
        Book book = new Book(title, description, isbnCode);
        book.setIdBook(id);
        updateBook(book);
    }
}
