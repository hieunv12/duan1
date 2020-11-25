package com.example.duan1.khachhang.datmenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.khachhang.datmenu.adapter.MenuChonAdapter;
import com.example.duan1.model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class MenuChonFragment extends Fragment {
    RecyclerView rclMenuChon;
    TextView tvTongTienMenu;
    Button btnThanhToan;
    List<Menu> menuList1 = new ArrayList<>();
    StorageReference storageRef;
    FirebaseDatabase database;
    double tongtien;

    public MenuChonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_menu_chon, container, false);

        rclMenuChon =view. findViewById(R.id.rclDSMenuChon);
        tvTongTienMenu = view. findViewById(R.id.tvTongTienMenu);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();

        getData();

        return view;
    }

    private void getData() {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("MenuChon");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Load Data", Toast.LENGTH_SHORT).show();
                menuList1.clear();
                for(DataSnapshot data:snapshot.getChildren() ){

                    Menu menu1=data.getValue(Menu.class);
                    menu1.setMaMon(data.getKey());

                    menuList1.add(menu1);
                    tongtien = tongtien + Double.parseDouble(menu1.getGiaTien());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rclMenuChon.setLayoutManager(linearLayoutManager);

                    MenuChonAdapter menuAdapter = new MenuChonAdapter(menuList1, getContext());
                    rclMenuChon.setAdapter(menuAdapter);

                    tvTongTienMenu.setText(String.valueOf(tongtien));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}