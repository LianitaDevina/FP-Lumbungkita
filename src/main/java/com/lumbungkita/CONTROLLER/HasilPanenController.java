package com.lumbungkita.CONTROLLER;

import com.lumbungkita.MODEL.HasilPanen;
import com.lumbungkita.DATABASE.HasilPanenDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class HasilPanenController {
    @FXML private TextField tfNamaHasil;
    @FXML private TextField tfJenisHasil;
    @FXML private TextField tfHargaJual;
    @FXML private TextField tfStok;
    @FXML private TextField tfIdPetani;

    @FXML private TableView<HasilPanen> tableHasilPanen;
    @FXML private TableColumn<HasilPanen, Integer> colIdPanen;
    @FXML private TableColumn<HasilPanen, String> colNamaHasil;
    @FXML private TableColumn<HasilPanen, String> colJenisHasil;
    @FXML private TableColumn<HasilPanen, Double> colHarga;
    @FXML private TableColumn<HasilPanen, Integer> colStok;
    @FXML private TableColumn<HasilPanen, Integer> colIdPetani;

    private HasilPanenDAO dao;
    private ObservableList<HasilPanen> dataList;
    private Integer selectedId = null;

    @FXML
    public void initialize() {
        dao = new HasilPanenDAO();
        dataList = FXCollections.observableArrayList();

        // 1. Set Kolom Tabel
        colIdPanen.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getIdPanen()));
        colNamaHasil.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNamaHasilPanen()));
        colJenisHasil.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getJenisHasilPanen()));
        colHarga.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getHargaJual()));
        colStok.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getStok()));
        colIdPetani.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getIdPetani()));

        // 2. Masukkan List ke Tabel
        tableHasilPanen.setItems(dataList);

        // 3. Load Data Awal
        loadData();

        // 4. Listener Klik Tabel
        tableHasilPanen.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                fillForm(newVal);
            }
        });
    }

    private void loadData() {
        dataList.clear();
        List<HasilPanen> hasilDB = dao.getAllHasilPanen();
        dataList.addAll(hasilDB);
        tableHasilPanen.refresh();
    }

    @FXML
    private void handleTambah() {
        if (isValidInput()) {
            HasilPanen hp = new HasilPanen(
                tfNamaHasil.getText(),
                tfJenisHasil.getText(),
                Double.parseDouble(tfHargaJual.getText()),
                Integer.parseInt(tfStok.getText()),
                Integer.parseInt(tfIdPetani.getText())
            );
            
            dao.addHasilPanenById(hp);
            
            loadData();
            handleClear();
            showAlert("Berhasil", "Data berhasil ditambahkan.");
        }
    }

    @FXML
    private void handleUbah() {
        if (selectedId == null) {
            showAlert("Peringatan", "Pilih data di tabel dulu!");
            return;
        }
        if (isValidInput()) {
            HasilPanen hp = new HasilPanen(
                selectedId,
                tfNamaHasil.getText(),
                tfJenisHasil.getText(),
                Double.parseDouble(tfHargaJual.getText()),
                Integer.parseInt(tfStok.getText()),
                Integer.parseInt(tfIdPetani.getText())
            );
            
            dao.updateHasilPanen(hp);
            
            loadData();
            handleClear();
            showAlert("Berhasil", "Data berhasil diubah.");
        }
    }

    @FXML
    private void handleHapus() {
        if (selectedId == null) {
            showAlert("Peringatan", "Pilih data di tabel dulu!");
            return;
        }
        
        dao.deleteHasilPanen(selectedId);
        
        loadData();
        handleClear();
        showAlert("Berhasil", "Data berhasil dihapus.");
    }

    @FXML
    private void handleClear() {
        tfNamaHasil.clear();
        tfJenisHasil.clear();
        tfHargaJual.clear();
        tfStok.clear();
        tfIdPetani.clear();
        selectedId = null;
        tableHasilPanen.getSelectionModel().clearSelection();
    }

    private void fillForm(HasilPanen hp) {
        selectedId = hp.getIdPanen();
        tfNamaHasil.setText(hp.getNamaHasilPanen());
        tfJenisHasil.setText(hp.getJenisHasilPanen());
        tfHargaJual.setText(String.valueOf(hp.getHargaJual()));
        tfStok.setText(String.valueOf(hp.getStok()));
        tfIdPetani.setText(String.valueOf(hp.getIdPetani()));
    }

    private boolean isValidInput() {
        try {
            if (tfNamaHasil.getText().isEmpty()) {
                showAlert("Input Error", "Nama Hasil Panen tidak boleh kosong.");
                return false;
            }
            Double.parseDouble(tfHargaJual.getText());
            Integer.parseInt(tfStok.getText());
            Integer.parseInt(tfIdPetani.getText());
            return true;
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Harga, Stok, dan ID Petani harus berupa angka!");
            return false;
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}