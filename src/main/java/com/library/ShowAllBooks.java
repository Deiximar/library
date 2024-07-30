package com.library;

import com.library.model.BookDAO;
import com.library.model.Book;
import java.util.List;

public class ShowAllBooks {
    public void displayAllBooks() {
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.getAllBooks();

        System.out.printf("%-10s %-50s %-20s \n", "ID", "Title", "ISBN",
                "Genre");
        // System.out.printf("%-10s %-20s %-20s %-20s %-15s %-15s\n", "ID", "Title",
        // "Author", "Last Name", "ISBN",
        // "Genre");
        for (Book book : books) {
            System.out.printf("%-10d %-50s %-20s \n",
                    book.getIdBook(),
                    book.getTitle(),
                    book.getCodeISBN());
            // ut.printf("%-10d %-20s %-20s %-20s %-15s %-15s\n",
            // book.getIdBook(),
            // book.getTitle(),
            // book.getAuthor().getName(),
            // book.getAuthor().getLastName(),
            // book.getCodeISBN(),
            // book.getGenre().getGenre());
        }
    }
}
