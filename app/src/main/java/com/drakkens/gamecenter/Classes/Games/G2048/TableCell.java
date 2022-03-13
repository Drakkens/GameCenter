package com.drakkens.gamecenter.Classes.Games.G2048;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

import com.drakkens.gamecenter.Classes.Games.G2048.Main;
import com.drakkens.gamecenter.R;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class TableCell extends androidx.appcompat.widget.AppCompatTextView {
    private boolean merged = false;
    private int value = 0;
    private int xPos;
    private int yPos;
    private final int[] pos = new int[]{xPos, yPos};
    private final HashMap<Integer, String> colorMap = new HashMap<Integer, String>();

    public TableCell(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        colorMap.put(0, "ffffffff");
        colorMap.put(2, "ffede5db");
        colorMap.put(4, "ffeddfc5");
        colorMap.put(8, "fff2b077");
        colorMap.put(16, "fff59563");
        colorMap.put(32, "fff77d62");
        colorMap.put(64, "fff45f3e");
        colorMap.put(128, "ffecce73");
        colorMap.put(256, "ffefca63");
        colorMap.put(512, "ffeac74e");
        colorMap.put(1024, "ffeec541");
        colorMap.put(2048, "ffff3d3a");

        this.setValue(0);

    }

/*--------------------------
        Getters & Setters
    ------------------------- */

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        if (value != 0) {
            this.changeText(String.valueOf(value));
            Main.emptyTableCells.remove(this);

        }
        if (value == 0) {
            this.changeText("");
            AtomicBoolean exists = new AtomicBoolean(false);
            Main.emptyTableCells.forEach(tableCell -> {
                if (tableCell == this) exists.set(true);

            });
            if (!exists.get()) Main.emptyTableCells.add(this);
        }
        this.changeColor();
    }

    private void changeColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.setBackgroundColor(Color.parseColor(colorMap.get(this.getValue())));
//            this.setBackgroundDrawable(getResources().getDrawable(getResources().getIdentifier("Color" + colorMap.get(value), "drawable", getContext().getPackageName())));
            this.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), getResources().getIdentifier("color_" + colorMap.get(value), "drawable", getContext().getPackageName()), null));

        }
    }

    public int getxPos() {
        return xPos;
    }

    public void setX(int x) {
        xPos = x;
    }

    public int getyPos() {
        return yPos;
    }

    public void setY(int y) {
        yPos = y;
    }

    public void changeText(String text) {
        this.setText(text);
    }

    public int[] getNext(MovementDirection direction) {
        if (this.getxPos() + direction.toInt()[0] >= 0 && this.getyPos() + direction.toInt()[1] >= 0) {
            return new int[]{this.getxPos() + direction.toInt()[0], this.getyPos() + direction.toInt()[1]};
        }
        return null;
    }
}
