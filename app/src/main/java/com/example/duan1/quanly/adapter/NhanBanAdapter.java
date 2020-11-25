package com.example.duan1.quanly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;

import java.util.List;

public class NhanBanAdapter extends  RecyclerView.Adapter<NhanBanAdapter.ViewHolder> {

    List<QuanLyBan> list;

    public Context context;
    LayoutInflater inflater;



    public NhanBanAdapter(Context context, List<QuanLyBan> list) {
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
            tvtentk_ban = itemView.findViewById(R.id.tvtentk_ban);
            tvtenban_ban = itemView.findViewById(R.id.tvtenban_ban);
            //Xử lý khi nút Chi tiết được bấm

        }
    }
    @NonNull
    @Override
    public NhanBanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View   view=inflater.inflate(R.layout.item_nhan_ban,null);

        NhanBanAdapter.ViewHolder viewHolder = new NhanBanAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NhanBanAdapter.ViewHolder holde, int position) {

        QuanLyBan quanLyBan=list.get(position);
        holde.tvtentk_ban.setText("Tài Khoản:"+"  "+quanLyBan.getTrangthai());
        holde.tvtenban_ban.setText("Tên Bàn :"+"  "+quanLyBan.getMaban());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
