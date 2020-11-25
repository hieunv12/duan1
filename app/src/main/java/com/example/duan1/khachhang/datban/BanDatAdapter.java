package com.example.duan1.khachhang.datban;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.BanDat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BanDatAdapter extends RecyclerView.Adapter<BanDatAdapter.ViewHolder>{
    Context context;
    ArrayList<BanDat> listBanDat;
    LayoutInflater inflater;

    public BanDatAdapter(Context context, ArrayList<BanDat> listBanDat) {
        this.context = context;
        this.listBanDat=listBanDat;
        inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_d_s_ban_dat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        BanDat banDat = listBanDat.get(position);
        holder.txtTenSanPham.setText(banDat.getMaban());
        Picasso.get().load(listBanDat.get(position).getHinhanh()).into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return listBanDat.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtTenSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            imgAvatar = itemView.findViewById(R.id.imghinhAnhBan);
            txtTenSanPham = itemView.findViewById(R.id.tvmaBan);

        }
    }
}
