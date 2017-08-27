package com.gjnm.androiddraw.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by gaojian12 on 17/8/17.
 */

public class AnimaHeartView extends View {

    private static final String TAG = "AnimaHeartView";

    private static final int PATH_WIDTH = 4;
    // 起始点
    private static final int[] START_POINT = new int[]{
            600, 300
    };
    // 爱心下端点
    private static final int[] BOTTOM_POINT = new int[]{
            600, 750
    };
    // 左侧控制点
    private static final int[] LEFT_CONTROL_POINT = new int[]{
            300, 100
    };
    // 右侧控制点
    private static final int[] RIGHT_CONTROL_POINT = new int[]{
            900, 100
    };

    private PathMeasure mPathMeasure;
    private Paint mPaint;
    private Paint textPaint;
    private Path mPath;
    private float[] mCurrentPosition = new float[2];

    public AnimaHeartView(Context context) {
        super(context);
        init();
    }

    public AnimaHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimaHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPath.moveTo(START_POINT[0], START_POINT[1]);
        mPath.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0], BOTTOM_POINT[1]);
        mPath.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PATH_WIDTH);
        mPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(50);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);

        mPathMeasure = new PathMeasure(mPath, true);
        mCurrentPosition = new float[2];
        mCurrentPosition[0] = START_POINT[0];
        mCurrentPosition[1] = START_POINT[1];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        canvas.drawCircle(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], 5, mPaint);
        canvas.drawCircle(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], 5, mPaint);

        canvas.drawCircle(mCurrentPosition[0], mCurrentPosition[1], 10, mPaint);
        canvas.drawText("点我", START_POINT[0], START_POINT[1] + 100, textPaint);
    }

    public void startPathAnim(long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        Log.i(TAG, "measure length = " + mPathMeasure.getLength());

        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
