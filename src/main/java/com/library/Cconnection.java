package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class Cconnection {

  Dotenv dotenv = Dotenv.load();
  Connection connection;

  String username = dotenv.get("postgres");
  String password = "1980";
  String bdName = "library";
  String url = "jdbc:postgresql://localhost:5432/" + bdName;

  public Connection setConnection() {

    try {
      connection = DriverManager.getConnection(url, username, password);
      System.out.println("Conectado a la base de datos de PostgreSQL");
    }

    catch (Exception e) {
      e.printStackTrace();
    }

    return connection;
  }
}
