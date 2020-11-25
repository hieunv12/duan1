package com.example.duan1.quanly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.model.QuanLyMenu;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ThemMenuActivity extends AppCompatActivity {
    TextInputLayout edMaMon,edTenMon,edGiaTien;
    ImageView imgQuanLyMenu,imgCamera;
    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;
    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_menu);
        setTitle("Thêm Menu");
        edMaMon=findViewById(R.id.edMaMon);
        edTenMon=findViewById(R.id.edTenMon);
        edGiaTien=findViewById(R.id.edGiaTien);
        imgQuanLyMenu=findViewById(R.id.imgQuanLyMenu);
        imgCamera=findViewById(R.id.imgCameraMenu);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chupanh();
            }
        });
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
                imgQuanLyMenu.setImageBitmap(selectedImage);
                Log.e("TAG", "ImG "+selectedImage );
                Log.e("TAG", "ImG== "+ imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ThemMenuActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ThemMenuActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }


    private void chupanh() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    public void LuuMenu(View view) {

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
                Toast.makeText(ThemMenuActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                Log.e("TAG1", "onFailure: " +e.getMessage() );
            }
        });
        imgQuanLyMenu.setDrawingCacheEnabled(true);
        imgQuanLyMenu.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgQuanLyMenu.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

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

                    String mamon=edMaMon.getEditText().getText().toString();
                    String tenmon=edTenMon.getEditText().getText().toString();
                    String giatien=edGiaTien.getEditText().getText().toString();

                    String key=mamon;
                    DatabaseReference myRef1=database.getReference().child("QuanLyMenu");
                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(key)) {
                                Toast.makeText(ThemMenuActivity.this, "Món ăn đã có", Toast.LENGTH_SHORT).show();
                            } else {

                                DatabaseReference myRef=database.getReference().child("QuanLyMenu").child(key);
                                QuanLyMenu quanLyMenu=new QuanLyMenu(mamon,tenmon,giatien,linkanh);
                                myRef.setValue(quanLyMenu).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(ThemMenuActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ThemMenuActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
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