package com.stew.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * Created by Stew on 2016/1/15.
 * mail: stewforani@gmail.com
 */

public class CircleLayout extends RelativeLayout {

    //center arc paint def color
    private Paint centerArcDefPaint;

    //center arc paint with angleAnimation
    private Paint centerArcPaint;

    //center circle paint
    private Paint centerCirclePaint;

    //center circle top text paint  （u can use start、continue、done as progress text）
    private Paint centerCircleTopTextPaint;

    //center circle bottom text paint  (u can use around circle title as progress detail text)
    private Paint centerCircleBottomPaint;

    //start degree in a clockwise direction
    private float degree = 90;

    //the values of matrix by rotate the degree
    private float[] values = new float[9];

    //around circle layout width ,height > width
    int aroundCircleLayoutWidth = (int) getResources().getDimension(R.dimen.aroundCircleLayoutWidth);

    //around circle layout height
    int aroundCircleLayoutHeight = (int) getResources().getDimension(R.dimen.aroundCircleLayoutHeight);

    //around circle layout width ,height = width
    int aroundWhiteCircleLayoutWidth = (int) getResources().getDimension(R.dimen.aroundWhiteCircleLayoutWidth);

    //around circle layout height
    int aroundWhiteCircleLayoutHeight = (int) getResources().getDimension(R.dimen.aroundWhiteCircleLayoutHeight);

    //center to around circle radius
    int centerToAroundCircleRadius = (int) getResources().getDimension(R.dimen.outRadius);

    //around circle bottom title text en
    private int[] aroundCircleTitleEn;

    //around circle bottom title text cn
    private int[] aroundCircleTitleCn;

    //around circle word ,set by yourself
    private int[] circleIcon;

    //status of every circle
    private int[] circleCompleteStatusList;

    //count of around circles
    private int aroundCircleCount;

    //current progress num
    private int progressNum;


    private String centerCircleText;
    private float centerCircleTextSize;
    private int centerCircleTextColor;
    private int centerCircleColor;
    private int centerArcColorDef;
    private int centerArcColor;
    private int aroundCircleDefColor;
    private int aroundCircleDoingColor;
    private int aroundCircleCompleteColor;

    private int aroundSmallCircleColor;
    private float aroundCircleTitleTextSize;
    private int aroundCircleTitleTextColor;


    private Context context;
    private Matrix matrix;
    private circleClickListener mListener;
    private AttrEntity attrEntity = new AttrEntity();
    private float mSweepAnglePer;
    private float mSweepAngle = 0;
    private Animation anim;
    RectF mRectangle = new RectF();

