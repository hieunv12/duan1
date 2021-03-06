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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.model.QuanLyMenu;
import com.example.duan1.quanly.adapter.QuanLyBanAdapter;
import com.example.duan1.quanly.adapter.QuanLyMenuAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachMenuActivity extends AppCompatActivity {
    RecyclerView rclQuanLyMenu;
    QuanLyMenuAdapter adapter;
    private ArrayList<QuanLyMenu> list;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_menu);

        setTitle("Danh Sách Menu");
        rclQuanLyMenu=findViewById(R.id.rclQuanLyMenu);
        linearLayoutManager = new LinearLayoutManager(DanhSachMenuActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclQuanLyMenu.getContext(),linearLayoutManager.getOrientation());
        rclQuanLyMenu.addItemDecoration(dividerItemDecoration);
        list=new ArrayList<>();
        GetData();


    }

    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("QuanLyMenu");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(DanhSachMenuActivity.this, "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();
                for(DataSnapshot data:snapshot.getChildren() ){

                    QuanLyMenu quanLyMenu=data.getValue(QuanLyMenu.class);
                    quanLyMenu.setMamon(data.getKey());

                    list.add(quanLyMenu);
//                    Log.e("TAG", "onCreate: "+snapshot.toString());
//                    Log.e("TAG", "onCreate: "+list);

                    adapter=new QuanLyMenuAdapter(DanhSachMenuActivity.this,list);
                    rclQuanLyMenu.setLayoutManager(linearLayoutManager);
                    rclQuanLyMenu.setAdapter(adapter);
                }

                ItemClickSupport.addTo(rclQuanLyMenu).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                        Intent intent=new Intent(DanhSachMenuActivity.this,UpdateMenuActivity.class);
                        Bundle b=new Bundle();
                        b.putString("mamon",list.get(i).getMamon());
                        b.putString("img",list.get(i).getAnhmon());
                        b.putString("tenmon",list.get(i).getTenmon());
                        b.putString("giatien",list.get(i).getGia());
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                ItemClickSupport.addTo(rclQuanLyMenu).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {


                        AlertDialog.Builder adb=new AlertDialog.Builder(DanhSachMenuActivity.this);
                        adb.setTitle("Xóa: ");
                        adb.setMessage(Html.fromHtml(
                                "Bạn có muốn xóa Bàn: <font color='#ff4343'>"
                                        + list.get(i).getMamon() + "</font> khỏi danh sách?"));
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                DatabaseReference mRe=database.getReference("QuanLyMenu");
                                mRe.child(list.get(i).getMamon()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(DanhSachMenuActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }});
                        adb.show();

                        return false;
                    }
                });
                Log.e("TAG", "ds: "+list );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DanhSachMenuActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ban,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(DanhSachMenuActivity.this, ThemMenuActivity.class);
                startActivity(intent);
                return(true);

        }
        return super.onOptionsItemSelected(item);
    }
}