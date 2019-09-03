package com.example.haoss.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SignInCalendar extends View {

    private Context context;
    private Paint paint;

    public SignInCalendar(Context context) {
        this(context, null);
    }

    public SignInCalendar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 1);
    }

    public SignInCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100, 100, 50f, paint);
    }
}
