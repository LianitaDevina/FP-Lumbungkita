package com.lumbungkita.MODEL;

public class LaporanPerProduk {
    // atribut laporan per produk
    private String namaProduk;
    private int totalTerjual;
    private double hargaSatuan;
    private double totalPendapatan;

    // konstruktor LaporanPerProduk
    public LaporanPerProduk(String namaProduk, int totalTerjual, double hargaSatuan, double totalPendapatan) {
        this.namaProduk = namaProduk;
        this.totalTerjual = totalTerjual;
        this.hargaSatuan = hargaSatuan;
        this.totalPendapatan = totalPendapatan;
    }

    // getter untuk atribut
    public String getNamaProduk() { return namaProduk; }
    public int getTotalTerjual() { return totalTerjual; }
    public double getHargaSatuan() { return hargaSatuan; }
    public double getTotalPendapatan() { return totalPendapatan; }
}