package com.example.duan1.quanly.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.Menu;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.model.QuanLyMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NhanMenuAdapter extends RecyclerView.Adapter<NhanMenuAdapter.ViewHolder> {

        ArrayList<Menu> list;

public Context context;
        LayoutInflater inflater;



public NhanMenuAdapter(Context context, ArrayList<Menu> list) {
        this.list = list;
        this.context = context;

        inflater= (LayoutInflater) context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
        }


public class ViewHolder extends RecyclerView.ViewHolder {
    private View itemview;
    TextView tvtentk_ban,tvtenban_ban;


    public ViewHolder(View itemView) {
        super(itemView);
        itemview = itemView;
        tvtentk_ban = itemView.findViewById(R.id.tvtentk_menu);
        tvtenban_ban = itemView.findViewById(R.id.tvtenban_menu);
        //Xử lý khi nút Chi tiết được bấm

    }
}
    @NonNull
    @Override
    public NhanMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View   view=inflater.inflate(R.layout.item_nhan_menu,null);

        NhanMenuAdapter.ViewHolder viewHolder = new NhanMenuAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NhanMenuAdapter.ViewHolder holde, int position) {

        Menu quanLyMenu=list.get(position);
        holde.tvtentk_ban.setText("Tài Khoản:"+"  "+quanLyMenu.getMaMon());
        holde.tvtenban_ban.setText("Tên Bàn :"+"  "+quanLyMenu.getTenMon());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
