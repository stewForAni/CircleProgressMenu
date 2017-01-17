package com.stew.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Stew on 2016/1/15.
 * TODO
 */
public class whiteCircleView extends TextView {

    private Paint CirclePaint;
    private Context context;

    private int circleColor;
    private int RadiusCircle;
    private int mMeasureHeight;
    private int mMeasureWidth;

    public whiteCircleView(Context context, int circleColor) {
        super(context);
        this.context = context;
        this.circleColor = circleColor;
        initView();
    }

    public whiteCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public whiteCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mMeasureWidth, mMeasureHeight);
        initView();
    }

    private void initView() {
        CirclePaint = new Paint();
        CirclePaint.setAntiAlias(true);
        CirclePaint.setColor(circleColor);

        int length;
        if (mMeasureHeight >= mMeasureWidth) {
            length = mMeasureWidth;
        } else {
            length = mMeasureHeight;
        }
        RadiusCircle = length / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        canvas.drawCircle(center, center, RadiusCircle, CirclePaint);
        super.onDraw(canvas);
    }

}
