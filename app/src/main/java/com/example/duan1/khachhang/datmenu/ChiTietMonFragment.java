package com.example.duan1.khachhang.datmenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.khachhang.datban.ChiTietFragment;
import com.squareup.picasso.Picasso;


public class ChiTietMonFragment extends Fragment {

    ImageView imgChiTietAnhMon;
    TextView tvChiTietTenMon,tvChiTietGiaMon;
    String anhmonchon,tenmonchon,giamonchon;
    public ChiTietMonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chi_tiet_mon, container, false);


        imgChiTietAnhMon =view. findViewById(R.id.imgChiTietAnhMon);
        tvChiTietTenMon = view.findViewById(R.id.tvChiTietTenMon);
        tvChiTietGiaMon = view.findViewById(R.id.tvChiTietGiaMon);


        Bundle b = getArguments();
        anhmonchon =b.getString("anhmonchon");
        tenmonchon =b.getString("tenmonchon");
        giamonchon =b.getString("giamonchon");

        tvChiTietGiaMon.setText(giamonchon);
        tvChiTietTenMon.setText(tenmonchon);
        Picasso.get().load(anhmonchon).into(imgChiTietAnhMon);



        return view;
    }
}