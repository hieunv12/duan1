package com.example.duan1.khachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.duan1.R;
import com.example.duan1.khachhang.datban.DatBanFragment;
import com.example.duan1.khachhang.datmenu.MenuFragment;
import com.example.duan1.khachhang.taikhoan.TaiKhoanFragment;
import com.example.duan1.khachhang.trangchu.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class KhachHangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        showSelectedFragment(new TrangChuFragment());

        BottomNavigationView navigationView=findViewById(R.id.bottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(naviListenr);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener naviListenr=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch ((item.getItemId())){
                case  R.id.navigation_trangchu:
                    fragment=new TrangChuFragment();
                    break;
                case  R.id.navigation_menu:
                    fragment=new MenuFragment();
                    break;
                case  R.id.navigation_ban:
                    fragment=new DatBanFragment();
                    break;
                case  R.id.navigation_taikhoan:
                    fragment=new TaiKhoanFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
            return true;
        }
    };
    private void showSelectedFragment (Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_danhsach,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}