package com.dwibagus.projek_patukang.Model;

public class ModelDataTukang {
    String id_sewa,tgl_sewa,nama_sewa,status_sewa;

    public ModelDataTukang(){}

    public ModelDataTukang(String id_sewa, String tgl_sewa, String nama_sewa,String status_sewa) {
        this.tgl_sewa = tgl_sewa;
        this.id_sewa = id_sewa;
        this.nama_sewa = nama_sewa;
        this.status_sewa = status_sewa;
    }

    public String getTgl_sewa() {
        return tgl_sewa;
    }
    public void setTgl_sewa(String tgl_sewa) {
        this.tgl_sewa = tgl_sewa;
    }

    public String getId_sewa() {
        return id_sewa;
    }
    public void setId_sewa(String id_sewa) {
        this.id_sewa = id_sewa;
    }

    public String getNama_sewa() {
        return nama_sewa;
    }
    public void setNama_sewa(String nama_sewa) {
        this.nama_sewa = nama_sewa;
    }

    public String getStatus_sewa() {
        return status_sewa;
    }
    public void setStatus_sewa(String status_sewa) {
        this.status_sewa = status_sewa;
    }
}

