package com.dwibagus.projek_patukang.Model;

public class ModelData {
    String id_sewa,id_tukang,tgl_sewa,status_sewa;

    public ModelData(){}

    public ModelData(String id_sewa,String id_tukang, String tgl_sewa, String status_sewa) {
        this.tgl_sewa = tgl_sewa;
        this.id_sewa = id_sewa;
        this.id_tukang = id_tukang;
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

    public String getId_tukang() {
        return id_tukang;
    }
    public void setId_tukang(String id_tukang) {
        this.id_tukang = id_tukang;
    }

    public String getStatus_sewa() {
        return status_sewa;
    }
    public void setStatus_sewa(String status_sewa) {
        this.status_sewa = status_sewa;
    }
}

