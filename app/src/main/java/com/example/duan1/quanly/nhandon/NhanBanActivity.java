package com.example.duan1.quanly.nhandon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.model.QuanLyBan;
import com.example.duan1.quanly.adapter.NhanBanAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NhanBanActivity extends AppCompatActivity {
    RecyclerView rlcNhanBan;
    NhanBanAdapter adapter;
    private ArrayList<QuanLyBan> list;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_ban);
        rlcNhanBan=findViewById(R.id.rlcNhanBan);

        linearLayoutManager = new LinearLayoutManager(NhanBanActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rlcNhanBan.getContext(),
                linearLayoutManager.getOrientation());
        rlcNhanBan.addItemDecoration(dividerItemDecoration);
        list=new ArrayList<>();
        GetData();

    }



    private  void GetData(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mdata=database.getReference("BanDat");
        mdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(NhanBanActivity.this, "Load Data", Toast.LENGTH_SHORT).show();
                list.clear();

                for(DataSnapshot data:snapshot.getChildren() ){

                    QuanLyBan quanLyBan=data.getValue(QuanLyBan.class);
                    quanLyBan.setMaban(data.getKey());

                    list.add(quanLyBan);
                    Log.e("TAG", "onCreate: "+snapshot.toString());
                    Log.e("TAG", "onCreate: "+list);

                    adapter=new NhanBanAdapter(NhanBanActivity.this,list);
                    rlcNhanBan.setLayoutManager(linearLayoutManager);
                    rlcNhanBan.setAdapter(adapter);
                }

//                ItemClickSupport.addTo(rlcNhanBan).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int i, View v) {
//                        Intent intent=new Intent(NhanBanActivity.this, UpdateBanActivity.class);
//                        Bundle b=new Bundle();
//                        b.putString("maban",list.get(i).getMaban());
//                        b.putString("img",list.get(i).getAnhban());
//                        b.putString("trangthai",list.get(i).getTrangthai());
//                        b.putString("ghichu",list.get(i).getGhichu());
//                        intent.putExtras(b);
//                        startActivity(intent);
//                    }
//                });
//                ItemClickSupport.addTo(rclQuanLyBan).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClicked(RecyclerView recyclerView, int i, View v) {
//
//
//                        AlertDialog.Builder adb=new AlertDialog.Builder(DanhSachBanActivity.this);
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
//                                        Toast.makeText(DanhSachBanActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(NhanBanActivity.this, "thất bại", Toast.LENGTH_SHORT).show();

            }
        });


    }
}