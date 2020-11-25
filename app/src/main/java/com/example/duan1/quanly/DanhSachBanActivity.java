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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.quanly.adapter.QuanLyBanAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachBanActivity extends AppCompatActivity {
    RecyclerView rclQuanLyBan;
    QuanLyBanAdapter adapter;
    private ArrayList<QuanLyBan> list;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_ban);
        setTitle("Danh Sách Bàn");
        rclQuanLyBan=findViewById(R.id.rclQuanLyBan);
         linearLayoutManager = new LinearLayoutManager(DanhSachBanActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rclQuanLyBan.getContext(),
                linearLayoutManager.getOrientation());
        rclQuanLyBan.addItemDecoration(dividerItemDecoration);
        list=new ArrayList<>();
        GetData();
    }


    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("QuanLyBan");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(DanhSachBanActivity.this, "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();

                for(DataSnapshot data:snapshot.getChildren() ){

                    QuanLyBan quanLyBan=data.getValue(QuanLyBan.class);
                    quanLyBan.setMaban(data.getKey());

                    list.add(quanLyBan);
                    Log.e("TAG", "onCreate: "+snapshot.toString());
                    Log.e("TAG", "onCreate: "+list);

                    adapter=new QuanLyBanAdapter(DanhSachBanActivity.this,list);
                    rclQuanLyBan.setLayoutManager(linearLayoutManager);
                    rclQuanLyBan.setAdapter(adapter);
                }

                ItemClickSupport.addTo(rclQuanLyBan).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
                        Intent intent=new Intent(DanhSachBanActivity.this,UpdateBanActivity.class);
                        Bundle b=new Bundle();
                        b.putString("maban",list.get(i).getMaban());
                        b.putString("img",list.get(i).getAnhban());
                        b.putString("trangthai",list.get(i).getTrangthai());
                        b.putString("ghichu",list.get(i).getGhichu());
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });
                ItemClickSupport.addTo(rclQuanLyBan).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {


                        AlertDialog.Builder adb=new AlertDialog.Builder(DanhSachBanActivity.this);
                        adb.setTitle("Xóa: ");
                        adb.setMessage(Html.fromHtml(
                                "Bạn có muốn xóa Bàn: <font color='#ff4343'>"
                                        + list.get(i).getMaban() + "</font> khỏi danh sách?"));
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                DatabaseReference mRe=database.getReference("QuanLyBan" +
                                        "");
                                mRe.child(list.get(i).getMaban()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(DanhSachBanActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DanhSachBanActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

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
             Intent   intent = new Intent(DanhSachBanActivity.this, ThemBanActivity.class);
                startActivity(intent);
                return(true);

        }
        return super.onOptionsItemSelected(item);
    }
}