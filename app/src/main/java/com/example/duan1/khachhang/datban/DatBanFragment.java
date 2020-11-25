package com.example.duan1.khachhang.datban;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.duan1.R;
import com.example.duan1.model.BanDat;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.quanly.ItemClickSupport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


public class DatBanFragment extends Fragment {
    int keyHDTC = 0;

    RecyclerView rclDSBan;

    private ArrayList<QuanLyBan> list= new ArrayList<>();
    private ArrayList<BanDat> listDat= new ArrayList<>();

    BanAdapter banAdapter;


    public DatBanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dat_ban, container, false);
        rclDSBan =view.findViewById(R.id.rclDSBan);

        getData();
        return view;
    }

    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();// ve nha tim dieu kien de in len vs trang thai trong
        DatabaseReference myRef =  database.getReference("QuanLyBan");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    QuanLyBan quanLyBan = data.getValue(QuanLyBan.class);
                    quanLyBan.setMaban(data.getKey());
                    if (data.child("trangthai").getValue().toString().equals("Trống")==true){

                    }list.add(quanLyBan);
                    Log.e("TAG", "onCreateeeee: "+data.child("trangthai").getValue().toString().equals("Trống"));
                    Log.e("TAG", "ds: "+list );
                    banAdapter = new BanAdapter(getContext(), list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rclDSBan.setLayoutManager(linearLayoutManager);
                    rclDSBan.setAdapter(banAdapter);
                }

                ItemClickSupport.addTo(rclDSBan).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        DatabaseReference dataDatBan = database.getReference("BanDat");
                        Log.e("TAG", "onItemClicked: "+list.get(position).getMaban() );
                        dataDatBan.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(list.get(position).getMaban())){
                                    Toast.makeText(getContext(), "ban da co", LENGTH_SHORT).show();
                                }else {
                                    Map<String,Object> newBanDat = new HashMap<>();
                                    newBanDat.put("maban",list.get(position).getMaban());
                                    newBanDat.put("hinhanh",list.get(position).getAnhban());
                                    newBanDat.put("trangthai",list.get(position).getTrangthai());
                                    dataDatBan.child(list.get(position).getMaban()).updateChildren(newBanDat);
                                    myRef.child(list.get(position).getMaban()).child("trangthai").setValue("Đặt");
                                    Toast.makeText(getContext(), "đã thêm vào bàn đặt", LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
                ItemClickSupport.addTo(rclDSBan).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                        ChiTietFragment chiTietFragment=new ChiTietFragment();
                        FragmentTransaction transaction =getFragmentManager().beginTransaction();

                        Bundle c =new Bundle();
                        c.putString("maban",list.get(position).getMaban());
                        c.putString("trangthai",list.get(position).getTrangthai());
                        c.putString("hinhanh",list.get(position).getAnhban());
                        c.putString("ghichu",list.get(position).getGhichu());
//                      Log.e("TAG", "onCreateeeee: "+list.get(position).getMaban()); lấy được mã bàn
                        chiTietFragment.setArguments(c);
                        transaction.replace(R.id.frameLayout, chiTietFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        return false;
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.danhsach){
            DanhSachBanDatFragment fragmentOne = new DanhSachBanDatFragment();
            FragmentTransaction transaction =this.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentOne);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}