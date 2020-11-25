package com.example.duan1.quanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.model.TaiKhoanKH;
import com.example.duan1.quanly.adapter.QuanLyBanAdapter;
import com.example.duan1.quanly.adapter.QuanLyTKAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyTKActivity extends AppCompatActivity {
    RecyclerView rclQuanLyTK;
    QuanLyTKAdapter adapter;
    private ArrayList<TaiKhoanKH> list;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_t_k);
        setTitle("Danh Tài Khoản");
        rclQuanLyTK=findViewById(R.id.rclQuanLyTK);
        linearLayoutManager = new LinearLayoutManager(QuanLyTKActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclQuanLyTK.getContext(),
                linearLayoutManager.getOrientation());
        rclQuanLyTK.addItemDecoration(dividerItemDecoration);
        list=new ArrayList<>();
        GetData();
    }
    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("TaiKhoanKH");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(QuanLyTKActivity.this, "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();

                for(DataSnapshot data:snapshot.getChildren() ){

                    TaiKhoanKH taiKhoanKH=data.getValue(TaiKhoanKH.class);
                    taiKhoanKH.setTentk(data.getKey());

                    list.add(taiKhoanKH);
                    Log.e("TAG", "onCreate: "+snapshot.toString());
                    Log.e("TAG", "onCreate: "+list);

                    adapter=new QuanLyTKAdapter(QuanLyTKActivity.this,list);
                    rclQuanLyTK.setLayoutManager(linearLayoutManager);
                    rclQuanLyTK.setAdapter(adapter);
                }

                ItemClickSupport.addTo(rclQuanLyTK).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                        Intent intent=new Intent(QuanLyTKActivity.this,ThongTinChiTietTKActivity.class);
                        Bundle b=new Bundle();
                        b.putString("tentk",list.get(i).getTentk());
                        b.putString("name",list.get(i).getName());
                        b.putString("namsinh",list.get(i).getNamsinh());
                        b.putString("sdt",list.get(i).getSdt());
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
//                ItemClickSupport.addTo(rclQuanLyBan).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {
//
//
//                        AlertDialog.Builder adb=new AlertDialog.Builder(QuanLyTKActivity.this);
//                        adb.setTitle("Xóa: ");
//                        adb.setMessage(Html.fromHtml(
//                                "Bạn có muốn xóa Bàn: <font color='#ff4343'>"
//                                        + list.get(i).getMaban() + "</font> khỏi danh sách?"));
//                        adb.setNegativeButton("Cancel", null);
//                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                FirebaseDatabase database=FirebaseDatabase.getInstance();
//                                DatabaseReference mRe=database.getReference("QuanLyBan" +
//                                        "");
//                                mRe.child(list.get(i).getMaban()).removeValue(new DatabaseReference.CompletionListener() {
//                                    @Override
//                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                        Toast.makeText(QuanLyTKActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }});
//                        adb.show();
//
//                        return false;
//                    }
//                });
                Log.e("TAG", "ds: "+list );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuanLyTKActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });


    }
}