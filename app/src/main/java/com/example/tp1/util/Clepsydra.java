package com.example.tp1.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class Clepsydra extends View {

    private Paint backgroundRectPaint = new Paint() {{
        setARGB(255, 0, 0, 0);
    }};

    private Rect backgroundRect = new Rect();

    private Paint frontRectPaint = new Paint() {{
        setARGB(255, 187, 187, 187);
    }};

    private Rect frontRect = new Rect();

    public Clepsydra(Context context) {
        super(context);
    }

    public Clepsydra(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Clepsydra(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFillRatio(double ratio) {
        if (!(0 <= ratio && ratio <= 1)) {
            throw new IllegalArgumentException();
        }

        int width = getWidth();
        int height = getHeight();

        backgroundRect.set(0, 0, width, height);
        frontRect.set(0, (int) ( (((double) 1) - ratio) * (double) height), width, height);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(backgroundRect, backgroundRectPaint);

        canvas.drawRect(frontRect, frontRectPaint);

        super.onDraw(canvas);
    }
}
