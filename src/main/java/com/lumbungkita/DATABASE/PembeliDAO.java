package com.lumbungkita.DATABASE;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.lumbungkita.MODEL.Pembeli;

public class PembeliDAO {

    // --- CRUD BIASA (TETAP) ---

    public List<Pembeli> getAllPembeli() {
        List<Pembeli> list = new ArrayList<>();
        String sql = "SELECT * FROM pembeli";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pembeli p = new Pembeli(
                        rs.getInt("id_pembeli"),
                        rs.getString("nama_pembeli"),
                        rs.getString("tipe_pembeli"),
                        rs.getString("nomor_hp_pembeli")
                );
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertPembeli(Pembeli p) {
        String sql = "INSERT INTO pembeli (nama_pembeli, tipe_pembeli, nomor_hp_pembeli) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNamaPembeli());
            ps.setString(2, p.getTipePembeli());
            ps.setString(3, p.getNomorHpPembeli());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePembeli(Pembeli p) {
        String sql = "UPDATE pembeli SET nama_pembeli=?, tipe_pembeli=?, nomor_hp_pembeli=? WHERE id_pembeli=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNamaPembeli());
            ps.setString(2, p.getTipePembeli());
            ps.setString(3, p.getNomorHpPembeli());
            ps.setInt(4, p.getIdPembeli());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePembeli(int id) {
        String sql = "DELETE FROM pembeli WHERE id_pembeli=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- [PENTING] INI METHOD YANG SEBELUMNYA HILANG ---
    public String getTipePembeli(int idPembeli) {
        String tipe = "umum"; // Default
        String sql = "SELECT tipe_pembeli FROM pembeli WHERE id_pembeli = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPembeli);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tipe = rs.getString("tipe_pembeli");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipe;
    }
}