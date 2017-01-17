package com.stew.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Stew on 2016/1/15.
 * TODO
 */
public class CircleView extends ImageView {

    private Paint inCirclePaint, mTextPaint;
    private Context context;
    private int radiusOfCircle;

    private int circleTag;

    private int mMeasureHeight;
    private int mMeasureWidth;

    private int statusOfCircle = 0;
    private AttrEntity attrEntity;

    private String titleCn;
    private String titleEn;

    public CircleView(Context context, AttrEntity attrEntity, int status, int i) {
        super(context);
        this.context = context;
        this.attrEntity = attrEntity;

        this.titleCn = attrEntity.aroundCircleTitleCn;
        this.titleEn = attrEntity.aroundCircleTitleEn;

        this.statusOfCircle = status;
        this.circleTag = i;
        initView();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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

        inCirclePaint = new Paint();
        inCirclePaint.setAntiAlias(true);

        if (statusOfCircle == AttrEntity.COMPLETE) {
            inCirclePaint.setColor(attrEntity.aroundCircleCompleteColor);
        } else if (statusOfCircle == AttrEntity.DOING) {
            inCirclePaint.setColor(attrEntity.aroundCircleDoingColor);
        } else if (statusOfCircle == AttrEntity.DEF) {
            inCirclePaint.setColor(attrEntity.aroundCircleDefColor);
        }

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(attrEntity.aroundCircleTitleTextSize);
        mTextPaint.setColor(attrEntity.aroundCircleTitleTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        int length;
        if (mMeasureHeight >= mMeasureWidth) {
            length = mMeasureWidth;
        } else {
            length = mMeasureHeight;
        }

        length = length - ViewUtil.dp2px(context, 20);
        radiusOfCircle = (length) / 2;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int center = getWidth() / 2;
        int offset1 = (int) getResources().getDimension(R.dimen.offset_7);
        int offset2 = (int) getResources().getDimension(R.dimen.offset_8);
        int offset4 = (int) getResources().getDimension(R.dimen.offset_5);
        int offset5 = (int) getResources().getDimension(R.dimen.offset_6);
        canvas.drawCircle(center, center, radiusOfCircle, inCirclePaint);

        if (circleTag == 0) {
            canvas.drawText(titleEn, 0, titleEn.length(), center, center + radiusOfCircle + offset1, mTextPaint);
            canvas.drawText(titleCn, 0, titleCn.length(), center, center + radiusOfCircle + offset2, mTextPaint);
        } else {
            canvas.drawText(titleEn, 0, titleEn.length(), center, center + radiusOfCircle + offset4, mTextPaint);
            canvas.drawText(titleCn, 0, titleCn.length(), center, center + radiusOfCircle + offset5, mTextPaint);
        }
        super.onDraw(canvas);
    }

}
