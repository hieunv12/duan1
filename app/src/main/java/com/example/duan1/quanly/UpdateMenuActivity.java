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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
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

public class UpdateMenuActivity extends AppCompatActivity {
    TextInputLayout edtenmon,edgiatien;
    TextView tvmamon;
    ImageView imgquanLyMenu,imgcamera;

    static final int RESULT_LOAD_IMG = 1;
    StorageReference storageRef;

    FirebaseDatabase database;
    Uri imageUri;
    String linkanh;

    String mamon,img,tenmon,giatien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        setTitle("Sửa");
        tvmamon=findViewById(R.id.tvmaMon);
        edtenmon=findViewById(R.id.edtenMon);
        edgiatien=findViewById(R.id.edgiaTien);
        imgquanLyMenu=findViewById(R.id.imgquanLyMenuSua);
        imgcamera=findViewById(R.id.imgcameraSua);
        FirebaseStorage storage = FirebaseStorage.getInstance();


        Intent i=getIntent();
        Bundle b=i.getExtras();
        mamon=b.getString("mamon");
        img=b.getString("img");
        tenmon=b.getString("tenmon");
        giatien=b.getString("giatien");

        tvmamon.setText(mamon);
        edtenmon.getEditText().setText(tenmon);
        edgiatien.getEditText().setText(giatien);

        Log.e("anh", "ảnh:="+img );
        Picasso.get().load(img).into(imgquanLyMenu);

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
                imgquanLyMenu.setImageBitmap(selectedImage);
                Log.e("TAG", "ImG "+selectedImage );
                Log.e("TAG", "ImG== "+ imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(UpdateMenuActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(UpdateMenuActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }




    public void SuaMon(View view) {


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
                    Toast.makeText(UpdateMenuActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "onFailure: " +e.getMessage() );
                }
            });
            imgquanLyMenu.setDrawingCacheEnabled(true);
            imgquanLyMenu.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imgquanLyMenu.getDrawable()).getBitmap();
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


                        String tenmon1=edtenmon.getEditText().getText().toString();
                        String giatien1=edgiatien.getEditText().getText().toString();
                        String key=mamon;
                        DatabaseReference myRef=database.getReference("QuanLyMenu");
                        myRef.child(key).child("anhmon").setValue(linkanh);
                        myRef.child(key).child("tenmon").setValue(tenmon1);
                        myRef.child(key).child("gia").setValue(giatien1);

//                   finish();
                        Toast.makeText(UpdateMenuActivity.this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
        imgquanLyMenu.setDrawingCacheEnabled(true);
        imgquanLyMenu.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgquanLyMenu.getDrawable()).getBitmap();
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

                    String tenmon1=edtenmon.getEditText().getText().toString();
                    String giatien1=edgiatien.getEditText().getText().toString();
                    String key=mamon;
                    DatabaseReference myRef=database.getReference("QuanLyMenu");
                    myRef.child(key).child("anhmon").setValue(linkanh);
                    myRef.child(key).child("tenmon").setValue(tenmon1);
                    myRef.child(key).child("gia").setValue(giatien1);

//                   finish();
                    Toast.makeText(UpdateMenuActivity.this, "Sửa Thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    }
