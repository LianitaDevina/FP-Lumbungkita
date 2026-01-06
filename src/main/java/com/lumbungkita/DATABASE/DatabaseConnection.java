package com.lumbungkita.DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/koperasi";
    private static final String USER = "root"; 
    private static final String PASSWORD = "SatuTambahSatu2."; 
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: MySQL JDBC Driver tidak ditemukan.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}