package com.lumbungkita.MODEL;

public class Petani {
    // Atribut dibuat 'private' agar tidak bisa diakses langsung
    private int idPetani;
    private String namaPetani;
    private String alamatPetani;
    private String nomorHpPetani;

    // konstruktor inisialisasi semua atribut untuk mengambil data dari database
    public Petani(int idPetani, String namaPetani, String alamatPetani, String nomorHpPetani) {
        this.idPetani = idPetani;
        this.namaPetani = namaPetani;
        this.alamatPetani = alamatPetani;
        this.nomorHpPetani = nomorHpPetani;
    }

    // konstruktor tanpa idPetani (misal untuk insert baru)
    public Petani(String namaPetani, String alamatPetani, String nomorHpPetani) {
        this.namaPetani = namaPetani;
        this.alamatPetani = alamatPetani;
        this.nomorHpPetani = nomorHpPetani;
    }

    // getter dan setter untuk setiap atribut
    public int getIdPetani() {
        return idPetani;
    }
    public void setIdPetani(int idPetani) {
        this.idPetani = idPetani;
    }

    public String getNamaPetani() {
        return namaPetani;
    }
    public void setNamaPetani(String namaPetani) {
        this.namaPetani = namaPetani;
    }

    public String getAlamatPetani() {
        return alamatPetani;
    }
    public void setAlamatPetani(String alamatPetani) {
        this.alamatPetani = alamatPetani;
    }

    public String getNomorHpPetani() {
        return nomorHpPetani;
    }
    public void setNomorHpPetani(String nomorHpPetani) {
        this.nomorHpPetani = nomorHpPetani;
    }
    
    // method toString untuk menampilkan nama petani
    @Override
    public String toString() {
        return namaPetani;
    }
}