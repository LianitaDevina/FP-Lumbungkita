package com.lumbungkita.CONTROLLER;

import com.lumbungkita.DATABASE.HasilPanenDAO;
import com.lumbungkita.DATABASE.TransaksiDAO;
import com.lumbungkita.MODEL.DetailTransaksi;
import com.lumbungkita.MODEL.HasilPanen;
import com.lumbungkita.MODEL.KeranjangItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.List;

public class TransaksiController {

    // --- KOMPONEN FXML ---
    @FXML private TextField tfIdPembeli;
    @FXML private TextField tfIdPanen;
    @FXML private TextField tfJumlah;
    @FXML private Label lblTotalBayar;

    // Tabel Keranjang
    @FXML private TableView<KeranjangItem> tblKeranjang;
    @FXML private TableColumn<KeranjangItem, Integer> colIdBrg;
    @FXML private TableColumn<KeranjangItem, String> colNamaBrg;
    @FXML private TableColumn<KeranjangItem, Double> colHargaBrg;
    @FXML private TableColumn<KeranjangItem, Integer> colQty;
    @FXML private TableColumn<KeranjangItem, Double> colSubtotal;

    // --- VARIABEL DATA ---
    private TransaksiDAO transaksiDAO;
    private HasilPanenDAO hasilPanenDAO;
    private ObservableList<KeranjangItem> listKeranjang;
    private double totalBayarBersih = 0;

    @FXML
    public void initialize() {
        try {
            // Inisialisasi DAO & List
            transaksiDAO = new TransaksiDAO();
            hasilPanenDAO = new HasilPanenDAO();
            listKeranjang = FXCollections.observableArrayList();

            // Setup Kolom Tabel
            colIdBrg.setCellValueFactory(new PropertyValueFactory<>("idPanen"));
            colNamaBrg.setCellValueFactory(new PropertyValueFactory<>("namaPanen"));
            colHargaBrg.setCellValueFactory(new PropertyValueFactory<>("hargaSatuan"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

            // Hubungkan List ke Tabel
            tblKeranjang.setItems(listKeranjang);
            
            // Set Total Awal
            lblTotalBayar.setText("Rp 0,00");

        } catch (Exception e) {
            System.err.println("Error saat inisialisasi TransaksiController: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- ACTION: TOMBOL TAMBAH ---
    @FXML
    private void handleTambah() {
        // 1. Validasi Input Kosong
        if (tfIdPanen.getText().isEmpty() || tfJumlah.getText().isEmpty()) {
            showAlert("Peringatan", "ID Panen dan Jumlah harus diisi.");
            return;
        }

        try {
            int idPanen = Integer.parseInt(tfIdPanen.getText());
            int jumlahBeli = Integer.parseInt(tfJumlah.getText());

            // 2. Validasi Jumlah Positif
            if (jumlahBeli <= 0) {
                showAlert("Peringatan", "Jumlah pembelian harus lebih dari 0.");
                return;
            }

            // 3. Cek Database
            HasilPanen panen = hasilPanenDAO.getHasilPanenById(idPanen);
            
            if (panen == null) {
                showAlert("Tidak Ditemukan", "ID Hasil Panen " + idPanen + " tidak ada di database.");
                return;
            }

            // 4. Cek Stok
            if (jumlahBeli > panen.getStok()) {
                showAlert("Stok Kurang", "Stok " + panen.getNamaHasilPanen() + " sisa: " + panen.getStok());
                return;
            }

            // 5. Masukkan ke Keranjang
            KeranjangItem item = new KeranjangItem(
                panen.getIdPanen(),
                panen.getNamaHasilPanen(),
                panen.getHargaJual(),
                jumlahBeli
            );

            listKeranjang.add(item);
            hitungTotal();

            // 6. Reset Input Barang
            tfIdPanen.clear();
            tfJumlah.clear();
            tfIdPanen.requestFocus(); 

        } catch (NumberFormatException e) {
            showAlert("Error Input", "ID Panen dan Jumlah harus berupa Angka.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error Sistem", e.getMessage());
        }
    }

    // --- ACTION: TOMBOL HAPUS ITEM ---
    @FXML
    private void handleHapusItem() {
        KeranjangItem selected = tblKeranjang.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listKeranjang.remove(selected);
            hitungTotal();
        } else {
            showAlert("Info", "Silakan pilih barang di tabel untuk dihapus.");
        }
    }

    // --- ACTION: TOMBOL CHECKOUT ---
    @FXML
    private void handleCheckout() {
        if (listKeranjang.isEmpty()) {
            showAlert("Kosong", "Keranjang belanja masih kosong.");
            return;
        }
        if (tfIdPembeli.getText().isEmpty()) {
            showAlert("Peringatan", "Masukkan ID Pembeli terlebih dahulu.");
            return;
        }

        try {
            int idPembeli = Integer.parseInt(tfIdPembeli.getText());
            List<DetailTransaksi> listDetail = new ArrayList<>();

            // Konversi dari ObservableList ke List<DetailTransaksi>
            for (KeranjangItem item : listKeranjang) {
                // Constructor DetailTransaksi
                listDetail.add(new DetailTransaksi(0, item.getQuantity(), item.getSubtotal(), 0, item.getIdPanen()));
            }

            // Simpan ke Database
            boolean sukses = transaksiDAO.simpanTransaksi(idPembeli, totalBayarBersih, listDetail);

            if (sukses) {
                showAlert("Berhasil", "Transaksi berhasil disimpan!");
                resetForm();
            } else {
                showAlert("Gagal", "Terjadi kesalahan saat menyimpan ke database.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "ID Pembeli harus berupa angka.");
        }
    }

    private void hitungTotal() {
        totalBayarBersih = 0;
        for (KeranjangItem item : listKeranjang) {
            totalBayarBersih += item.getSubtotal();
        }
        lblTotalBayar.setText("Rp " + String.format("%,.2f", totalBayarBersih));
    }

    private void resetForm() {
        listKeranjang.clear();
        tfIdPembeli.clear();
        tfIdPanen.clear();
        tfJumlah.clear();
        hitungTotal();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}