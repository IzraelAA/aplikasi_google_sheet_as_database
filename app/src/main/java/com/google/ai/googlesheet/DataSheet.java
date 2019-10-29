package com.google.ai.googlesheet;

public class DataSheet {
    private String Id;
    private String Nama;
private String Umur;
public DataSheet(){

}
    public DataSheet(String id, String nama, String umur) {
        Id = id;
        Nama = nama;
        Umur = umur;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUmur() {
        return Umur;
    }

    public void setUmur(String umur) {
        Umur = umur;
    }
}