    public CircleLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CircleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttrs(attrs);
    }

    public CircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        int defColor = getResources().getColor(R.color.colorPrimary);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressMenu);
        centerCircleText = typedArray.getString(R.styleable.CircleProgressMenu_centerCircleText);
        centerCircleTextSize = typedArray.getDimension(R.styleable.CircleProgressMenu_centerCircleTextSize, 0f);
        centerCircleTextColor = typedArray.getColor(R.styleable.CircleProgressMenu_centerCircleTextColor, defColor);

        centerCircleColor = typedArray.getColor(R.styleable.CircleProgressMenu_centerCircleColor, defColor);

        centerArcColorDef = typedArray.getColor(R.styleable.CircleProgressMenu_centerArcColorDef, defColor);
        centerArcColor = typedArray.getColor(R.styleable.CircleProgressMenu_centerArcColor, defColor);

        aroundCircleDefColor = typedArray.getColor(R.styleable.CircleProgressMenu_aroundCircleDefColor, defColor);
        aroundCircleDoingColor = typedArray.getColor(R.styleable.CircleProgressMenu_aroundCircleDoingColor, defColor);
        aroundCircleCompleteColor = typedArray.getColor(R.styleable.CircleProgressMenu_aroundCircleCompleteColor, defColor);

        aroundSmallCircleColor = typedArray.getColor(R.styleable.CircleProgressMenu_aroundSmallCircleColor, defColor);

        aroundCircleTitleTextSize = typedArray.getDimension(R.styleable.CircleProgressMenu_titleSize, 0f);
        aroundCircleTitleTextColor = typedArray.getColor(R.styleable.CircleProgressMenu_titleColor, defColor);
        typedArray.recycle();
    }

    public void initView() {
        setWillNotDraw(false);
        initPaintAndAnim();

        /**
         * add circle around of the layout
         */
        addAroundCircle();

        /**
         * add small white circle around of the layout
         */
        addAroundSmallCircle();
    }


    private void addAroundCircle() {

        matrix = new Matrix();

        attrEntity.aroundCircleDefColor = aroundCircleDefColor;
        attrEntity.aroundCircleDoingColor = aroundCircleDoingColor;
        attrEntity.aroundCircleCompleteColor = aroundCircleCompleteColor;
        attrEntity.aroundCircleTitleTextSize = aroundCircleTitleTextSize;
        attrEntity.aroundCircleTitleTextColor = aroundCircleTitleTextColor;

        for (int i = 0; i < aroundCircleCount; i++) {
            matrix.setRotate(degree);
            matrix.getValues(values);
            degree -= 360.0f / aroundCircleCount;
            attrEntity.aroundCircleTitleCn = getResources().getString(aroundCircleTitleCn[i]);
            attrEntity.aroundCircleTitleEn = getResources().getString(aroundCircleTitleEn[i]);
            CircleView itemCView = new CircleView(context, attrEntity, circleCompleteStatusList[i], i);
            LayoutParams params = new LayoutParams(aroundCircleLayoutWidth, aroundCircleLayoutHeight);
            params.leftMargin = (int) ((1 + values[0]) * centerToAroundCircleRadius);
            params.topMargin = (int) ((1 - values[3]) * centerToAroundCircleRadius);
            itemCView.setLayoutParams(params);
            itemCView.setTag(i + 1);

            if (circleIcon != null && circleIcon.length > 0) {
                itemCView.setImageResource(circleIcon[i]);
            }

            itemCView.setPadding(ViewUtil.dp2px(context, 28), ViewUtil.dp2px(context, 28), ViewUtil.dp2px(context, 28), ViewUtil.dp2px(context, 58));
            itemCView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    mListener.click(tag);
                }
            });

            addView(itemCView);
        }
        degree = 90;
        matrix.reset();
    }


    private void addAroundSmallCircle() {

        for (int i = 0; i < aroundCircleCount; i++) {
            matrix.setRotate(degree);
            matrix.getValues(values);
            degree -= 360.0f / aroundCircleCount;
            whiteCircleView wCView = new whiteCircleView(context, aroundSmallCircleColor);
            LayoutParams params = new LayoutParams(aroundWhiteCircleLayoutWidth, aroundWhiteCircleLayoutHeight);
            params.leftMargin = (int) ((1 + values[0]) * (centerToAroundCircleRadius) + ViewUtil.dp2px(context, 32) - values[0] * ViewUtil.dp2px(context, 30));
            params.topMargin = (int) ((1 - values[3]) * (centerToAroundCircleRadius) + ViewUtil.dp2px(context, 32) + values[3] * ViewUtil.dp2px(context, 30));
            wCView.setLayoutParams(params);
            wCView.setText(String.valueOf(i + 1));
            wCView.setTextSize(12);
            TextPaint tp = wCView.getPaint();
            tp.setFakeBoldText(true);
            wCView.setGravity(Gravity.CENTER);
            addView(wCView);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //the range of arc
        int arc = (int) getResources().getDimension(R.dimen.arc);

        //offset of top and bottom of arc range(cause: bottom of first around circle has text)
        int offset = (int) getResources().getDimension(R.dimen.offset_1);

        //center circle radius
        int centerCircleRadius = (int) getResources().getDimension(R.dimen.centerCircleRadius);

        //the coordinates of center circle
        int center = getWidth() / 2;

        mRectangle.set(center - arc, center + offset - arc, center + arc, center + offset + arc);
        canvas.drawArc(mRectangle, -90, 360, false, centerArcDefPaint);
        canvas.drawArc(mRectangle, -90, mSweepAnglePer, false, centerArcPaint);
        canvas.drawCircle(center, center + offset, centerCircleRadius, centerCirclePaint);


        if (progressNum < aroundCircleCount) {
            if (!TextUtils.isEmpty(centerCircleText)) {
                canvas.drawText(centerCircleText, 0, centerCircleText.length(), center, center, centerCircleTopTextPaint);
            }
            String str = context.getResources().getString(aroundCircleTitleEn[progressNum]);
            if (!TextUtils.isEmpty(str)) {
                canvas.drawText(str, 0, str.length(), center, center + 3 * offset, centerCircleBottomPaint);
            }
        } else {
            String str = "All Done";
            canvas.drawText(str, 0, str.length(), center, center + offset, centerCircleTopTextPaint);
        }


        super.onDraw(canvas);
    }

    public interface circleClickListener {
        void click(int tag);
    }

    public void setOnClickListener(circleClickListener mListener) {
        this.mListener = mListener;
    }

    public void startAnim(float degree) {
        this.mSweepAngle = degree;
        this.startAnimation(anim);
    }

    public class AngleAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                mSweepAnglePer = interpolatedTime * mSweepAngle;
            } else {
                mSweepAnglePer = mSweepAngle;
            }
            postInvalidate();
        }
    }


    private void initPaintAndAnim() {

        centerArcDefPaint = new Paint();
        centerArcDefPaint.setAntiAlias(true);
        centerArcDefPaint.setStyle(Paint.Style.STROKE);
        centerArcDefPaint.setStrokeWidth(getResources().getDimension(R.dimen.arcStrokeWidth));
        centerArcDefPaint.setColor(centerArcColorDef);

        centerArcPaint = new Paint();
        centerArcPaint.setAntiAlias(true);
        centerArcPaint.setStyle(Paint.Style.STROKE);
        centerArcPaint.setStrokeWidth(getResources().getDimension(R.dimen.arcStrokeWidth));
        centerArcPaint.setStrokeJoin(Paint.Join.ROUND);
        centerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        centerArcPaint.setColor(centerArcColor);

        centerCirclePaint = new Paint();
        centerCirclePaint.setAntiAlias(true);
        centerCirclePaint.setColor(centerCircleColor);

        centerCircleTopTextPaint = new Paint();
        centerCircleTopTextPaint.setAntiAlias(true);
        centerCircleTopTextPaint.setTextSize(centerCircleTextSize);
        centerCircleTopTextPaint.setColor(centerCircleTextColor);
        centerCircleTopTextPaint.setTextAlign(Paint.Align.CENTER);

        centerCircleBottomPaint = new Paint();
        centerCircleBottomPaint.setAntiAlias(true);
        centerCircleBottomPaint.setTextSize(centerCircleTextSize);
        centerCircleBottomPaint.setColor(centerCircleTextColor);
        centerCircleBottomPaint.setTextAlign(Paint.Align.CENTER);

        anim = new AngleAnimation();
        anim.setDuration(1500);
    }

    public void setView(int[] aroundCircleTitleEn,
                        int[] aroundCircleTitleCn,
                        int[] circleIcon,
                        int aroundCircleCount,
                        int[] circleCompleteStatusList) {

        if (aroundCircleTitleEn.length != aroundCircleCount
                || aroundCircleTitleCn.length != aroundCircleCount
                || circleIcon.length != aroundCircleCount
                || circleCompleteStatusList.length != aroundCircleCount) {
            return;
        }

        if (progressNum > aroundCircleCount) {
            return;
        }

        this.aroundCircleTitleEn = aroundCircleTitleEn;
        this.aroundCircleTitleCn = aroundCircleTitleCn;
        this.circleIcon = circleIcon;
        this.aroundCircleCount = aroundCircleCount;
        this.circleCompleteStatusList = circleCompleteStatusList;
    }

    public void setProgressNum(int progressNum) {
        this.progressNum = progressNum;
    }


}
