package com.example.duan1.model;

public class BanDat {
    private String hinhanh,maban,trangthai;

    public BanDat() {
    }

    public BanDat(String hinhanh, String maban, String trangthai) {
        this.hinhanh = hinhanh;
        this.maban = maban;
        this.trangthai = trangthai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMaban() {
        return maban;
    }

    public void setMaban(String maban) {
        this.maban = maban;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
