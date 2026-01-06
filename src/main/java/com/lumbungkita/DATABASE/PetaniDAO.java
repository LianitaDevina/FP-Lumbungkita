package com.lumbungkita.DATABASE;

import com.lumbungkita.MODEL.Petani;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetaniDAO {

    // method untuk mendapatkan semua data petani
    public List<Petani> getAllPetani() {
        List<Petani> list = new ArrayList<>();
        String query = "SELECT * FROM petani";

        // blok try untuk mengelola sumber daya database
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // perulangan untuk menambahkan data ke list
            while (rs.next()) {
                list.add(new Petani(
                    rs.getInt("id_petani"),
                    rs.getString("nama_petani"),
                    rs.getString("alamat_petani"),
                    rs.getString("nomor_hp_petani")
                ));
            }

            // blok catch untuk menangani SQLException
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // method untuk menambahkan data petani
    public boolean addPetani(Petani p) {
        String query = "INSERT INTO petani (nama_petani, alamat_petani, nomor_hp_petani) VALUES (?, ?, ?)";

        // blok try untuk mengelola sumber daya database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, p.getNamaPetani());
            pstmt.setString(2, p.getAlamatPetani());
            pstmt.setString(3, p.getNomorHpPetani());
            return pstmt.executeUpdate() > 0;

        // blok catch untuk menangani SQLException
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // method untuk memperbarui data petani
    public boolean updatePetani(Petani p) {
        String query = "UPDATE petani SET nama_petani=?, alamat_petani=?, nomor_hp_petani=? WHERE id_petani=?";

        // blok try untuk mengelola sumber daya database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, p.getNamaPetani());
            pstmt.setString(2, p.getAlamatPetani());
            pstmt.setString(3, p.getNomorHpPetani());
            pstmt.setInt(4, p.getIdPetani());
            return pstmt.executeUpdate() > 0;

        // blok catch untuk menangani SQLException
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // method untuk menghapus data petani
    public boolean deletePetani(int id) {
        String query = "DELETE FROM petani WHERE id_petani=?";

        // blok try untuk mengelola sumber daya database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        // blok catch untuk menangani SQLException
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}