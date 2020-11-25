package com.example.duan1.SplashScreenActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duan1.AdminActivity;
import com.example.duan1.R;
import com.example.duan1.dangnhap.DangKyTKActivity;
import com.example.duan1.dangnhap.DangNhapActivity;


public class OnboardringFragment2 extends Fragment {

Button btn_skip;

    public OnboardringFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_onboardring2, container, false);
        btn_skip=view.findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(container.getContext(), DangNhapActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}