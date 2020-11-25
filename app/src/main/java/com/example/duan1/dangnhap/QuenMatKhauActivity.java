package com.example.duan1.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duan1.R;

public class QuenMatKhauActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
    }

    public void LayLaiMK(View view) {
        Intent i=new Intent(this,DoiMatKhauActivity.class);
        startActivity(i);
    }
}