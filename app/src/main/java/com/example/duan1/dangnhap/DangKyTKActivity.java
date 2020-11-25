package com.example.duan1.dangnhap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.TaiKhoanKH;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DangKyTKActivity extends AppCompatActivity {
TextInputLayout edTenTK,edName,edPass,edPassLai,edNamSinh,edSDT,edMaQL;
    StorageReference storageRef;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_t_k);
        setTitle("Đăng Ký");

        edTenTK=findViewById(R.id.edTenTK);
        edPass=findViewById(R.id.edPass);
        edPassLai=findViewById(R.id.edPassLai);
        edName=findViewById(R.id.edName);
        edNamSinh=findViewById(R.id.edNamSinh);
        edSDT=findViewById(R.id.edSDT);
        edMaQL=findViewById(R.id.edMaQL);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();



    }


    public void DangKyTk(View view) {

        String pass=checkPassword();
        String tentk=edTenTK.getEditText().getText().toString();

        String hoten=edName.getEditText().getText().toString();
        String namsinh=edNamSinh.getEditText().getText().toString();
        String sdt=edSDT.getEditText().getText().toString();
        String maql=edMaQL.getEditText().getText().toString();
        String key=tentk;
        if(maql.equals("1234567890")){
            DatabaseReference myRef1=database.getReference().child("TaiKhoanQL");
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(tentk)) {
                        Toast.makeText(DangKyTKActivity.this, "", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference myRef=database.getReference().child("TaiKhoanQL").child(key);
                        TaiKhoanKH taiKhoanKH=new TaiKhoanKH(tentk,pass,hoten,namsinh,sdt);
                        myRef.setValue(taiKhoanKH).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DangKyTKActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DangKyTKActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            DatabaseReference myRef1=database.getReference().child("TaiKhoanKH");
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(tentk)) {

                        Toast.makeText(DangKyTKActivity.this, "", Toast.LENGTH_SHORT).show();
                    } else {

                        DatabaseReference myRef=database.getReference().child("TaiKhoanKH").child(key);
                        TaiKhoanKH taiKhoanKH=new TaiKhoanKH(tentk,pass,hoten,namsinh,sdt);
                        myRef.setValue(taiKhoanKH).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DangKyTKActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DangKyTKActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        Intent i=new Intent(this,DangNhapActivity.class);
        startActivity(i);

    }

    private String checkPassword() {
        edPassLai.setError(null);
        try{
            String pass1=edPass.getEditText().getText().toString();
            String passlai=edPassLai.getEditText().getText().toString();
            if(passlai.length() == 0){
                edPassLai.setError("Vui lòng nhập mật khẩu!");
                return null;
            }

                if(pass1.equals(passlai)){
                    return pass1;
                }else{
                    edPassLai.setError("Mật khẩu bạn nhập không đúng!");
                    return null;
                }

        }catch (Exception e){
            edPassLai.setError("Mật khẩu không hợp lệ!");
            return null;
        }
    }
}