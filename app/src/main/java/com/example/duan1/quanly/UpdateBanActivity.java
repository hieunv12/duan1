package com.example.duan1.quanly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class UpdateBanActivity extends AppCompatActivity {
    TextInputLayout edchiTiet;
    TextView tvmaBan;
    ImageView imgquanLyBan,imgcamera;
    RadioButton rabdat,rabtrong,rabdangXuLy;
    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;
    String chon="";
    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;

    String maban,img,chitiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ban);
        setTitle("Sửa");
        tvmaBan=findViewById(R.id.tvmaBan);
        edchiTiet=findViewById(R.id.edchiTiet);
        imgquanLyBan=findViewById(R.id.imgquanLyBan);
        imgcamera=findViewById(R.id.imgcamera);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        rabdat=findViewById(R.id.rabdat);
        rabtrong=findViewById(R.id.rabtrong);
        rabdangXuLy=findViewById(R.id.rabdangXuLy);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        maban=b.getString("maban");
        img=b.getString("img");
        chon=b.getString("trangthai");
        chitiet=b.getString("ghichu");

        tvmaBan.setText(maban);
        edchiTiet.getEditText().setText(chitiet);
        Log.e("anh", "ảnh:="+img );
        Picasso.get().load(img).into(imgquanLyBan);

        storageRef = storage.getReference();
        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });





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
                imgquanLyBan.setImageBitmap(selectedImage);
                Log.e("TAG", "ImG "+selectedImage );
                Log.e("TAG", "ImG== "+ imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(UpdateBanActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(UpdateBanActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    public void LuuBan(View view) {


        if(rabdat.isChecked()){
            chon="Đặt";
        }else  if(rabdangXuLy.isChecked()){
            chon="Đang sử lý";
        }else {
            chon="Trống";
        }




        StorageReference mountainsRef=storageRef.child("image/"+ UUID.randomUUID().toString());

        if(imageUri!=null){

            Log.e("TAG", "LuuBan: "+mountainsRef);
            mountainsRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    //  Toast.makeText(ThemBanActivity.this, "Thannhcong", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "onCompl"+task.getResult() );
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateBanActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "onFailure: " +e.getMessage() );
                }
            });
            imgquanLyBan.setDrawingCacheEnabled(true);
            imgquanLyBan.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imgquanLyBan.getDrawable()).getBitmap();
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

                        String chitiet1=edchiTiet.getEditText().getText().toString();

                        String key=maban;
                        DatabaseReference myRef=database.getReference("QuanLyBan");
                        myRef.child(key).child("anhban").setValue(linkanh);
                        myRef.child(key).child("ghichu").setValue(chitiet1);
                        myRef.child(key).child("trangthai").setValue(chon);

//                   finish();
                        Toast.makeText(UpdateBanActivity.this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
            imgquanLyBan.setDrawingCacheEnabled(true);
            imgquanLyBan.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imgquanLyBan.getDrawable()).getBitmap();
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

                        String chitiet1=edchiTiet.getEditText().getText().toString();

                        String key=maban;
                        DatabaseReference myRef=database.getReference("QuanLyBan");
                        myRef.child(key).child("anhban").setValue(linkanh);
                        myRef.child(key).child("ghichu").setValue(chitiet1);
                        myRef.child(key).child("trangthai").setValue(chon);

//                   finish();
                        Toast.makeText(UpdateBanActivity.this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }




}