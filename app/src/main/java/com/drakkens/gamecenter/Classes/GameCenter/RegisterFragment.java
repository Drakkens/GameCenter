package com.drakkens.gamecenter.Classes.GameCenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drakkens.gamecenter.Classes.DatabaseUtils.Database;
import com.drakkens.gamecenter.R;

public class RegisterFragment extends Fragment {
    private Database database;

    public RegisterFragment(Database database) {
        this.database = database;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_register, container, false);

        TextView username = view.findViewById(R.id.userRegisterText);
        TextView password1 = view.findViewById(R.id.passwordRegisterText);
        TextView password2 = view.findViewById(R.id.passwordRegisterText2);

        Button registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Log.d("A", "A");
                if (password1.getText().toString().equals(password2.getText().toString())) {
                    if (database.insertUser(username.getText().toString(), password1.getText().toString()) != -1)
                        Toast.makeText(this.getContext(), "Registered!", Toast.LENGTH_SHORT).show();

                    else Toast.makeText(this.getContext(), "User already registered", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this.getContext(), "Different Passwords!", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        return view;
    }
}
