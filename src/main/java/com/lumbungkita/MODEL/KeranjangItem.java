package com.lumbungkita.MODEL;

public class KeranjangItem {
    private int idPanen;
    private String namaPanen;
    private double hargaSatuan;
    private int quantity;
    private double subtotal;

    public KeranjangItem(int idPanen, String namaPanen, double hargaSatuan, int quantity) {
        this.idPanen = idPanen;
        this.namaPanen = namaPanen;
        this.hargaSatuan = hargaSatuan;
        this.quantity = quantity;
        this.subtotal = hargaSatuan * quantity;
    }

    // GETTER
    public int getIdPanen() { return idPanen; }
    public String getNamaPanen() { return namaPanen; }
    public double getHargaSatuan() { return hargaSatuan; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return subtotal; }
}