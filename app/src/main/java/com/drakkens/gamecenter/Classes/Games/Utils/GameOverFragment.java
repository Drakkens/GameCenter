package com.drakkens.gamecenter.Classes.Games.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.drakkens.gamecenter.Classes.DatabaseUtils.Database;

public class GameOverFragment extends DialogFragment {
    private final Database database;
    private final String user;
    private final int score;
    private final int time;

    public GameOverFragment(Database database, String user, int score, int time) {
        this.database = database;
        this.user = user;
        this.score = score;
        this.time = time;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(this.getContext())
                .setMessage("Game Over!")
                .setPositiveButton("Save Score", (dialogInterface, i) -> {
                    database.insertScore(user, score, time);
                }).create();
    }
}
