package com.lumbungkita.CONTROLLER;

import com.lumbungkita.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class DashboardController {

    // menghubungkan BorderPane dari FXML
    @FXML
    private BorderPane mainContainer;

    // method untuk memuat halaman ke dalam BorderPane
    private void loadPage(String page) {
        Parent root = null;

        // blok try-catch untuk memuat file FXML
        try {
            // PERBAIKAN DI SINI:
            // Tambahkan "/com/lumbungkita/" agar mencari dari folder resources utama
            // bukan dari folder CONTROLLER
            root = FXMLLoader.load(getClass().getResource("/com/lumbungkita/" + page + ".fxml"));
        } catch (IOException e) {
            System.err.println("Gagal memuat halaman: " + page);
            e.printStackTrace();
        }
        
        // Cek jika root tidak null baru dipasang
        if (root != null) {
            mainContainer.setCenter(root);
        }
    }

    // method untuk menampilkan halaman Petani
    @FXML
    void showPetani(ActionEvent event) {
        loadPage("PetaniView");
    }

    // method untuk menampilkan halaman Pembeli
    @FXML
    void showPembeli(ActionEvent event) {
        System.out.println("Menu Pembeli diklik (File belum ada)");
    }
    
    // method untuk menampilkan halaman Transaksi
    @FXML
    void showTransaksi(ActionEvent event) {
        System.out.println("Menu Transaksi diklik (File belum ada)");
    }

    // method untuk menampilkan halaman Laporan
    @FXML
    void showLaporan(ActionEvent event) {
        loadPage("LaporanView");
    }

    // method untuk logout dan kembali ke layar login
    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        App.setRoot("LoginView");
    }
}