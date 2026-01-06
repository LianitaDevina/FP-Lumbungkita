package com.lumbungkita.MODEL;
import java.sql.Timestamp;

public class LaporanPenjualan {
    // atribut laporan penjualan
    private int idTransaksi;
    private Timestamp tanggal;
    private String namaPembeli;
    private double totalHarga;
    private int totalQty; 

    // konstruktor 
    public LaporanPenjualan(int idTransaksi, Timestamp tanggal, String namaPembeli, double totalHarga, int totalQty) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.namaPembeli = namaPembeli;
        this.totalHarga = totalHarga;
        this.totalQty = totalQty;
    }

    // getter
    public int getIdTransaksi() { return idTransaksi; }
    public Timestamp getTanggal() { return tanggal; }
    public String getNamaPembeli() { return namaPembeli; }
    public double getTotalHarga() { return totalHarga; }
    public int getTotalQty() { return totalQty; } 
}