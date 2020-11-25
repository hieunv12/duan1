package com.example.duan1.quanly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.duan1.R;

public class ThongTinChiTietTKActivity extends AppCompatActivity {
TextView tvtentkct,tvnamect,tvnamsinhct,tvsdtchitiet;
String tentk,name,namsinh,sdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet_t_k);
        setTitle("Thông tin chi tiết");
        tvtentkct=findViewById(R.id.tvtentk_ct);
        tvnamect=findViewById(R.id.tvname_ct);
        tvnamsinhct=findViewById(R.id.tvnamsinh_ct);
        tvsdtchitiet=findViewById(R.id.tvsdt_ct);


        Intent i=getIntent();
        Bundle b=i.getExtras();
        tentk=b.getString("tentk");
        name=b.getString("name");
        namsinh=b.getString("namsinh");
        sdt=b.getString("sdt");

        tvtentkct.setText("Tên Tài Khoản :"+tentk);
        tvnamect.setText("Họ Tên :"+name);
        tvnamsinhct.setText("Năm Sinh"+namsinh);
        tvsdtchitiet.setText("Số Điện Thoại"+sdt);

    }
}