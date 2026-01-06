package com.lumbungkita.CONTROLLER;

import com.lumbungkita.DATABASE.HasilPanenDAO;
import com.lumbungkita.DATABASE.PembeliDAO; // <--- Import Baru
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
    private PembeliDAO pembeliDAO; // <--- Tambahan Variabel
    private ObservableList<KeranjangItem> listKeranjang;
    private double totalBayarBersih = 0;

    @FXML
    public void initialize() {
        try {
            // Inisialisasi DAO & List
            transaksiDAO = new TransaksiDAO();
            hasilPanenDAO = new HasilPanenDAO();
            pembeliDAO = new PembeliDAO(); // <--- Inisialisasi DAO Pembeli
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

    // --- ACTION: TOMBOL TAMBAH (TIDAK BERUBAH) ---
    @FXML
    private void handleTambah() {
        if (tfIdPanen.getText().isEmpty() || tfJumlah.getText().isEmpty()) {
            showAlert("Peringatan", "ID Panen dan Jumlah harus diisi.");
            return;
        }

        try {
            int idPanen = Integer.parseInt(tfIdPanen.getText());
            int jumlahBeli = Integer.parseInt(tfJumlah.getText());

            if (jumlahBeli <= 0) {
                showAlert("Peringatan", "Jumlah pembelian harus lebih dari 0.");
                return;
            }

            HasilPanen panen = hasilPanenDAO.getHasilPanenById(idPanen);
            
            if (panen == null) {
                showAlert("Tidak Ditemukan", "ID Hasil Panen " + idPanen + " tidak ada di database.");
                return;
            }

            if (jumlahBeli > panen.getStok()) {
                showAlert("Stok Kurang", "Stok " + panen.getNamaHasilPanen() + " sisa: " + panen.getStok());
                return;
            }

            KeranjangItem item = new KeranjangItem(
                panen.getIdPanen(),
                panen.getNamaHasilPanen(),
                panen.getHargaJual(),
                jumlahBeli
            );

            listKeranjang.add(item);
            hitungTotal();

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

    // --- ACTION: HAPUS ITEM (TIDAK BERUBAH) ---
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

    // --- ACTION: CHECKOUT (LOGIKA DISKON DITAMBAHKAN DI SINI) ---
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

            // --- LOGIKA DISKON DIMULAI ---
            
            // 1. Cek Kategori Pembeli
            String kategori = pembeliDAO.getTipePembeli(idPembeli);
            double totalAkhirTransaksi = totalBayarBersih; // Default harga normal
            boolean isReseller = false;

            // 2. Jika Reseller, potong 15%
            if (kategori != null && kategori.equalsIgnoreCase("reseller")) {
                isReseller = true;
                double diskon = totalBayarBersih * 0.15;
                totalAkhirTransaksi = totalBayarBersih - diskon;
            }

            // 3. Konfirmasi ke User (Opsional, agar terlihat sistem bekerja)
            String pesanKonfirmasi = "Total: Rp " + String.format("%,.2f", totalBayarBersih);
            if (isReseller) {
                pesanKonfirmasi += "\nDiskon Reseller (15%): -Rp " + String.format("%,.2f", (totalBayarBersih * 0.15));
                pesanKonfirmasi += "\nTotal Bayar: Rp " + String.format("%,.2f", totalAkhirTransaksi);
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Pembayaran");
            alert.setHeaderText("Status Pembeli: " + kategori);
            alert.setContentText(pesanKonfirmasi + "\n\nLanjutkan transaksi?");
            if (alert.showAndWait().get() != ButtonType.OK) {
                return; // Batal jika user menekan Cancel
            }
            // --- LOGIKA DISKON SELESAI ---


            List<DetailTransaksi> listDetail = new ArrayList<>();
            // Siapkan Data Detail
            for (KeranjangItem item : listKeranjang) {
                listDetail.add(new DetailTransaksi(0, item.getQuantity(), item.getSubtotal(), 0, item.getIdPanen()));
            }

            // 4. Simpan Transaksi (Kirim totalAkhirTransaksi yg sudah didiskon)
            boolean sukses = transaksiDAO.simpanTransaksi(idPembeli, totalAkhirTransaksi, listDetail);

            if (sukses) {
                // Update Stok
                for (KeranjangItem item : listKeranjang) {
                    hasilPanenDAO.kurangiStok(item.getIdPanen(), item.getQuantity());
                }
                
                showAlert("Berhasil", "Transaksi berhasil! Total Bayar: Rp " + String.format("%,.2f", totalAkhirTransaksi));
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