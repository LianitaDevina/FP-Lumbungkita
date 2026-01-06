package com.lumbungkita.MODEL;
import java.sql.Timestamp;

public class LaporanPenjualan {
    // atribut laporan penjualan
    private int idTransaksi;
    private Timestamp tanggal;
    private String namaPembeli;
    private double totalHarga;

    // konstruktor LaporanPenjualan
    public LaporanPenjualan(int idTransaksi, Timestamp tanggal, String namaPembeli, double totalHarga) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.namaPembeli = namaPembeli;
        this.totalHarga = totalHarga;
    }

    // getter untuk atribut
    public int getIdTransaksi() { return idTransaksi; }
    public Timestamp getTanggal() { return tanggal; }
    public String getNamaPembeli() { return namaPembeli; }
    public double getTotalHarga() { return totalHarga; }
}