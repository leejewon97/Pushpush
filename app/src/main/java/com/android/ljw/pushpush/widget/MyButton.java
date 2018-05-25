package com.android.ljw.pushpush.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class MyButton extends AppCompatButton {

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.GRAY);
        setTextColor(Color.WHITE);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.GRAY);
        setTextColor(Color.WHITE);
    }

}