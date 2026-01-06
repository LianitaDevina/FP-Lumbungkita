package com.lumbungkita.DATABASE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    // fungsi untuk mengecek login admin
    public boolean cekLogin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        // blok try untuk mengelola sumber daya database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
            
        // blok catch untuk menangani SQLException
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}