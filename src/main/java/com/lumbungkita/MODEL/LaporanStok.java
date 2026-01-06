package com.lumbungkita.MODEL;

public class LaporanStok {
    //  atribut laporan stok
    private String namaBarang;
    private String jenisBarang;
    private double harga;
    private int stok;
    private String namaPetani;

    // konstruktor LaporanStok
    public LaporanStok(String namaBarang, String jenisBarang, double harga, int stok, String namaPetani) {
        this.namaBarang = namaBarang;
        this.jenisBarang = jenisBarang;
        this.harga = harga;
        this.stok = stok;
        this.namaPetani = namaPetani;
    }

    // getter untuk atribut
    public String getNamaBarang() { return namaBarang; }
    public String getJenisBarang() { return jenisBarang; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
    public String getNamaPetani() { return namaPetani; }
}