package com.example.duan1.quanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duan1.R;

public class QuanLyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        setTitle("Quản Lý Thay Đổi");
    }

    public void QuanlyBan(View view) {
        Intent i=new Intent(this,DanhSachBanActivity.class);
        startActivity(i);
    }

    public void QuanLyMenu(View view) {
        Intent i=new Intent(this,DanhSachMenuActivity.class);
        startActivity(i);
    }
}