package com.lumbungkita.CONTROLLER;

import com.lumbungkita.MODEL.Pembeli;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.lumbungkita.DATABASE.DatabaseConnection;

import java.sql.*;

public class PembeliController {

    @FXML private TextField txtNama;
    @FXML private ComboBox<String> cbTipe;
    @FXML private TextField txtNoHp;
    @FXML private TableView<Pembeli> tablePembeli;
    @FXML private TableColumn<Pembeli, Integer> colId;
    @FXML private TableColumn<Pembeli, String> colNama;
    @FXML private TableColumn<Pembeli, String> colTipe;
    @FXML private TableColumn<Pembeli, String> colNoHp;

    private ObservableList<Pembeli> listPembeli = FXCollections.observableArrayList();
    private Pembeli selectedPembeli;

    @FXML
    public void initialize() {
        // 1. Setup Kolom Tabel agar sesuai dengan atribut Class Pembeli
        colId.setCellValueFactory(new PropertyValueFactory<>("idPembeli"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaPembeli"));
        colTipe.setCellValueFactory(new PropertyValueFactory<>("tipePembeli"));
        colNoHp.setCellValueFactory(new PropertyValueFactory<>("nomorHpPembeli"));

        // 2. Isi ComboBox Tipe Pembeli
        cbTipe.setItems(FXCollections.observableArrayList("Umum", "Reseller"));

        // 3. Load Data dari Database
        loadData();

        // 4. Listener saat baris tabel diklik (untuk mengisi form otomatis)
        tablePembeli.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedPembeli = newSelection;
                txtNama.setText(newSelection.getNamaPembeli());
                cbTipe.setValue(newSelection.getTipePembeli());
                txtNoHp.setText(newSelection.getNomorHpPembeli());
            }
        });
    }

    // --- READ ---
    private void loadData() {
        listPembeli.clear();
        String query = "SELECT * FROM pembeli";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                listPembeli.add(new Pembeli(
                        rs.getInt("id_pembeli"),
                        rs.getString("nama_pembeli"),
                        rs.getString("tipe_pembeli"),
                        rs.getString("nomor_hp_pembeli")
                ));
            }
            tablePembeli.setItems(listPembeli);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal mengambil data: " + e.getMessage());
        }
    }

    // --- CREATE ---
    @FXML
    void handleAdd(ActionEvent event) {
        if (!validateInput()) return;

        String query = "INSERT INTO pembeli (nama_pembeli, tipe_pembeli, nomor_hp_pembeli) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, txtNama.getText());
            pstmt.setString(2, cbTipe.getValue());
            pstmt.setString(3, txtNoHp.getText());
            pstmt.executeUpdate();

            loadData();
            handleClear(null);
            showAlert("Sukses", "Data berhasil ditambahkan!");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Gagal menambah data: " + e.getMessage());
        }
    }

    // --- UPDATE ---
    @FXML
    void handleUpdate(ActionEvent event) {
        if (selectedPembeli == null) {
            showAlert("Peringatan", "Pilih data di tabel terlebih dahulu!");
            return;
        }
        if (!validateInput()) return;

        String query = "UPDATE pembeli SET nama_pembeli=?, tipe_pembeli=?, nomor_hp_pembeli=? WHERE id_pembeli=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, txtNama.getText());
            pstmt.setString(2, cbTipe.getValue());
            pstmt.setString(3, txtNoHp.getText());
            pstmt.setInt(4, selectedPembeli.getIdPembeli());
            pstmt.executeUpdate();

            loadData();
            handleClear(null);
            showAlert("Sukses", "Data berhasil diperbarui!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETE ---
    @FXML
    void handleDelete(ActionEvent event) {
        if (selectedPembeli == null) {
            showAlert("Peringatan", "Pilih data yang ingin dihapus!");
            return;
        }

        String query = "DELETE FROM pembeli WHERE id_pembeli=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, selectedPembeli.getIdPembeli());
            pstmt.executeUpdate();

            loadData();
            handleClear(null);
            showAlert("Sukses", "Data berhasil dihapus!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- CLEAR FORM ---
    @FXML
    void handleClear(ActionEvent event) {
        txtNama.clear();
        txtNoHp.clear();
        cbTipe.setValue(null);
        selectedPembeli = null;
        tablePembeli.getSelectionModel().clearSelection();
    }

    // Validasi Input Kosong
    private boolean validateInput() {
        if (txtNama.getText().isEmpty() || cbTipe.getValue() == null || txtNoHp.getText().isEmpty()) {
            showAlert("Validasi", "Semua kolom harus diisi!");
            return false;
        }
        return true;
    }

    // Helper untuk menampilkan Alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}