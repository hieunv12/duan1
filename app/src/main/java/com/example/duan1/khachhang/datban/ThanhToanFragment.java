package com.example.duan1.khachhang.datban;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.model.BanDat;

import java.util.ArrayList;


public class ThanhToanFragment extends Fragment {
    TextView tvMa,tvTen,tvSDT,tvNgayThang,tvThoiGian,tvSoLuong,tvThanhTien;
    private ArrayList<BanDat> list = new ArrayList<>();

    public ThanhToanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_thanh_toan, container, false);

        tvMa = view.findViewById(R.id.tvHDMaNguoiDung);
        tvTen =  view.findViewById(R.id.tvHDTenNguoiDung);
        tvSDT =  view.findViewById(R.id.tvHDSDTNguoiDung);
        tvNgayThang =  view.findViewById(R.id.tvHDNgayDat);
        tvThoiGian =  view.findViewById(R.id.tvHDThoiGianDat);
        tvSoLuong =  view.findViewById(R.id.tvHDSoLuongBanDat);
        tvThanhTien =  view.findViewById(R.id.tvHDTienDatBan);
        //tạo 1 bộ nhớ tạm sau khi đăng nhập thành công

        SharedPreferences sharedPreferences =this.getContext().getSharedPreferences("abcde", Context.MODE_PRIVATE);

        String tenTK = sharedPreferences.getString("tenTK","");
        tvMa.setText(tenTK);
        String tenTK2 = sharedPreferences.getString("123","");
        tvMa.setText(tenTK2);
        //lấy xuống ép vào từng textView tuong ứng

        Log.e("TAG", "13151: "+tenTK2 );


        return view;
    }
}