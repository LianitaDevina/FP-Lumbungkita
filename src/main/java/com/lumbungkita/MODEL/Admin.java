package com.lumbungkita.MODEL;

public class Admin {
    // enkapsulasi atribut dengan private
    private int idAdmin;
    private String username;
    private String password;

    // konstruktor inisialisasi semua atribut
    public Admin(int idAdmin, String username, String password) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.password = password;
    }

    // method getter dan setter untuk setiap atribut
    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}