package com.example.duan1.khachhang.datban;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.BanDat;
import com.example.duan1.model.QuanLyBan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder>{
    Context context;
    ArrayList<QuanLyBan> listBan;
    ArrayList<BanDat> listBanDat;
    BanDat banDat;
    LayoutInflater inflater;

    public BanAdapter(Context context, ArrayList<QuanLyBan> listBan) {
        this.context = context;
        this.listBan = listBan;
        inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_d_s_ban, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        QuanLyBan quanLyBan = listBan.get(position);
        Log.e("TAG", "onBindViewHolder: "+listBan );
        holder.tvTenBan.setText(quanLyBan.getMaban());
        holder.tvTrangThai.setText(quanLyBan.getTrangthai());
        Picasso.get().load(listBan.get(position).getAnhban()).into(holder.imgHinhAnBan);

    }


    @Override
    public int getItemCount() {
        return listBan.size(); // trả item tại vị trí postion
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhAnBan;
        TextView tvTenBan, tvTrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view

            tvTenBan = itemView.findViewById(R.id.tvtenBan);
            tvTrangThai = itemView.findViewById(R.id.tvtrangThai);
            imgHinhAnBan = itemView.findViewById(R.id.imghinhAnhBan);
        }
    }

}
