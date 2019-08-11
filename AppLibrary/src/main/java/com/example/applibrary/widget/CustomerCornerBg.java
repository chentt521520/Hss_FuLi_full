package com.example.applibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.applibrary.utils.DensityUtil;

public class CustomerCornerBg extends Drawable {

    private Context context;
    private Paint paint;

    public CustomerCornerBg(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        int width = DensityUtil.getScreenWidth(context) - 100;
        int height = DensityUtil.dip2px(context, 35f);
        RectF reft = new RectF(0, 0, width, height);
        canvas.drawRoundRect(reft, height/2, height/2, paint);
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
