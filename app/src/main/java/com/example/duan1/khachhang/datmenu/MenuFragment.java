package com.example.duan1.khachhang.datmenu;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.khachhang.datban.DanhSachBanDatFragment;
import com.example.duan1.khachhang.datmenu.adapter.MenuAdapter;
import com.example.duan1.model.QuanLyMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    EditText edTimKiemMenu;
    RecyclerView rclDSMenu;
    StorageReference storageReference;
    List<QuanLyMenu> menuList = new ArrayList<>();

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_menu, container, false);

        edTimKiemMenu = view.findViewById(R.id.edTimKiemMenu);
        rclDSMenu =view. findViewById(R.id.rclDSMenu);

        firebaseDatabase = FirebaseDatabase.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();
        getData();
        return view;

    }

    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mdata = database.getReference("QuanLyMenu");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), "Load Data", Toast.LENGTH_SHORT).show();
                menuList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {

                    QuanLyMenu quanLyMenu = data.getValue(QuanLyMenu.class);
                    quanLyMenu.setMamon(data.getKey());
                    menuList.add(quanLyMenu);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rclDSMenu.setLayoutManager(linearLayoutManager);
                    MenuAdapter menuAdapter = new MenuAdapter(menuList,getContext());
                    rclDSMenu.setAdapter(menuAdapter);
                }

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
            MenuChonFragment fragmentOne = new MenuChonFragment();
            FragmentTransaction transaction =this.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragmentOne);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}