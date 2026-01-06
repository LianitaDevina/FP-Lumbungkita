package com.lumbungkita.MODEL;

import java.sql.Timestamp;

public class Transaksi {
    // enkapsulasi atribut dengan private
    private int idTransaksi;
    private Timestamp tanggalWaktu;
    private double totalHarga;
    private int idPembeli; // Foreign Key

    // konstruktor inisialisasi semua atribut untuk mengambil data dari database
    public Transaksi(int idTransaksi, Timestamp tanggalWaktu, double totalHarga, int idPembeli) {
        this.idTransaksi = idTransaksi;
        this.tanggalWaktu = tanggalWaktu;
        this.totalHarga = totalHarga;
        this.idPembeli = idPembeli;
    }

    // getter dan setter untuk setiap atribut
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }

    public Timestamp getTanggalWaktu() { return tanggalWaktu; }
    public void setTanggalWaktu(Timestamp tanggalWaktu) { this.tanggalWaktu = tanggalWaktu; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public int getIdPembeli() { return idPembeli; }
    public void setIdPembeli(int idPembeli) { this.idPembeli = idPembeli; }
}