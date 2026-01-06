package com.lumbungkita.MODEL;

public class HasilPanen {
    // enkapsulasi atribut dengan private
    private int idPanen;
    private String namaHasilPanen;
    private String jenisHasilPanen;
    private double hargaJual;
    private int stok;
    private int idPetani; // Foreign Key

    //konstruktor inisialisasi semua atribut untuk mengambil data dari database
    public HasilPanen(int idPanen, String namaHasilPanen, String jenisHasilPanen, double hargaJual, int stok, int idPetani) {
        this.idPanen = idPanen;
        this.namaHasilPanen = namaHasilPanen;
        this.jenisHasilPanen = jenisHasilPanen;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.idPetani = idPetani;
    }

    // konstruktor tanpa idPanen untuk insert data baru
    public HasilPanen(String namaHasilPanen, String jenisHasilPanen, double hargaJual, int stok, int idPetani) {
        this.namaHasilPanen = namaHasilPanen;
        this.jenisHasilPanen = jenisHasilPanen;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.idPetani = idPetani;
    }

    // getter dan setter untuk setiap atribut
    public int getIdPanen() { return idPanen; }
    public void setIdPanen(int idPanen) { this.idPanen = idPanen; }

    public String getNamaHasilPanen() { return namaHasilPanen; }
    public void setNamaHasilPanen(String namaHasilPanen) { this.namaHasilPanen = namaHasilPanen; }

    public String getJenisHasilPanen() { return jenisHasilPanen; }
    public void setJenisHasilPanen(String jenisHasilPanen) { this.jenisHasilPanen = jenisHasilPanen; }

    public double getHargaJual() { return hargaJual; }
    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public int getIdPetani() { return idPetani; }
    public void setIdPetani(int idPetani) { this.idPetani = idPetani; }
    
    // method toString untuk menampilkan nama hasil panen
    @Override
    public String toString() {
        return namaHasilPanen; 
    }
}