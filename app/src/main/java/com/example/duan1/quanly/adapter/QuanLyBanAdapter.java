package com.example.duan1.quanly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class QuanLyBanAdapter extends  RecyclerView.Adapter<QuanLyBanAdapter.ViewHolder> {
    List<QuanLyBan> list;
    List<QuanLyBan> listrr=new ArrayList<>();
    public Context context;
    LayoutInflater inflater;



    public QuanLyBanAdapter(Context context, List<QuanLyBan> list) {
        this.list = list;
        this.context = context;
        this.listrr=list;
        inflater= (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        TextView tvmaban,tvtrangthai;
        ImageView imgban;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            tvmaban = itemView.findViewById(R.id.tvmaban);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthai);
            imgban=(ImageView)itemView.findViewById(R.id.imgban);
            //Xử lý khi nút Chi tiết được bấm

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View   view=inflater.inflate(R.layout.item_danh_sach_quan_ly_ban,null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holde, int position) {

        QuanLyBan quanLyBan=list.get(position);
        holde.tvmaban.setText("Bàn:"+"  "+quanLyBan.getMaban());
        holde.tvtrangthai.setText("Trạng Thái:"+"  "+quanLyBan.getTrangthai());
        Picasso.get().load(list.get(position).getAnhban()).into(holde.imgban);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
