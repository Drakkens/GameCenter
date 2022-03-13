package com.drakkens.gamecenter.Classes.GameCenter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.drakkens.gamecenter.Classes.DatabaseUtils.Database;
import com.drakkens.gamecenter.R;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    public static Database database;
    public static String user = "";

    ViewPager2FragmentAdapter adapter;
    ViewPager2 viewPager2;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        database = new Database(this, "GameCenter");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.game_center_main);

        viewPager2 = this.findViewById(R.id.pager);
        fragmentArrayList.add(new RegisterFragment(database));
        fragmentArrayList.add(new LoginFragment(database));
        fragmentArrayList.add(new SettingsFragment(database));
        fragmentArrayList.add(new GameChooserFragment());
        fragmentArrayList.add(new ScoresFragment(database));

        adapter = new ViewPager2FragmentAdapter(fragmentArrayList, this);
        viewPager2.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }

    }
}
