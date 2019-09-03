package com.example.haoss.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.applibrary.utils.DensityUtil;

public class CoustmerHalfBg extends Drawable {

    private Paint paint;
    private Context context;
    private int color;

    public CoustmerHalfBg(Context context, int color) {
        this.context = context;
        this.color = color;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int width = DensityUtil.getScreenWidth(context);
        paint.setColor(color);
        canvas.drawRect(0, 0, width, DensityUtil.dip2px(context, 200f), paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, DensityUtil.dip2px(context, 200f), width, DensityUtil.dip2px(context, 250f), paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
