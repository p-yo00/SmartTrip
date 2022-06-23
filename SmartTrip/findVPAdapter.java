package com.example.projectui_yeon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class findVPAdapter extends FragmentStateAdapter {

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position%2==0) return new FindIDFragment();
        else return new FindPWFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public findVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
}
