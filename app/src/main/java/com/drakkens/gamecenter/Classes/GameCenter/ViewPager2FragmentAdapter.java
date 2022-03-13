package com.drakkens.gamecenter.Classes.GameCenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPager2FragmentAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> fragmentArrayList;

    public ViewPager2FragmentAdapter(ArrayList<Fragment> fragmentArrayList, AppCompatActivity activity) {
        super(activity);
        this.fragmentArrayList = fragmentArrayList;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
