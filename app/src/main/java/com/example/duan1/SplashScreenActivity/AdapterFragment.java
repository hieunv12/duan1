package com.example.duan1.SplashScreenActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterFragment extends FragmentStatePagerAdapter {
    public AdapterFragment(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new OnboardringFragment();
            case 1:
                return new OnboardringFragment1();
            case 2:
                return new OnboardringFragment2();
            default:
                return new OnboardringFragment();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}
