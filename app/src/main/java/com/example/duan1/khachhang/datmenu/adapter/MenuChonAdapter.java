package com.example.duan1.khachhang.datmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.Menu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuChonAdapter extends RecyclerView.Adapter<MenuChonAdapter.ViewHolder> {

    List<Menu> menuList;
    Context context;

    public MenuChonAdapter(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_chon,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(menuList.get(position).getHinhAhMon()).into(holder.imgHinhAnhMonChon);
        holder.tvTenMonChon.setText(menuList.get(position).getTenMon());
        holder.tvGiaMonChon.setText(String.valueOf(menuList.get(position).getGiaTien()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhAnhMonChon;
        TextView tvTenMonChon;
        TextView tvGiaMonChon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhMonChon = itemView.findViewById(R.id.imgHinhAnhMonChon);
            tvTenMonChon = itemView.findViewById(R.id.tvTenMonChon);
            tvGiaMonChon = itemView.findViewById(R.id.tvGiaTienChon);
        }
    }
}
