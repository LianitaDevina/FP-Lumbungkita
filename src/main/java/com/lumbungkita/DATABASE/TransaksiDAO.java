package com.lumbungkita.DATABASE;

import com.lumbungkita.MODEL.DetailTransaksi;
import java.sql.*;
import java.util.List;

public class TransaksiDAO {

    public boolean simpanTransaksi(int idPembeli, double totalHarga, List<DetailTransaksi> listDetail) {
        
        // 1. QUERY HEADER
        String sqlHeader = "INSERT INTO transaksi (id_pembeli, total_harga_transaksi) VALUES (?, ?)";
        
        // 2. QUERY DETAIL
        String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_panen, jumlah_pembelian, harga_saat_transaksi) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement psHeader = null;
        PreparedStatement psDetail = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Mulai Transaksi

            // --- TAHAP 1: SIMPAN HEADER ---
            psHeader = conn.prepareStatement(sqlHeader, Statement.RETURN_GENERATED_KEYS);
            psHeader.setInt(1, idPembeli);
            psHeader.setDouble(2, totalHarga); // Masuk ke total_harga_transaksi
            
            int affectedRows = psHeader.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan header transaksi.");
            }

            // Ambil ID Transaksi yang baru dibuat
            int idTransaksiBaru = 0;
            try (ResultSet rs = psHeader.getGeneratedKeys()) {
                if (rs.next()) {
                    idTransaksiBaru = rs.getInt(1);
                } else {
                    throw new SQLException("Gagal mengambil ID Transaksi baru.");
                }
            }

            // --- TAHAP 2: SIMPAN DETAIL ---
            psDetail = conn.prepareStatement(sqlDetail);
            
            for (DetailTransaksi dt : listDetail) {
                psDetail.setInt(1, idTransaksiBaru);            // id_transaksi
                psDetail.setInt(2, dt.getIdPanen());            // id_panen
                psDetail.setInt(3, dt.getJumlahPembelian());    // jumlah_pembelian
                psDetail.setDouble(4, dt.getHargaSaatTransaksi()); // harga_saat_transaksi
                psDetail.addBatch();
            }
            
            psDetail.executeBatch(); 

            conn.commit(); // Menyimpan Permanen
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            if (psHeader != null) try { psHeader.close(); } catch (SQLException e) {}
            if (psDetail != null) try { psDetail.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }
}