package com.drakkens.gamecenter.Classes.GameCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.drakkens.gamecenter.Classes.DatabaseUtils.Database;
import com.drakkens.gamecenter.Classes.Games.G2048.Main;
import com.drakkens.gamecenter.Classes.Games.GPegSolitaire.PegChooser;
import com.drakkens.gamecenter.Classes.Games.GPegSolitaire.PegMain;
import com.drakkens.gamecenter.R;
//Holds PageViewer2 For Fragments
public class GameChooserFragment extends Fragment {
    public GameChooserFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_chooser, container, false);
        view.findViewById(R.id.img).setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                startActivity(new Intent(this.getContext(), Main.class));

            }
            return true;
        });

        view.findViewById(R.id.img2).setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                startActivity(new Intent(this.getContext(), PegChooser.class));

            }
            return true;
        });

        return view;
    }
}