package com.gjnm.androiddraw.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by gaojian12 on 17/8/15.
 */

public class RulerView extends View {

    // 刻度尺高度
    private static final int DIVIDING_RULE_HEIGHT = 70;
    // 距离左右间
    private static final int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 10;

    private static final int MAX_LINETOP = 25;
    private static final int MIDDLE_LINETOP = 20;
    private static final int MIN_LINETOP = 15;
    // 第一条线距离边框距离
    private static final int FIRST_LINE_MARGIN = 5;
    // 打算绘制的厘米数
    private static final int DEFAULT_COUNT = 9;

    private int mDividRuleHeight, mHalfRuleHeight, mFirstLineMargin;
    private int mDividRuleLeftMargin, top, mTotalWidth, mRuleBottom;
    private int mMaxLineTop, mMiddleLineTop, mMinLineTop;
    private int mLineInterval;

    private Resources mResources;
    private Paint rectPaint;
    private Paint linePaint;

    private Context mContext;

    public RulerView(Context context) {
        super(context);
        mContext = context;
        initData();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
    }

    private void initData() {

        rectPaint = new Paint();
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStyle(Paint.Style.STROKE);
        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        mResources = mContext.getResources();
        mDividRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_HEIGHT, mResources.getDisplayMetrics());
        mHalfRuleHeight = mDividRuleHeight / 2;

        mDividRuleLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DIVIDING_RULE_MARGIN_LEFT_RIGHT, mResources.getDisplayMetrics());
        mFirstLineMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                FIRST_LINE_MARGIN, mResources.getDisplayMetrics());

        mTotalWidth = mResources.getDisplayMetrics().widthPixels;
        top = mResources.getDisplayMetrics().heightPixels / 3;
        mRuleBottom = top + mDividRuleHeight;

        mMaxLineTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MAX_LINETOP, mResources.getDisplayMetrics());
        mMiddleLineTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MIDDLE_LINETOP, mResources.getDisplayMetrics());
        mMinLineTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MIN_LINETOP, mResources.getDisplayMetrics());

        mLineInterval = (mTotalWidth - 2 * mDividRuleLeftMargin - 2 * mFirstLineMargin)
                / (DEFAULT_COUNT * 10 - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制外框
        drawOuter(canvas);
        // 绘制刻度线
        drawLines(canvas);
        // 绘制数字
        drawNumbers(canvas);
    }

    private void drawOuter(Canvas canvas) {
        Rect mOutRect = new Rect(mDividRuleLeftMargin, top, mTotalWidth - mDividRuleLeftMargin,
                mRuleBottom);
        canvas.drawRect(mOutRect, rectPaint);
    }


    private void drawLines(Canvas canvas) {
        canvas.save();
        canvas.translate(mFirstLineMargin + mDividRuleLeftMargin, 0);
        int lTop = mMaxLineTop;
        for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
            if (i % 10 == 0) {
                lTop = mMaxLineTop;
            } else if (i % 5 == 0) {
                lTop = mMiddleLineTop;
            } else {
                lTop = mMinLineTop;
            }
            canvas.drawLine(0, mRuleBottom - lTop, 0, mRuleBottom, linePaint);
            canvas.translate(mLineInterval, 0);

        }
        canvas.restore();
    }

    private void drawNumbers(Canvas canvas) {
        canvas.save();
        canvas.translate(mFirstLineMargin + mDividRuleLeftMargin, 0);
        for (int i = 0; i <= DEFAULT_COUNT; i++) {
            canvas.drawText(i + "", 0, mRuleBottom - mMaxLineTop, linePaint);
            canvas.translate(mLineInterval * 10, 0);
        }
        canvas.restore();
    }


}
