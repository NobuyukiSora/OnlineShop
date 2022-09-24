package com.example.projekakhirmcs;

public class Barang {
    private String namabarang;
    private String ratingbarang;
    private String hargabarang;

    public Barang(String namabarang, String ratingbarang, String hargabarang) {
        this.namabarang = namabarang;
        this.ratingbarang = ratingbarang;
        this.hargabarang = hargabarang;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getRatingbarang() {
        return ratingbarang;
    }

    public void setIdbarang(String ratingbarang) {
        this.ratingbarang = ratingbarang;
    }

    public String getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(String hargabarang) {
        this.hargabarang = hargabarang;
    }
}
