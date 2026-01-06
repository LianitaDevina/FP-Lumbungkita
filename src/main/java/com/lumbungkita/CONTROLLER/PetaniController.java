package com.lumbungkita.CONTROLLER;

import com.lumbungkita.MODEL.Petani;
import com.lumbungkita.DATABASE.PetaniDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PetaniController {
    @FXML
    private Button btnTambah;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnHapus;

    // menghubungkan elemen UI dari FXML
    @FXML private TableView<Petani> tabelPetani;
    @FXML private TableColumn<Petani, Integer> kolomID;
    @FXML private TableColumn<Petani, String> kolomNama;
    @FXML private TableColumn<Petani, String> kolomAlamat;
    @FXML private TableColumn<Petani, String> kolomNomorHP;

    @FXML private TextField tfNama;
    @FXML private TextField tfAlamat;
    @FXML private TextField tfNomorHP;

    // membuat instance PetaniDAO dan list untuk tabel
    private PetaniDAO petaniDAO = new PetaniDAO();
    private ObservableList<Petani> listPetani = FXCollections.observableArrayList();

    // method inisialisasi controller
    @FXML
    public void initialize() {
        // mengatur cell value factory untuk kolom tabel
        kolomID.setCellValueFactory(new PropertyValueFactory<>("idPetani"));
        kolomNama.setCellValueFactory(new PropertyValueFactory<>("namaPetani"));
        kolomAlamat.setCellValueFactory(new PropertyValueFactory<>("alamatPetani"));
        kolomNomorHP.setCellValueFactory(new PropertyValueFactory<>("nomorHpPetani"));

        // muat data ke tabel
        refreshTabel();

        // menangani pemilihan baris di tabel
        tabelPetani.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                tfNama.setText(newVal.getNamaPetani());
                tfAlamat.setText(newVal.getAlamatPetani());
                tfNomorHP.setText(newVal.getNomorHpPetani());
            }
        });
    }

    // method untuk menyegarkan data di tabel
    private void refreshTabel() {
        listPetani.clear();
        listPetani.addAll(petaniDAO.getAllPetani());
        tabelPetani.setItems(listPetani);
    }

    // method untuk membersihkan form input
    private void bersihkanForm() {
        tfNama.clear();
        tfAlamat.clear();
        tfNomorHP.clear();
        tabelPetani.getSelectionModel().clearSelection();
    }

    // method untuk menangani aksi tambah, edit, dan hapus
    @FXML
    void handleTambah(ActionEvent event) {
        // validasi input nama tidak boleh kosong
        if (tfNama.getText().isEmpty()) {
            showAlert("Error", "Nama tidak boleh kosong!");
            return;
        }

        // membuat objek Petani baru dan menambahkannya ke database
        Petani p = new Petani(tfNama.getText(), tfAlamat.getText(), tfNomorHP.getText());
        if (petaniDAO.addPetani(p)) {
            showAlert("Sukses", "Data berhasil ditambah!");
            refreshTabel();
            bersihkanForm();
        }
    }

    // method untuk menangani aksi edit
    @FXML
    void handleEdit(ActionEvent event) {
        // mengambil data petani yang dipilih di tabel
        Petani selected = tabelPetani.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Pilih data di tabel dulu!");
            return;
        }
        selected.setNamaPetani(tfNama.getText());
        selected.setAlamatPetani(tfAlamat.getText());
        selected.setNomorHpPetani(tfNomorHP.getText());

        // memperbarui data di database
        if (petaniDAO.updatePetani(selected)) {
            showAlert("Sukses", "Data berhasil diubah!");
            refreshTabel();
            bersihkanForm();
        }
    }

    // method untuk menangani aksi hapus
    @FXML
    void handleHapus(ActionEvent event) {
        Petani selected = tabelPetani.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "Pilih data yang ingin dihapus!");
            return;
        }
        if (petaniDAO.deletePetani(selected.getIdPetani())) {
            showAlert("Sukses", "Data berhasil dihapus!");
            refreshTabel();
            bersihkanForm();
        } else {
            showAlert("Gagal", "Tidak bisa menghapus (Mungkin data sedang dipakai transaksi).");
        }
    }

    // method untuk menampilkan alert
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}