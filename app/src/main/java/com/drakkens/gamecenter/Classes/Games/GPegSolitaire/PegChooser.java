package com.drakkens.gamecenter.Classes.Games.GPegSolitaire;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.drakkens.gamecenter.R;


public class PegChooser extends AppCompatActivity {
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.peg_chooser);

        viewPager2 = this.findViewById(R.id.pegChooserViewPager);
        ChooserAdapter adapter = new ChooserAdapter(this, this);
        viewPager2.setAdapter(adapter);




    }


    public void startPeg(int position) {
        Intent intent = new Intent(this, PegMain.class);
        intent.putExtra("Layout", position);
        this.startActivity(intent);
    }
}
