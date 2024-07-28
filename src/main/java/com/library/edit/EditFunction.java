/*package com.library.edit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;


public class EditFunction {
    private static BookDAO bookDAO = new BookDAO();

    */




    /* Mostrar todos los libros

    List<Book> books = bookDAO.getAllBooks();
if (books.isEmpty()) {
    System.out.println("No hay libros en la base de datos.");
 } return;
 
 */



/*  Elegir un libro para editar

System.out.println("Seleccione el ID del libro que desea editar:");
for (Book book : books) {
    System.out.println("ID: " + book.getIdBook() + ", Título: " + book.getTitle());
}
*/



/* Buscar libro elegido

 int bookId = scanner.nextInt();
scanner.nextLine(); // Limpiar el buffer

Book bookToEdit = bookDAO.findBookById(bookId);
 /* 

 /*  Editar la informacion del libro
System.out.print("Nuevo título (actual: " + bookToEdit.getTitle() + "): ");
String newTitle = scanner.nextLine();
if (!newTitle.isEmpty()) {
    bookToEdit.setTitle(newTitle);
}
System.out.print("Nueva descripción (actual: " + bookToEdit.getDescription() + "): ");
String newDescription = scanner.nextLine();
if (!newDescription.isEmpty()) {
    bookToEdit.setDescription(newDescription);
}
System.out.print("Nuevo código ISBN (actual: " + bookToEdit.getCodeISBN() + "): ");
String newISBN = scanner.nextLine();
if (!newISBN.isEmpty()) {
    bookToEdit.setCodeISBN(newISBN);
}

*/



 /*  Actualizar libro en la base de datos

boolean isUpdated = bookDAO.updateBook(bookToEdit);
if (isUpdated) {
    System.out.println("Libro actualizado con éxito.");
} else {
    System.out.println("Fallo al actualizar el libro.");
} else {
     System.out.println("No se encontró el libro con el ID especificado.");
        }
scanner.close();

    */




 
  
  
   /* public static void updateBook(Book book) {
        if (book != null) {
            bookDAO.updateBook(book);
        } else {
            System.out.println("The book to update is null.");
        }
    }
*/

    /*  Método para actualizar un libro dado su id, título, descripción e ISBN
    public void updateBookDetails(int id, String title, String description, String isbnCode) {
        Book book = new Book(title, description, isbnCode);
        book.setIdBook(id);
        updateBook(book);
    }
}
*/
