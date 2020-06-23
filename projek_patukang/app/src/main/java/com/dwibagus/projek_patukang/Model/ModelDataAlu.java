package com.dwibagus.projek_patukang.Model;

public class ModelDataAlu {
    String id_tukang,foto_tukang,nama_tukang,keahlian_tukang,harga_tukang;

    public ModelDataAlu(){}

    public ModelDataAlu(String id_tukang, String foto_tukang, String nama_tukang, String keahlian_tukang, String harga_tukang) {
        this.id_tukang = id_tukang;
        this.foto_tukang = foto_tukang;
        this.nama_tukang = nama_tukang;
        this.keahlian_tukang = keahlian_tukang;
        this.harga_tukang = harga_tukang;
    }

    public String getId_tukang() {
        return id_tukang;
    }
    public void setId_tukang(String id_tukang) {
        this.id_tukang = id_tukang;
    }

    public String getFoto_tukang() {
        return foto_tukang;
    }
    public void setFoto_tukang(String foto_tukang) {
        this.foto_tukang = foto_tukang;
    }

    public String getNama_tukang() {
        return nama_tukang;
    }
    public void setNama_tukang(String nama_tukang) {
        this.nama_tukang = nama_tukang;
    }

    public String getKeahlian_tukang() {
        return keahlian_tukang;
    }
    public void setKeahlian_tukang(String keahlian_tukang) { this.keahlian_tukang = keahlian_tukang;}

    public String getHarga_tukang() {
        return harga_tukang;
    }
    public void setHarga_tukang(String harga_tukang) {
        this.harga_tukang = harga_tukang;
    }
}

