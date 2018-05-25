package com.android.ljw.pushpush;

import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    Paint paint;
    int x, y;

    public Player() {
        paint = new Paint();
        paint.setColor(Color.RED);
        x = 0;
        y = 0;
    }

    public void up() {
        y--;
    }

    public void down() {
        y++;
    }

    public void left() {
        x--;
    }

    public void right() {
        x++;
    }
}
