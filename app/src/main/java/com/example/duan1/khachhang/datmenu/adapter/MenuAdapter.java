package com.example.duan1.khachhang.datmenu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.khachhang.datban.ChiTietFragment;
import com.example.duan1.khachhang.datmenu.ChiTietMonFragment;
import com.example.duan1.model.Menu;
import com.example.duan1.model.QuanLyMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    StorageReference storageRef;
    FirebaseDatabase database;
    private List<QuanLyMenu> menuList;
    Context context;
    static int sl =1;

    public MenuAdapter(List<QuanLyMenu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(menuList.get(position).getAnhmon()).into(holder.imgHinhAnhMon);
        holder.tvTenMon.setText(menuList.get(position).getTenmon());
        holder.tvGiaTien.setText(String.valueOf(menuList.get(position).getGia()));
        holder.cvMenu.setTag(position);
        holder.cvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer i = (Integer) view.getTag();

                ChiTietMonFragment chitiet=new ChiTietMonFragment();
                FragmentTransaction transaction =((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                Bundle b = new Bundle();
                b.putString("tenmonchon",menuList.get(i).getTenmon());
                b.putString("giamonchon",menuList.get(i).getGia());
                b.putString("anhmonchon",menuList.get(i).getAnhmon());
                chitiet.setArguments(b);
                transaction.replace(R.id.frameLayout, chitiet);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        holder.btnThem.setTag(position);
        holder.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = (Integer) v.getTag();
//                Intent intent = new Intent(context.getApplicationContext(), MenuChonActivity.class);
//                Bundle b = new Bundle();
//                b.putString("mamonchon",String.valueOf(sl));
//                b.putString("tenmonchon",menuList.get(i).getTenmon());
//                b.putString("giamonchon",menuList.get(i).getGia());
//                b.putString("anhmonchon",menuList.get(i).getAnhmon());
//                sl++;
//                intent.putExtras(b);
//                context.startActivity(intent);
                String mamonchon = String.valueOf(sl);
                String tenmonchon = menuList.get(i).getTenmon();
                String giamonchon = menuList.get(i).getGia();
                String anhmonchon = menuList.get(i).getAnhmon();

                FirebaseStorage storage = FirebaseStorage.getInstance();
                storageRef = storage.getReference();
                database = FirebaseDatabase.getInstance();

                DatabaseReference myRef=database.getReference().child("MenuChon").child(mamonchon);//tạo ra nhánh con và khóa chính
                Menu menu=new Menu(mamonchon,tenmonchon,giamonchon,anhmonchon);
                //add vào quản lí bàn
                //add tất cả giá trị vào firebase
                myRef.setValue(menu).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                sl++;
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhAnhMon;
        TextView tvTenMon;
        TextView tvGiaTien;
        TextView tvSoLong;
        Button btnThem;
        CardView cvMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhMon = itemView.findViewById(R.id.imgHinhAnhMon);
            tvTenMon = itemView.findViewById(R.id.tvTenMon);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
            tvSoLong = itemView.findViewById(R.id.tvSoLuong);
            btnThem = itemView.findViewById(R.id.btnThem);
            cvMenu = itemView.findViewById(R.id.cvMenu);
        }
    }
}
