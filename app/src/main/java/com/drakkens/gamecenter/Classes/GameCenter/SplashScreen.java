package com.drakkens.gamecenter.Classes.GameCenter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.drakkens.gamecenter.R;

@RequiresApi(api = Build.VERSION_CODES.S)
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView splash = findViewById(R.id.splash);
        TextView dragons = findViewById(R.id.infinityDragons);
        TextView studios = findViewById(R.id.studios);

        splash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animation));
        dragons.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animation));
        studios.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animation));

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(this, Main.class));
            finish();
        }, 3000);



    }
}
