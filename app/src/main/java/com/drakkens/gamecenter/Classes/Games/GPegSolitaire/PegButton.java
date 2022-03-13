package com.drakkens.gamecenter.Classes.Games.GPegSolitaire;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.drakkens.gamecenter.R;


public class PegButton extends androidx.appcompat.widget.AppCompatButton {
    public boolean isDisabled;
    public boolean isEmpty;
    private int posX;
    private int posY;
    private boolean clicked;


    public PegButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray xmlValues = context.obtainStyledAttributes(attrs, R.styleable.PegButton);
        this.isDisabled = xmlValues.getBoolean(R.styleable.PegButton_isDisabled, false);
        this.isEmpty = xmlValues.getBoolean(R.styleable.PegButton_isEmpty, false);

        if (isDisabled) {
            this.setClickable(false);
            this.setVisibility(GONE);
        }

        if (isEmpty) {
            this.setBackgroundDrawable(getResources().getDrawable(R.drawable.empty_peg));

        } else {
            PegMain.existingPegs.add(this);
            this.setBackgroundDrawable(getResources().getDrawable(R.drawable.peg_drawable));
        }

        xmlValues.recycle();

    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;

        if (empty) {
            this.setBackgroundDrawable(getResources().getDrawable(R.drawable.empty_peg));
            PegMain.existingPegs.remove(this);
        }
        else {
            this.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.peg_drawable));
            PegMain.existingPegs.add(this);
        }

    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public boolean getClicked() {
        return this.clicked;

    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;

        if (clicked) {
            this.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.peg_drawable_clicked));
        } else {
            this.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.peg_drawable));
        }
    }
}
