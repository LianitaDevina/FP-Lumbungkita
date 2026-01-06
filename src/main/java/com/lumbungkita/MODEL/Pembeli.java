package com.lumbungkita.MODEL;

public class Pembeli {
    // enkapsulasi atribut dengan private
    private int idPembeli;
    private String namaPembeli;
    private String tipePembeli;
    private String nomorHpPembeli;

    // kontruktor inisialisasi semua atribut utuk mengambil data dari database
    public Pembeli(int idPembeli, String namaPembeli, String tipePembeli, String nomorHpPembeli) {
        this.idPembeli = idPembeli;
        this.namaPembeli = namaPembeli;
        this.tipePembeli = tipePembeli;
        this.nomorHpPembeli = nomorHpPembeli;
    }

    // kontruktor tanpa idPembeli untuk insert data baru
    public Pembeli(String namaPembeli, String tipePembeli, String nomorHpPembeli) {
        this.namaPembeli = namaPembeli;
        this.tipePembeli = tipePembeli;
        this.nomorHpPembeli = nomorHpPembeli;
    }

    // getter dan setter untuk setiap atribut
    public int getIdPembeli() { return idPembeli; }
    public void setIdPembeli(int idPembeli) { this.idPembeli = idPembeli; }

    public String getNamaPembeli() { return namaPembeli; }
    public void setNamaPembeli(String namaPembeli) { this.namaPembeli = namaPembeli; }

    public String getTipePembeli() { return tipePembeli; }
    public void setTipePembeli(String tipePembeli) { this.tipePembeli = tipePembeli; }

    public String getNomorHpPembeli() { return nomorHpPembeli; }
    public void setNomorHpPembeli(String nomorHpPembeli) { this.nomorHpPembeli = nomorHpPembeli; }

    // method toString untuk menampilkan nama pembeli
    @Override
    public String toString() {
        return namaPembeli;
    }
}