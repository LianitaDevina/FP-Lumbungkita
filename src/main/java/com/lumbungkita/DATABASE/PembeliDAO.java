package com.lumbungkita.DATABASE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PembeliDAO {

    // Method untuk mengambil kategori pembeli (Umum / Reseller)
    public String getKategoriPembeli(int idPembeli) {
        String kategori = "Umum"; // Default
        String sql = "SELECT kategori FROM pembeli WHERE id_pembeli = ?"; 
        // Pastikan nama tabel 'pembeli' dan kolom 'kategori' sesuai database kamu

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPembeli);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                kategori = rs.getString("kategori");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kategori;
    }
}