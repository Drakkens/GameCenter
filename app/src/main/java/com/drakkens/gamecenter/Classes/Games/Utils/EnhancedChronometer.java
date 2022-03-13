package com.drakkens.gamecenter.Classes.Games.Utils;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;

public class EnhancedChronometer extends Chronometer {

    public EnhancedChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getTotalSeconds() {
        return (int) ((SystemClock.elapsedRealtime() - this.getBase()) / 1000);

    }

}
