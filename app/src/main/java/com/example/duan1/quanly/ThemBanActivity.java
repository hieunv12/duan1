package com.example.duan1.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import gun0912.tedbottompicker.TedBottomPicker;


public class ThemBanActivity extends AppCompatActivity {
    TextInputLayout edMaBan,edChiTiet;
    ImageView imgQuanLyBan,imgCamera;
    RadioButton rabDat,rabTrong,rabDangXuLy;
    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;
    String chon="";
    FirebaseDatabase database;
     Uri imageUri;
    String linkanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban);
        setTitle("Thêm Bàn");
        edMaBan=findViewById(R.id.edMaBan);
        edChiTiet=findViewById(R.id.edChiTiet);
        imgQuanLyBan=findViewById(R.id.imgQuanLyBan);
        imgCamera=findViewById(R.id.imgCamera);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        storageRef = storage.getReference();
         imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });

        rabDat=findViewById(R.id.rabDat);
        rabTrong=findViewById(R.id.rabTrong);
        rabDangXuLy=findViewById(R.id.rabDangXuLy);


        database = FirebaseDatabase.getInstance();
    }



    private void chupanh() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);



        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                 imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                                selectedImage=data.getExtras().get("data");
                imgQuanLyBan.setImageBitmap(selectedImage);
                Log.e("TAG", "ImG "+selectedImage );
                Log.e("TAG", "ImG== "+ imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ThemBanActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ThemBanActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    public void LuuBan(View view) {


        StorageReference mountainsRef=storageRef.child("image/"+ UUID.randomUUID().toString());
        Log.e("TAG", "LuuBan: "+mountainsRef);
        Log.e("TAG", "ImG "+imageUri );
        mountainsRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
              //  Toast.makeText(ThemBanActivity.this, "Thannhcong", Toast.LENGTH_SHORT).show();
                Log.e("TAG1", "onCompl"+task.getResult() );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ThemBanActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                Log.e("TAG1", "onFailure: " +e.getMessage() );
            }
        });
        imgQuanLyBan.setDrawingCacheEnabled(true);
        imgQuanLyBan.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgQuanLyBan.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

//                Toast.makeText(ThemBanActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(ThemBanActivity.this, "Thanh công", Toast.LENGTH_SHORT).show();
                Log.e("TAG12", "imglik="+taskSnapshot.getMetadata() );
            }
        });
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return mountainsRef.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri= task.getResult();
                    linkanh=downloadUri.toString();
                    Log.e("TAG", "onComplete: "+downloadUri);

                    if(rabDat.isChecked()){
                        chon="Đặt";
                    }else  if(rabDangXuLy.isChecked()){
                        chon="Đang sử lý";
                    }else {
                        chon="Trống";
                    }

                    String maban=edMaBan.getEditText().getText().toString();
                    String chitiet=edChiTiet.getEditText().getText().toString();

                    String key=maban;

                    DatabaseReference myRef1=database.getReference().child("QuanLyBan");
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          if (snapshot.hasChild(key)) {
                              Toast.makeText(ThemBanActivity.this, "Bàn đã có", Toast.LENGTH_SHORT).show();
                          } else {
                              DatabaseReference myRef=database.getReference().child("QuanLyBan").child(key);
                              QuanLyBan quanLyBan=new QuanLyBan(maban,linkanh,chon,chitiet);
                              myRef.setValue(quanLyBan).addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      Log.e("TAG", "dulieu== "+ quanLyBan);
                                      Toast.makeText(ThemBanActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(ThemBanActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }
                      }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                         });


                } else {
                    // Handle failures
                    // ...
                }
            }
        });




    }
}