package com.drakkens.gamecenter.Classes.GameCenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class LoginFragment extends Fragment {
    private Database database;

    public LoginFragment(Database database) {
        this.database = database;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_login, container, false);

        TextView username = view.findViewById(R.id.userLoginText);
        TextView password = view.findViewById(R.id.passwordLoginText);

        Button login = view.findViewById(R.id.loginButton);
        login.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (database.getUserPassword(username.getText().toString()).equals(password.getText().toString())) {
                    Toast.makeText(this.getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Main.user = username.getText().toString();

                } else {
                    Toast.makeText(this.getContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();

                }
            }
            return true;
        });

//        Button switchToRegister = view.findViewById(R.id.switchToRegister);
//        switchToRegister.setOnTouchListener((view1, motionEvent) -> {
//            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//
//            }
//
//        });

        return view;
    }
}
