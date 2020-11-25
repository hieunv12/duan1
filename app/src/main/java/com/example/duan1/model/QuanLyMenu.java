package com.example.duan1.model;

public class QuanLyMenu {
    String mamon,tenmon,gia,anhmon;

    public QuanLyMenu() {
    }

    public QuanLyMenu(String mamon, String tenmon, String gia, String anhmon) {
        this.mamon = mamon;
        this.tenmon = tenmon;
        this.gia = gia;
        this.anhmon = anhmon;
    }

    public String getMamon() {
        return mamon;
    }

    public void setMamon(String mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getAnhmon() {
        return anhmon;
    }

    public void setAnhmon(String anhmon) {
        this.anhmon = anhmon;
    }
}
