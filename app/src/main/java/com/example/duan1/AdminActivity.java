package com.example.duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duan1.quanly.QuanLyActivity;
import com.example.duan1.quanly.QuanLyTKActivity;
import com.example.duan1.quanly.nhandon.NhanDonActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void NhanDon(View view) {

        Intent i=new Intent(this, NhanDonActivity.class);
        startActivity(i);
    }

    public void QuanLy(View view) {
        Intent i=new Intent(this, QuanLyActivity.class);
        startActivity(i);
    }

    public void TaiKhoanKhachHang(View view) {
        Intent i=new Intent(this, QuanLyTKActivity.class);
        startActivity(i);
    }

    public void ThongKe(View view) {
        Intent i=new Intent(this, QuanLyActivity.class);
        startActivity(i);
    }
}