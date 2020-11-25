package com.example.duan1.quanly.nhandon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duan1.R;

public class NhanDonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_don);
    }

    public void NhanBan(View view) {
        Intent i=new Intent(this,NhanBanActivity.class);
        startActivity(i);
    }

    public void NhanMenu(View view) {
        Intent i=new Intent(this,NhanMenuActivity.class);
        startActivity(i);
    }
}