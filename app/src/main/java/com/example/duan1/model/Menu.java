package com.example.duan1.model;

public class Menu {
    private String maMon;
    private String tenMon;
    private String giaTien;
    private String hinhAhMon;

    public Menu() {
    }

    public Menu(String maMon, String tenMon, String giaTien, String hinhAhMon) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.giaTien = giaTien;
        this.hinhAhMon = hinhAhMon;
    }

    public String getMaMon() {
        return maMon;
    }

    public void setMaMon(String maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getHinhAhMon() {
        return hinhAhMon;
    }

    public void setHinhAhMon(String hinhAhMon) {
        this.hinhAhMon = hinhAhMon;
    }

}
