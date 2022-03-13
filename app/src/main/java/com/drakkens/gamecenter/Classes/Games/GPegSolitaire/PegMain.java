package com.drakkens.gamecenter.Classes.Games.GPegSolitaire;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.drakkens.gamecenter.Classes.Games.Utils.EnhancedChronometer;
import com.drakkens.gamecenter.Classes.Games.Utils.GameOverFragment;
import com.drakkens.gamecenter.R;

import java.util.ArrayList;

public class PegMain extends AppCompatActivity {
    private PegButton[][] pegList = new PegButton[9][9];
    private PegButton currentPeg;
    private PegButton previousPeg = null;
    private EnhancedChronometer chronometer;
    private TextView remainingPegsText;

    private PegButton oldCurrentPeg, oldPreviousPeg, oldMiddlePeg;
    static public ArrayList<PegButton> existingPegs = new ArrayList<>();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        existingPegs = new ArrayList<>();

        int layout = getIntent().getIntExtra("Layout", 0);

        if (layout == 0) {
            this.setContentView(R.layout.german_peg);

        }
        if (layout == 1) {
            this.setContentView(R.layout.diamond_peg);

        }
        if (layout == 2) {
            this.setContentView(R.layout.french_peg);

        }
        if (layout == 3) {
            this.setContentView(R.layout.english_peg);

        }


//        switch (layout) {
//            case 0:
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//            case 3:
//        }


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chronometer = findViewById(R.id.timer3);
        chronometer.start();

        remainingPegsText = findViewById(R.id.remainingPegs3);

        Button undoButton = findViewById(R.id.undoButton3);
        undoButton.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (oldMiddlePeg != null) undoMovement();
            }
            return true;
        });

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        int row = -1;
        int column = 0;
        for (int position = 0; position < gridLayout.getChildCount(); position++) {
            column = position % 9;
            if (column == 0) row++;

            pegList[row][column] = (PegButton) gridLayout.getChildAt(position);
            pegList[row][column].setPosX(row);
            pegList[row][column].setPosY(column);

            pegList[row][column].setOnTouchListener((view, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    selectPeg(view);

                }
                return true;
            });
        }
        remainingPegsText.setText(String.valueOf(existingPegs.size()));

    }

    private void movePeg() {
        if ((currentPeg.getPosY() == previousPeg.getPosY()) && (Math.abs(currentPeg.getPosX() - previousPeg.getPosX()) == 2) || (currentPeg.getPosX() == previousPeg.getPosX()) && (Math.abs(currentPeg.getPosY() - previousPeg.getPosY()) == 2)) {
            PegButton middlePeg = pegList[previousPeg.getPosX() + (currentPeg.getPosX() - previousPeg.getPosX()) / 2][previousPeg.getPosY() + (currentPeg.getPosY() - previousPeg.getPosY()) / 2];

            if (!middlePeg.isEmpty()) {

                oldCurrentPeg = currentPeg;
                oldMiddlePeg = middlePeg;
                oldPreviousPeg = previousPeg;

                currentPeg.setEmpty(false);
                currentPeg = null;
                middlePeg.setEmpty(true);
                previousPeg.setEmpty(true);


                remainingPegsText.setText(String.valueOf(existingPegs.size()));
                if (checkWin() || checkLose()) {
                    chronometer.stop();
                    GameOverFragment fragment = new GameOverFragment(com.drakkens.gamecenter.Classes.GameCenter.Main.database, com.drakkens.gamecenter.Classes.GameCenter.Main.user, existingPegs.size(), chronometer.getTotalSeconds());
                    fragment.show(getSupportFragmentManager(), null);
                }

            }
        }
    }

    private void selectPeg(View view) {
        if (currentPeg != null && !currentPeg.isEmpty) {
            previousPeg = currentPeg;
            if (previousPeg.getClicked() && currentPeg != view) previousPeg.setClicked(false);

        }

        currentPeg = (PegButton) view;

        if (!currentPeg.isEmpty()) {
            currentPeg.setClicked(!currentPeg.getClicked());

        } else {
            if (previousPeg != null && !previousPeg.isEmpty) {
                movePeg();

            }

        }
    }

    private boolean checkWin() {
        return existingPegs.size() == 1;

    }

    private boolean checkLose() {
        boolean validMovement = false;
        PegButton[] pegColumn = new PegButton[pegList.length];
        for (int i = 0; i < pegList.length; i++) {

            for (int j = 0; j < pegList.length; j++) {
                pegColumn[j] = pegList[j][i];

            }

            if (checkPegs(pegList[i]) || checkPegs(pegColumn)) {
                validMovement = true;
            }
        }
        Log.d("ValidMoves", String.valueOf(validMovement));
        return !validMovement;
    }


    private boolean checkPegs(PegButton[] pegButtons) {
        boolean validMovement = false;
        for (int i = 1; i < pegButtons.length - 1; i++) {
            if (!pegButtons[i].isDisabled) {
                if (!pegButtons[i - 1].isDisabled) {
                    if (!pegButtons[i].isEmpty) {
                        if (pegButtons[i - 1].isEmpty) {
                            if (!pegButtons[i + 1].isEmpty) {
                                validMovement = true;
                            }

                        } else {
                            if (pegButtons[i + 1].isEmpty) {
                                validMovement = true;

                            }
                        }
                    }
                }
            }
        }
        return validMovement;
    }

    private void undoMovement() {
        oldCurrentPeg.setEmpty(!oldCurrentPeg.isEmpty());
        oldMiddlePeg.setEmpty(!oldMiddlePeg.isEmpty());
        oldPreviousPeg.setEmpty(!oldPreviousPeg.isEmpty());

        remainingPegsText.setText(String.valueOf(existingPegs.size()));

        currentPeg = null;
        previousPeg = null;

        chronometer.start();

    }
}


