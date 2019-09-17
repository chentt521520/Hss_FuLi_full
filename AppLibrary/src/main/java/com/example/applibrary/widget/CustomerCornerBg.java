package com.example.applibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.applibrary.R;
import com.example.applibrary.utils.DensityUtil;

public class CustomerCornerBg extends View {

    private Context context;
    private Paint paint;
    private Paint paint2;
    private int padding;
    private int height;

    public CustomerCornerBg(Context context) {
        this(context, null);
    }

    public CustomerCornerBg(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 1);
    }

    public CustomerCornerBg(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.padding = DensityUtil.dip2px(context, 20f);
        this.height = DensityUtil.getScreenHeight(context);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint2 = new Paint();
        paint2.setColor(Color.GRAY);
        paint2.setAntiAlias(true);
        paint2.setTextSize(DensityUtil.sp2px(context, 12f));
    }

    public void setSize(int padding, int height) {
        this.padding = padding;
        this.height = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = DensityUtil.getScreenWidth(context) - 2 * padding;
        RectF rect = new RectF(padding, 0, width + padding, height);
        canvas.drawRoundRect(rect, height / 2, height / 2, paint);


        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_search);

        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        matrix.postTranslate(padding * 2f, (height - bitmap.getHeight()) / 2);
        canvas.drawBitmap(bitmap, matrix, paint);

        Paint.FontMetrics fm = paint2.getFontMetrics();
        int textHeight = (int) (fm.descent - fm.ascent);
        int baseLine = height - textHeight;
        Path path = new Path();
        path.moveTo(padding, baseLine);
        path.lineTo(width + padding, baseLine);
        canvas.drawTextOnPath("搜索水果，蛋糕", path, bitmap.getWidth() + DensityUtil.dip2px(getContext(), 25f), 0, paint2);
    }
}
