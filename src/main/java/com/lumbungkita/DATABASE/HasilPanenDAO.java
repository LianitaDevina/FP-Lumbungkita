package com.lumbungkita.DATABASE;

import com.lumbungkita.MODEL.HasilPanen;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HasilPanenDAO {

    // method pencarian id
    public HasilPanen getHasilPanenById(int id) {
        String sql = "SELECT * FROM hasil_panen WHERE id_panen = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new HasilPanen(
                    rs.getInt("id_panen"),
                    rs.getString("nama_hasil_panen"),
                    rs.getString("jenis_hasil_panen"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok"),
                    rs.getInt("id_petani")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // controller hasil panen (CRUD)

    // Mengambil Semua Data
    public List<HasilPanen> getAllHasilPanen() {
        List<HasilPanen> list = new ArrayList<>();
        String sql = "SELECT * FROM hasil_panen";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new HasilPanen(
                    rs.getInt("id_panen"),
                    rs.getString("nama_hasil_panen"),
                    rs.getString("jenis_hasil_panen"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok"),
                    rs.getInt("id_petani")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Menambah Data
    public void addHasilPanenById(HasilPanen hp) {
        String sql = "INSERT INTO hasil_panen (nama_hasil_panen, jenis_hasil_panen, harga_jual, stok, id_petani) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hp.getNamaHasilPanen());
            ps.setString(2, hp.getJenisHasilPanen());
            ps.setDouble(3, hp.getHargaJual());
            ps.setInt(4, hp.getStok());
            ps.setInt(5, hp.getIdPetani());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Data
    public void updateHasilPanen(HasilPanen hp) {
        String sql = "UPDATE hasil_panen SET nama_hasil_panen=?, jenis_hasil_panen=?, harga_jual=?, stok=?, id_petani=? WHERE id_panen=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hp.getNamaHasilPanen());
            ps.setString(2, hp.getJenisHasilPanen());
            ps.setDouble(3, hp.getHargaJual());
            ps.setInt(4, hp.getStok());
            ps.setInt(5, hp.getIdPetani());
            ps.setInt(6, hp.getIdPanen());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hapus Data
    public void deleteHasilPanen(int id) {
        String sql = "DELETE FROM hasil_panen WHERE id_panen=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // pengurangan stok
    public void kurangiStok(int idPanen, int jumlahDibeli) {
        String sql = "UPDATE hasil_panen SET stok = stok - ? WHERE id_panen = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, jumlahDibeli); 
            ps.setInt(2, idPanen);
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Gagal mengurangi stok: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
