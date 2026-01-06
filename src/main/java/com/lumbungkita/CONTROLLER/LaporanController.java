package com.lumbungkita.CONTROLLER;

import com.lumbungkita.MODEL.LaporanStok;
import com.lumbungkita.MODEL.LaporanPenjualan;
import com.lumbungkita.MODEL.LaporanPerProduk;
import com.lumbungkita.DATABASE.LaporanDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LaporanController {

    // untuk menghubungkan elemen FXML
    @FXML private TableView<LaporanStok> tabelStok;
    @FXML private TableColumn<LaporanStok, String> colStokNama;
    @FXML private TableColumn<LaporanStok, String> colStokJenis;
    @FXML private TableColumn<LaporanStok, Double> colStokHarga;
    @FXML private TableColumn<LaporanStok, Integer> colStokSisa;
    @FXML private TableColumn<LaporanStok, String> colStokPetani;

    // untuk menghubungkan elemen FXML tab Laporan Penjualan
    @FXML private TableView<LaporanPenjualan> tabelPenjualan;
    @FXML private TableColumn<LaporanPenjualan, Integer> colJualID;
    @FXML private TableColumn<LaporanPenjualan, String> colJualTanggal;
    @FXML private TableColumn<LaporanPenjualan, String> colJualPembeli;
    @FXML private TableColumn<LaporanPenjualan, Double> colJualTotal;

    // untuk menghubungkan elemen FXML tab Laporan Per Produk
    @FXML private TableView<LaporanPerProduk> tabelProduk;
    @FXML private TableColumn<LaporanPerProduk, String> colProdukNama;      
    @FXML private TableColumn<LaporanPerProduk, Integer> colProdukTerjual;  
    @FXML private TableColumn<LaporanPerProduk, Double> colProdukHarga;     
    @FXML private TableColumn<LaporanPerProduk, Double> colProdukPendapatan; 

    // DAO untuk mengambil data laporan
    private LaporanDAO laporanDAO = new LaporanDAO();

    // inisialisasi controller
    @FXML
    public void initialize() {
        initTabelStok();
        initTabelPenjualan();
        initTabelProduk();
    }

    // inisialisasi tabel laporan stok  
    private void initTabelStok() {
        colStokNama.setCellValueFactory(new PropertyValueFactory<>("namaBarang"));
        colStokJenis.setCellValueFactory(new PropertyValueFactory<>("jenisBarang"));
        colStokHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colStokSisa.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colStokPetani.setCellValueFactory(new PropertyValueFactory<>("namaPetani"));
        
        tabelStok.setItems(FXCollections.observableArrayList(laporanDAO.getLaporanStok()));
    }

    // inisialisasi tabel laporan penjualan
    private void initTabelPenjualan() {
        colJualID.setCellValueFactory(new PropertyValueFactory<>("idTransaksi"));
        colJualTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colJualPembeli.setCellValueFactory(new PropertyValueFactory<>("namaPembeli"));
        colJualTotal.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

        tabelPenjualan.setItems(FXCollections.observableArrayList(laporanDAO.getLaporanPenjualan()));
    }

    // inisialisasi tabel laporan per produk
    private void initTabelProduk() {
        colProdukNama.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        colProdukTerjual.setCellValueFactory(new PropertyValueFactory<>("totalTerjual"));
        colProdukHarga.setCellValueFactory(new PropertyValueFactory<>("hargaSatuan"));
        colProdukPendapatan.setCellValueFactory(new PropertyValueFactory<>("totalPendapatan"));

        tabelProduk.setItems(FXCollections.observableArrayList(laporanDAO.getLaporanPerProduk()));
    }
}