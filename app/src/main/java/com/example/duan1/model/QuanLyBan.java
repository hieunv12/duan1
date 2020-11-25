package com.example.duan1.model;

public class QuanLyBan {
    String maban,anhban,trangthai,ghichu;

    public QuanLyBan() {
    }

    public QuanLyBan(String maban, String anhban, String trangthai, String ghichu) {
        this.maban = maban;
        this.anhban = anhban;
        this.trangthai = trangthai;
        this.ghichu = ghichu;
    }

    public String getMaban() {
        return maban;
    }

    public void setMaban(String maban) {
        this.maban = maban;
    }

    public String getAnhban() {
        return anhban;
    }

    public void setAnhban(String anhban) {
        this.anhban = anhban;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
