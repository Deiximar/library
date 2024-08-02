package com.library.config;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class DBManager {

  private static Dotenv dotenv = Dotenv.load();
  private static Connection connection;

  private static final String username = dotenv.get("DB_USER");
  private static final String password = dotenv.get("DB_PASSWORD");
  private static final String bdName = "library";
  private static final String url = "jdbc:postgresql://localhost:5432/" + bdName;

  public static Connection initConnection() {
    try {
      connection = DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static void closeConnection() {
    try {
      connection.close();
      System.out.println("Has salido correctamente del sistema");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}