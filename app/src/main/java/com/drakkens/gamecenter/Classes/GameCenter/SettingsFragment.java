package com.drakkens.gamecenter.Classes.GameCenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class SettingsFragment extends Fragment {
    private Database database;

    public SettingsFragment(Database database) {
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
        View view = inflater.inflate(R.layout.screen_settings, container, false);

        TextView usernameText = view.findViewById(R.id.settingsUserNameText);
        TextView currentPasswordText = view.findViewById(R.id.settingsCurrentPasswordText);
        TextView newPasswordText1 = view.findViewById(R.id.settingsNewPasswordText1);
        TextView newPasswordText2 = view.findViewById(R.id.settingsNewPasswordText2);

        Button changePassword = view.findViewById(R.id.changePasswordButton);

        changePassword.setOnTouchListener((view1, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (currentPasswordText.getText().toString().equals(database.getUserPassword(usernameText.getText().toString()))) {
                    if (newPasswordText1.getText().toString().equals(newPasswordText2.getText().toString())) {
                        database.updatePassword(usernameText.getText().toString(), newPasswordText1.getText().toString());
                        Toast.makeText(this.getContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(this.getContext(), "New Passwords Different!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this.getContext(), "Wrong Password", Toast.LENGTH_SHORT).show();

            }
            return true;
        });

        return view;

    }
}
