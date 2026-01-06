package com.lumbungkita.DATABASE;

import com.lumbungkita.MODEL.LaporanStok;
import com.lumbungkita.MODEL.LaporanPenjualan;
import com.lumbungkita.MODEL.LaporanPerProduk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaporanDAO {

    // metode untuk mengambil laporan dari database
    public List<LaporanStok> getLaporanStok() {
        List<LaporanStok> list = new ArrayList<>();
        // sql query untuk mengambil data laporan stok dengan join ke tabel petani
        String query = "SELECT h.nama_hasil_panen, h.jenis_hasil_panen, h.harga_jual, h.stok, p.nama_petani " +
                       "FROM hasil_panen h " +
                       "JOIN petani p ON h.id_petani = p.id_petani";

        // try-with-resources untuk koneksi database
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // perulangan untuk mengambil data dari result set
            while (rs.next()) {
                list.add(new LaporanStok(
                    rs.getString("nama_hasil_panen"),
                    rs.getString("jenis_hasil_panen"),
                    rs.getDouble("harga_jual"),
                    rs.getInt("stok"),
                    rs.getString("nama_petani")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LaporanPenjualan> getLaporanPenjualan() {
        List<LaporanPenjualan> list = new ArrayList<>();
        
        String query = "SELECT t.id_transaksi, t.tanggal_waktu_transaksi, p.nama_pembeli, t.total_harga_transaksi, " +
                       "SUM(d.jumlah_pembelian) as total_barang " + 
                       "FROM transaksi t " +
                       "JOIN pembeli p ON t.id_pembeli = p.id_pembeli " +
                       "JOIN detail_transaksi d ON t.id_transaksi = d.id_transaksi " + 
                       "GROUP BY t.id_transaksi " + 
                       "ORDER BY t.tanggal_waktu_transaksi DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new LaporanPenjualan(
                    rs.getInt("id_transaksi"),
                    rs.getTimestamp("tanggal_waktu_transaksi"),
                    rs.getString("nama_pembeli"),
                    rs.getDouble("total_harga_transaksi"),
                    rs.getInt("total_barang")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // metode untuk mengambil laporan per produk dari database
    public List<LaporanPerProduk> getLaporanPerProduk() {
        List<LaporanPerProduk> list = new ArrayList<>();
        String query = "SELECT h.nama_hasil_panen, " +
                       "SUM(d.jumlah_pembelian) as total_terjual, " +
                       "h.harga_jual, " +
                       "SUM(d.jumlah_pembelian * d.harga_saat_transaksi) as total_pendapatan " +
                       "FROM detail_transaksi d " +
                       "JOIN hasil_panen h ON d.id_panen = h.id_panen " +
                       "GROUP BY h.id_panen, h.nama_hasil_panen, h.harga_jual";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new LaporanPerProduk(
                    rs.getString("nama_hasil_panen"),
                    rs.getInt("total_terjual"),
                    rs.getDouble("harga_jual"),
                    rs.getDouble("total_pendapatan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
