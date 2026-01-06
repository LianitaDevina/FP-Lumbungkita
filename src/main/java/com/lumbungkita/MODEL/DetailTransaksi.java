package com.lumbungkita.MODEL;

public class DetailTransaksi {
    // enkapsulasi atribut dengan private
    private int idDetailTransaksi;
    private int jumlahPembelian;
    private double hargaSaatTransaksi;
    private int idTransaksi; // Foreign Key
    private int idPanen;     // Foreign Key

    // konstruktor inisialisasi semua atribut untuk mengambil data dari database
    public DetailTransaksi(int idDetailTransaksi, int jumlahPembelian, double hargaSaatTransaksi, int idTransaksi, int idPanen) {
        this.idDetailTransaksi = idDetailTransaksi;
        this.jumlahPembelian = jumlahPembelian;
        this.hargaSaatTransaksi = hargaSaatTransaksi;
        this.idTransaksi = idTransaksi;
        this.idPanen = idPanen;
    }

    // getter dan setter untuk setiap atribut
    public int getIdDetailTransaksi() { return idDetailTransaksi; }
    public void setIdDetailTransaksi(int idDetailTransaksi) { this.idDetailTransaksi = idDetailTransaksi; }

    public int getJumlahPembelian() { return jumlahPembelian; }
    public void setJumlahPembelian(int jumlahPembelian) { this.jumlahPembelian = jumlahPembelian; }

    public double getHargaSaatTransaksi() { return hargaSaatTransaksi; }
    public void setHargaSaatTransaksi(double hargaSaatTransaksi) { this.hargaSaatTransaksi = hargaSaatTransaksi; }

    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public int getIdPanen() { return idPanen; }
    public void setIdPanen(int idPanen) { this.idPanen = idPanen; }
}