package com.example.duan1.khachhang.datban;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChiTietFragment extends Fragment {

    ImageView imageView;
    TextView tvmaban,tvtrangthai;
    TextView tvChiTiet;
    private ArrayList<QuanLyBan> list = new ArrayList<>();
    private ArrayAdapter<QuanLyBan> adapter;
    String ghichu;
    String hinhanh;
    public ChiTietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chi_tiet, container, false);
        imageView = view.findViewById(R.id.imgHinhAnhBan);
        tvmaban = view.findViewById(R.id.tvMaBan);
        tvtrangthai = view.findViewById(R.id.tvChiTiet);
        tvChiTiet = view.findViewById(R.id.tvChiTiet);


        Bundle bundle = getArguments();
        String maban = bundle.getString("maban");
        String trangthai = bundle.getString("trangthai");
        String ghichu = bundle.getString("ghichu");
        String hinhanh = bundle.getString("hinhanh");

        tvmaban.setText(maban);
        tvtrangthai.setText(trangthai);
        tvChiTiet.setText(ghichu);
        Picasso.get().load(hinhanh).into(imageView);


        return view;
    }
}