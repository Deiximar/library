package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String username = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        String bdName = "library";
        String url = "jdbc:postgresql://localhost:5432/" + bdName;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conectado a la base de datos de PostgreSQL");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            while (resultSet.next()) {
                int id = resultSet.getInt("id_book");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String ISBN_code = resultSet.getString("isbn_code");

                System.out.println("id: " + id + ", titulo: " + title + ", descripcion: " + description
                        + ", codigo ISBN: " + ISBN_code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
