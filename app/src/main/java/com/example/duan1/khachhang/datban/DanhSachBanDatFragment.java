package com.example.duan1.khachhang.datban;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duan1.R;
import com.example.duan1.model.BanDat;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DanhSachBanDatFragment extends Fragment {
    Button btnDat;
    RecyclerView rclDSBanDat;
    private ArrayList<BanDat> list= new ArrayList<>();
    BanDatAdapter banDatAdapter;

    public DanhSachBanDatFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_ban_dat, container, false);
        rclDSBanDat = view.findViewById(R.id.rclBanDat);
        btnDat = view.findViewById(R.id.btnDat);



        getData();
        return view;
    }

    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =  database.getReference("BanDat");

        DatabaseReference myRef2 =  database.getReference("QuanLyBan");

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("abcde", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                BanDat banDat = snapshot.getValue(BanDat.class);
//                    Toast.makeText(BanDatActivity.this, banDat.getMaban(), Toast.LENGTH_SHORT).show();//lay dc ma ban

                banDat.setMaban(banDat.getMaban());
                banDat.setHinhanh(banDat.getHinhanh());
                list.add(banDat);


                banDatAdapter = new BanDatAdapter(getContext(), list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                rclDSBanDat.setLayoutManager(linearLayoutManager);
                rclDSBanDat.setAdapter(banDatAdapter);

                myRef2.child(banDat.getMaban()).child("trangthai").setValue("Trống");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        btnDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ThanhToanFragment thanhToanFragment=new ThanhToanFragment();
                FragmentTransaction transaction =getFragmentManager().beginTransaction();
                Bundle c =new Bundle();
                for (int i = 0; i <list.size() ; i++) {
                    Log.e("TAG","121"+list.get(i).getMaban());
                    editor.putString("123",list.get(i).getMaban()+"+");
                    editor.apply();// da demo gui dc thanh cong
                }
                thanhToanFragment.setArguments(c);
                transaction.replace(R.id.frameLayout, thanhToanFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }


}