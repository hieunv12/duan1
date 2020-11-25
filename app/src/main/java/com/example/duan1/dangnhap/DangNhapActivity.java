package com.example.duan1.dangnhap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.AdminActivity;
import com.example.duan1.R;
import com.example.duan1.khachhang.KhachHangActivity;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.model.TaiKhoanKH;
import com.example.duan1.quanly.DanhSachBanActivity;
import com.example.duan1.quanly.ItemClickSupport;
import com.example.duan1.quanly.UpdateBanActivity;
import com.example.duan1.quanly.adapter.QuanLyBanAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DangNhapActivity extends AppCompatActivity {
TextInputLayout edTenTKDangNhap,edPassDangNhap;
List<TaiKhoanKH> list=new ArrayList<>();
    List<TaiKhoanKH> list1=new ArrayList<>();
//String tentk="";
//String pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        edTenTKDangNhap=findViewById(R.id.edTenTKDangNhap);
        edPassDangNhap=findViewById(R.id.edPassDangNhap);

    }



    public void DangNhap(View view) {
        GetData();
    }

    private  void GetData(){
        String tentk=edTenTKDangNhap.getEditText().getText().toString();
        String pass=edPassDangNhap.getEditText().getText().toString();
        FirebaseDatabase database=FirebaseDatabase.getInstance();

        DatabaseReference mdata=database.getReference("TaiKhoanKH");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(DangNhapActivity.this, "Load Data", Toast.LENGTH_SHORT).show();


                for(DataSnapshot data:snapshot.getChildren() ){

                    TaiKhoanKH taiKhoanKH=data.getValue(TaiKhoanKH.class);
                    taiKhoanKH.setTentk(data.getKey());

                    list.add(taiKhoanKH);


                }


                for (int i = 0; i <list.size() ; i++) {
                    if(tentk.equals(list.get(i).getTentk())&&pass.equals(list.get(i).getPass())){
                        Toast.makeText(DangNhapActivity.this, "Đăng Nhập THành công", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(DangNhapActivity.this, KhachHangActivity.class);
                        startActivity(intent);
                        break;
                    }else {
                        Toast.makeText(DangNhapActivity.this, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhapActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });



        DatabaseReference mdata1=database.getReference("TaiKhoanQL");
        mdata1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(DangNhapActivity.this, "Load Data", Toast.LENGTH_SHORT).show();


                for(DataSnapshot data:snapshot.getChildren() ){

                    TaiKhoanKH taiKhoanKH=data.getValue(TaiKhoanKH.class);
                    taiKhoanKH.setTentk(data.getKey());
                    list1.add(taiKhoanKH);
                }


                for (int i = 0; i <list1.size() ; i++) {
                    if(tentk.equals(list1.get(i).getTentk())&&pass.equals(list1.get(i).getPass())){
                        Toast.makeText(DangNhapActivity.this, "Đăng Nhập THành công", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(DangNhapActivity.this, AdminActivity.class);
                        startActivity(intent);
                        break;
                    }else {
                        Toast.makeText(DangNhapActivity.this, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhapActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void QuenMatKhau(View view) {
        Intent i=new Intent(this,QuenMatKhauActivity.class);
        startActivity(i);
    }

    public void dangkytk(View view) {
        Intent i=new Intent(this,DangKyTKActivity.class);
        startActivity(i);
    }


}