package com.drakkens.gamecenter.Classes.Games.G2048;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.drakkens.gamecenter.Classes.Games.Utils.EnhancedChronometer;
import com.drakkens.gamecenter.Classes.Games.Utils.GameOverFragment;
import com.drakkens.gamecenter.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends AppCompatActivity {
    private final TableCell[][] tableCells = new TableCell[4][4];
    public static ArrayList<TableCell> emptyTableCells = new ArrayList<>();
    private GestureDetectorCompat detector;
    private int score = 0;
    private TextView scoreTextView;
    private EnhancedChronometer chronometer;
    private int[][] oldTableValues;
    private int oldScore;
    boolean lost;

    private boolean started = false;

    @Override
    protected void onStart() {
        emptyTableCells = new ArrayList<>();
        for (TableCell[] tc:tableCells) {
            emptyTableCells.addAll(Arrays.asList(tc));
        }

        populateRandomCell();
        saveTable();

        super.onStart();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_2048);

        detector = new GestureDetectorCompat(getApplicationContext(), new GestureListener());
        scoreTextView = findViewById(R.id.scoreTextView);
        chronometer = findViewById(R.id.timer);

        oldTableValues = new int[tableCells.length][tableCells[0].length];

        Button undoButton = findViewById(R.id.undo2048);

        undoButton.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) undoMovement();

            return true;
        });

        int count = 0;

        for (int i = 0; i < tableCells[0].length; i++) {
            for (int j = 0; j < tableCells.length; j++) {
                int id = this.getResources().getIdentifier("textView" + count, "id", this.getPackageName());
                TableCell tableCell = findViewById(id);

                tableCell.setX(i);
                tableCell.setY(j);

                tableCells[i][j] = tableCell;
                emptyTableCells.add(tableCell);

                count += 1;

            }
        }

    }

    private void movePiecesByDirection(MovementDirection direction) {
        if (!started) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();

        }

        started = true;


        boolean spawnNumber = false;

        saveTable();

        for (TableCell[] tc : tableCells) {
            for (TableCell tc2 : tc) {
                tc2.setMerged(false);
            }
        }

        if (direction.getGeneralAxis() == MovementDirection.GeneralAxis.HORIZONTAL) {
            for (TableCell[] tableCellRow : tableCells) {
                if (relocateZeros(tableCellRow, direction.opposite().getStartPosition(tableCells)[1], direction.opposite().toInt()[1])) spawnNumber = true;
                if (mergePieces(tableCellRow, direction.getStartPosition(tableCells)[1], direction.toInt()[1], direction)) spawnNumber = true;

            }
        } else {
            for (int i = 0; i < tableCells[0].length; i++) {
                if (relocateZeros(getTableColumn(i), direction.opposite().getStartPosition(tableCells)[0], direction.opposite().toInt()[0])) spawnNumber = true;
                if (mergePieces(getTableColumn(i), direction.getStartPosition(tableCells)[0], direction.toInt()[0], direction)) spawnNumber = true;

            }
        }

        if (spawnNumber) {
            populateRandomCell();

        }

        if (checkLost()) {
            chronometer.stop();
            GameOverFragment fragment = new GameOverFragment(com.drakkens.gamecenter.Classes.GameCenter.Main.database, com.drakkens.gamecenter.Classes.GameCenter.Main.user, score, chronometer.getTotalSeconds());
            fragment.show(getSupportFragmentManager(), null);

        }

    }

    private TableCell[] getTableColumn(int columnNum) {
        TableCell[] tableColumn = new TableCell[tableCells.length];
        for (int i = 0; i < tableCells[0].length; i++) {
            tableColumn[i] = tableCells[i][columnNum];

        }

        return tableColumn;

    }

    private boolean relocateZeros(TableCell[] tableCellRow, int startPos, int next) {
        boolean moved = false;
        boolean inProgress;
        do {
            inProgress = false;
            int tempStartPos = startPos;
            for (int i = tableCellRow.length - 1; i > 0; i--) {
                if (tableCellRow[tempStartPos].getValue() != 0 && tableCellRow[tempStartPos + next].getValue() == 0) {
                    tableCellRow[tempStartPos + next].setValue(tableCellRow[tempStartPos].getValue());
                    tableCellRow[tempStartPos].setValue(0);
                    inProgress = true;
                    moved = true;
                }
                tempStartPos += next;

            }
        } while (inProgress);
        return moved;
    }

    private boolean mergePieces(TableCell[] tableCellsRow, int startPos, int next, MovementDirection direction) {
        boolean moved = false;

        for (int i = tableCellsRow.length - 1; i > 0; i--) {
            if (tableCellsRow[startPos].getValue() == tableCellsRow[startPos + next].getValue() && !tableCellsRow[startPos].isMerged()) {
                tableCellsRow[startPos].setValue(tableCellsRow[startPos].getValue() + tableCellsRow[startPos + next].getValue());
                tableCellsRow[startPos].setMerged(true);
                tableCellsRow[startPos + next].setValue(0);

                score += tableCellsRow[startPos].getValue();
                scoreTextView.setText(String.valueOf(score));

                if (tableCellsRow[startPos].getValue() != 0) moved = true;


                if (direction.getGeneralAxis() == MovementDirection.GeneralAxis.HORIZONTAL) {
                    relocateZeros(tableCellsRow, direction.opposite().getStartPosition(tableCells)[1], direction.opposite().toInt()[1]);
                } else {
                    relocateZeros(tableCellsRow, direction.opposite().getStartPosition(tableCells)[0], direction.opposite().toInt()[0]);

                }
            }

            startPos += next;
        }
        return moved;
    }

    private boolean checkLost() {
        boolean lost = true;
        if (emptyTableCells.size() == 0) {
            for (int i = 0; i < tableCells.length; i++) {
                TableCell[] tc = getTableColumn(i);
                for (int j = 0; j < tc.length - 1; j++) {
                    if (tc[j].getValue() == tc[j + 1].getValue()) {
                        return false;
                    }

                }
                for (int j = 0; j < tableCells[0].length - 1; j++) {
                    if (tableCells[i][j].getValue() == tableCells[i][j + 1].getValue()) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return lost;
    }

    private void undoMovement() {
        for (int i = 0; i < tableCells.length; i++) {
            for (int j = 0; j < tableCells[0].length; j++) {
                tableCells[i][j].setValue(oldTableValues[i][j]);
                chronometer.start();

            }
        }
        score = oldScore;
        scoreTextView.setText(String.valueOf(score));

    }

    private void saveTable() {
        for (int i = 0; i < tableCells.length; i++) {
            for (int j = 0; j < tableCells[0].length; j++) {
                if (oldTableValues[i][j] != tableCells[i][j].getValue()) oldTableValues[i][j] = tableCells[i][j].getValue();
            }
        }

        oldScore = score;

    }


    private MovementDirection checkDirection(MotionEvent e1, MotionEvent e2) {

        //Fling direction detector
        double xVar = e2.getX() - e1.getX();
        double yVar = e2.getY() - e1.getY();

        if (Math.abs(xVar) > Math.abs(yVar)) {
            if (xVar < 0) {
                return MovementDirection.LEFT;

            } else {
                return MovementDirection.RIGHT;

            }

        } else {
            if (yVar < 0) {
                return MovementDirection.UP;

            } else {
                return MovementDirection.DOWN;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void populateRandomCell() {
        if (emptyTableCells.size() > 0) {
            int emptyIndex = (int) Math.floor(Math.random() * emptyTableCells.size());

            TableCell tableCell = emptyTableCells.get(emptyIndex);

            if (Math.random() > 0.5f) tableCell.setValue(2);
            else tableCell.setValue(4);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*-------------------------
           Gesture Listener
     ------------------------*/


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("Movement", e1.getX() + " " + e1.getY() + " " + e2.getX() + " " + e2.getY());

            MovementDirection direction = checkDirection(e1, e2);

            if (direction != MovementDirection.UNDEFINED) {
                movePiecesByDirection(direction);


            }

            return true;
        }
    }
}
