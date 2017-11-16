package com.gjnm.androiddraw.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.gjnm.androiddraw.R;
import com.gjnm.androiddraw.untils.CompatibilityHelper;
import com.gjnm.androiddraw.untils.UiUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;

public class AntiVirusScanView extends View {

    private int mHeight;
    private int mWidth;
    private DrawFilter mDrawFilter;   // 过滤器可以禁用/启用抗锯齿,或改变颜色
    private AntivirusModuleCircle mCircles;
    private Point mCenterPoint;
    private Path mRotateCirclePath;
    private Path mLittleCirclePath;
    private Path mBigCirclePath;
    private Path mCenterLinePath;
    private Path mOutSideLinePath;
    private Path mProgressPath;
    private Paint mPaintCircle;
    private Bitmap mPhoneBitmap;
    private Bitmap mPhoneLineBitmap;
    private float mPhoneWidth;
    private float mPhoneHeight;
    private float mRunTime = 1400;
    private float mSleepTime = 600;
    private Paint mLightCirclePaint;
    private Point[] mPoint4;
    private Paint mPointsPaint;
    private Paint mPaintOutLines;
    private Paint mPaintInLines;
    private Paint mCenterCirclePaint;
    private Paint mLightPaint;
    private Paint mProgressPaint;
    private Paint mPaintRoundCircle;
    private float mCurrentProgress = 0;

    private int phone_Light_StrokeWidth;
    private int points_StrokeWidth;
    private int progress_StrokeWidth;
    private ValueAnimator startAnimator;
    private ValueAnimator endAnimator;
    private int mR;

    private Bitmap mXfermodeBitmap;
    private final PorterDuffXfermode mXfermode;

    private Paint mPaintBackground;

    private Matrix mPhoneMatrix;
    private Matrix mPointMatrix;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AntiVirusScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        phone_Light_StrokeWidth = UiUtils.dipToPx(2);
        points_StrokeWidth = UiUtils.dipToPx(1);
        progress_StrokeWidth = UiUtils.dipToPx(6);
        mPhoneBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.phone_light);
        // 设置光源的方向
        float[] direction = new float[]{1, 1, 1};
        //设置环境光亮度
        float light = 0.9f;
        // 选择要应用的反射等级
        float specular = 6;
        // 向mask应用一定级别的模糊
        float blur = 3.5f;
        EmbossMaskFilter emboss = new EmbossMaskFilter(direction, light, specular, blur);
        // 应用mask myPaint.setMaskFilter(emboss);
        mPhoneWidth = mPhoneBitmap.getWidth();
        mPhoneHeight = mPhoneBitmap.getHeight();
        mPhoneLineBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.phone_line);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.rgb(106, 129, 187));
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(phone_Light_StrokeWidth);

        mPaintRoundCircle = new Paint();
        mPaintRoundCircle.setColor(Color.rgb(106, 129, 187));
        mPaintRoundCircle.setStyle(Paint.Style.STROKE);
        mPaintRoundCircle.setStrokeWidth(phone_Light_StrokeWidth);
        mPaintRoundCircle.setAlpha((int) (255 * 0.3));

        mRotateCirclePath = new Path();
        mLittleCirclePath = new Path();
        mBigCirclePath = new Path();
        mCenterLinePath = new Path();
        mOutSideLinePath = new Path();
        mLightCirclePaint = new Paint();
        mLightCirclePaint.setColor(Color.rgb(106, 129, 187));
        mLightCirclePaint.setStyle(Paint.Style.STROKE);
        mLightCirclePaint.setStrokeWidth(phone_Light_StrokeWidth);

        mPointsPaint = new Paint();
        mPointsPaint.setColor(Color.rgb(106, 129, 187));
        mPointsPaint.setStyle(Paint.Style.STROKE);
        mPointsPaint.setStrokeWidth(points_StrokeWidth);
        mPointsPaint.setAntiAlias(true);

        mPaintOutLines = new Paint();
        mPaintOutLines.setColor(Color.rgb(106, 129, 187));
        mPaintOutLines.setStyle(Paint.Style.STROKE);
        mPaintOutLines.setStrokeWidth(points_StrokeWidth);
        mPaintOutLines.setAntiAlias(true);

        mPaintInLines = new Paint();
        mPaintInLines.setColor(Color.rgb(255, 255, 255));
        mPaintInLines.setStyle(Paint.Style.STROKE);
        mPaintInLines.setStrokeWidth(points_StrokeWidth * 0.5f);
        mPaintInLines.setAlpha(60);
        mPaintInLines.setAntiAlias(true);

        mCenterCirclePaint = new Paint();
        mCenterCirclePaint.setColor(Color.rgb(150, 170, 246));
        mCenterCirclePaint.setStyle(Paint.Style.STROKE);
        mCenterCirclePaint.setStrokeWidth(points_StrokeWidth);
        mCenterCirclePaint.setAntiAlias(true);

        mLightPaint = new Paint();
        mLightPaint.setColor(Color.rgb(255, 255, 255));
        mLightPaint.setStyle(Paint.Style.STROKE);
        mLightPaint.setStrokeWidth(phone_Light_StrokeWidth * 0.5f);
        mLightPaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(Color.rgb(150, 170, 246));
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(progress_StrokeWidth);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPath = new Path();

        mPaintBackground = new Paint();
        mPaintBackground.setColor(Color.rgb(28, 26, 53));
        mPaintBackground.setStyle(Paint.Style.FILL);
        mPaintBackground.setStrokeWidth(5);
        mPaintBackground.setAntiAlias(true);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        //关闭硬件加速
        if (CompatibilityHelper.isHigherAndroid3_0()) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        drawPhoneAfter(canvas);
        long currentTime = System.currentTimeMillis();
        mRotateCirclePath.reset();  // 清除path上的内容，重置path到 path = new Path()的初始状态
        mLittleCirclePath.reset();  // Path.rewind():清除path上的内容，但会保留path上相关的数据结构，以高效的复用
        mBigCirclePath.reset();
        mProgressPath.reset();
        mR = mCircles.getLittleCirclePath(mLittleCirclePath, currentTime);
        mCircles.getBigCirclePath(mBigCirclePath, currentTime);
        mCircles.getCirclePath(mRotateCirclePath);
        canvas.drawPath(mBigCirclePath, mPaintCircle);
        canvas.drawPath(mRotateCirclePath, mPaintRoundCircle);
        ArrayList<Point> points = mCircles.getPointCircle();
        for (Point p : points
                ) {
            canvas.drawPoint(p.x, p.y, mPointsPaint);

        }
        drawLines(canvas);
        drawLinesBefore(canvas);

        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mR, mPaintBackground);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        mPaintCircle.setColor(Color.BLACK);
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setStrokeWidth(5);
        mPaintCircle.setAntiAlias(true);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mR, mPaintCircle);
        mPaintCircle.setXfermode(mXfermode); // DST_IN
        mPaintCircle.setColor(Color.rgb(106, 129, 187));
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(phone_Light_StrokeWidth);
        drawPhoneLineAfter(canvas);

        mPaintCircle.setXfermode(null);
        canvas.restoreToCount(sc);

        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mR, mCenterCirclePaint);
        mCircles.getProgressCircle(mCurrentProgress, canvas, mProgressPaint, mProgressPath);

        if (mStatus == Status.END) {
            return;
        }
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!changed) {
            // 如果没有位置和大小没有变化,则无需变化View的初始化内容
            return;
        }
        mHeight = bottom - top;
        mWidth = right - left;
        mCenterPoint = new Point();
        mCenterPoint.y = mHeight / 2;
        mCenterPoint.x = mWidth / 2;
        mCircles = new AntivirusModuleCircle(new RectF(0, 0, mWidth, mHeight), mCenterPoint);
        mCircles.getCenterLines(mCenterLinePath);
        mCircles.getOutsideLines(mOutSideLinePath);

        mStartDrawLinesTime = System.currentTimeMillis() + 1000;
        mBeforeStartDrawLinesTime = System.currentTimeMillis() + 1000 - mBeforeInterval;
        mPoint4 = new Point[4];
        mPoint4[0] = new Point(mCenterPoint.x, mCenterPoint.y);
        mPoint4[1] = new Point((int) (mCenterPoint.x + mCircles.mR / 4), (int) (mCenterPoint.y + mCircles.mR / 4));
        mPoint4[2] = new Point(mCenterPoint.x, (int) (mCenterPoint.y - mCircles.mR / 4));
        mPoint4[3] = new Point(mCenterPoint.x, (int) (mCenterPoint.y + mCircles.mR / 4));

        mPhoneMatrix = new Matrix();
        mPhoneMatrix.postTranslate(mCenterPoint.x - mPhoneWidth / 2, mCenterPoint.y - mPhoneHeight / 2);
        mPointMatrix = new Matrix();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mXfermodeBitmap != null) {
            mXfermodeBitmap.recycle();
            mXfermodeBitmap = null;
        }
    }

    private long mBeforeInterval = 300;
    private long mBeforeStartDrawLinesTime;
    private int mDegree;
    private long mStartDrawLinesTime;

    private void drawLines(Canvas canvas) {
        long currentTime = System.currentTimeMillis() - mStartDrawLinesTime;
        if (currentTime < 0) {
            return;
        }

        int count = (int) (currentTime / (mRunTime + mSleepTime));
        count = count % 8;
        float other = currentTime % (mRunTime + mSleepTime);

        boolean sleep = other > mRunTime;
        float k = other / mRunTime;

        if (!sleep) {
            canvas.save();
            mDegree = (int) (count * 45 + 45 * k);
            canvas.translate(mCenterPoint.x, mCenterPoint.y);
            canvas.rotate(mDegree);
            canvas.translate(-mCenterPoint.x, -mCenterPoint.y);
            canvas.drawPath(mOutSideLinePath, mPaintOutLines);
            canvas.drawPath(mCenterLinePath, mPaintInLines);
            canvas.restore();
        } else {
            canvas.save();
            canvas.translate(mCenterPoint.x, mCenterPoint.y);
            canvas.rotate(mDegree);
            canvas.translate(-mCenterPoint.x, -mCenterPoint.y);
            canvas.drawPath(mOutSideLinePath, mPaintOutLines);
            canvas.drawPath(mCenterLinePath, mPaintInLines);
            canvas.restore();
        }

    }

    private void drawLinesBefore(Canvas canvas) {
        float currentTime = System.currentTimeMillis() - mBeforeStartDrawLinesTime;
        if (currentTime < 0 || currentTime > mBeforeInterval) {
            return;
        }

        float k = currentTime / mBeforeInterval;
        canvas.save();
        canvas.translate(mCenterPoint.x, mCenterPoint.y);
        canvas.scale(k, k, 0.5f, 0.5f);
        canvas.translate(-mCenterPoint.x, -mCenterPoint.y);
        canvas.drawPath(mOutSideLinePath, mPaintOutLines);
        canvas.drawPath(mCenterLinePath, mPaintInLines);
        canvas.restore();
    }

    private void drawPhoneAfter(Canvas canvas) {
        long currentTime = System.currentTimeMillis() - mStartDrawLinesTime;
        if (currentTime < 0) {
            canvas.drawBitmap(mPhoneLineBitmap, mPhoneMatrix, mPaintCircle);
            return;
        }

        int count = (int) (currentTime / (mRunTime + mSleepTime));
        //count 代表下一个要移动的位置
        count = (count + 1) % 4;
        float other = currentTime % (mRunTime + mSleepTime);

        boolean sleep = other > mRunTime;
        float k = other / mRunTime;
        int next = count;
        int last = count - 1;
        if (last < 0) {
            last = 3;
        }
        if (sleep) {
            mPointMatrix.reset();
            mPointMatrix.postTranslate(mPoint4[next].x - mPhoneWidth / 2, mPoint4[next].y - mPhoneHeight / 2);
            canvas.drawBitmap(mPhoneLineBitmap, mPointMatrix, mPaintCircle);
        } else {
            int dx = mPoint4[next].x - mPoint4[last].x;
            int dy = mPoint4[next].y - mPoint4[last].y;

            mPointMatrix.reset();
            mPointMatrix.postTranslate(mPoint4[last].x - mPhoneWidth / 2 + dx * k, mPoint4[last].y - mPhoneHeight / 2 + dy * k);
            canvas.drawBitmap(mPhoneLineBitmap, mPointMatrix, mPaintCircle);
        }

    }

    boolean mStartDrawCircle = false;

    private void drawPhoneLineAfter(Canvas canvas) {
        long currentTime = System.currentTimeMillis() - mStartDrawLinesTime;
        if (currentTime < 0) {
            canvas.drawBitmap(mPhoneBitmap, mPhoneMatrix, mPaintCircle);
            return;
        }

        mStartDrawCircle = true;

        int count = (int) (currentTime / (mRunTime + mSleepTime));
        //count 代表下一个要移动的位置
        count = (count + 1) % 4;
        float other = currentTime % (mRunTime + mSleepTime);

        boolean sleep = other > mRunTime;
        float k = other / mRunTime;
        int next = count;
        int last = count - 1;
        if (last < 0) {
            last = 3;
        }
        if (sleep) {
            mPointMatrix.reset();
            mPointMatrix.postTranslate(mPoint4[next].x - mPhoneWidth / 2, mPoint4[next].y - mPhoneHeight / 2);
            canvas.drawBitmap(mPhoneBitmap, mPointMatrix, mPaintCircle);
        } else {
            int dx = mPoint4[next].x - mPoint4[last].x;
            int dy = mPoint4[next].y - mPoint4[last].y;

            mPointMatrix.reset();
            mPointMatrix.postTranslate(mPoint4[last].x - mPhoneWidth / 2 + dx * k, mPoint4[last].y - mPhoneHeight / 2 + dy * k);
            canvas.drawBitmap(mPhoneBitmap, mPointMatrix, mPaintCircle);
        }

    }

    private Status mStatus = Status.START;

    public void setStatus(Status status) {
        mStatus = status;
    }

    public enum Status {
        START,
        END
    }

    //动画执行的最短时间 4秒
    public static final long MIN_ANIMATION_DURATION = 4000;


    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    //设置进度
    public void setCurrentProgress(float mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
    }

    //初始时间改下即可
    public void startProgressAnimation() {
        startAnimator = ValueAnimator.ofFloat(0.0f, 75.0f);
        startAnimator.setDuration(MIN_ANIMATION_DURATION * 5);
        startAnimator.setStartDelay(1000);
        startAnimator.setInterpolator(new LinearInterpolator());
        startAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (Float) valueAnimator.getAnimatedValue();
                setCurrentProgress(progress);
            }
        });
        startAnimator.start();
    }

    public void endProgressAnimation(Animator.AnimatorListener listener) {
        //如果start动画还在运行的话,就cancel掉
        if (startAnimator != null && startAnimator.isRunning()) {
            startAnimator.cancel();
        }
        endAnimator = ValueAnimator.ofFloat(getCurrentProgress(), 100.0f);
        endAnimator.setDuration(2000);
        endAnimator.setInterpolator(new AccelerateInterpolator());
        endAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (Float) valueAnimator.getAnimatedValue();
                setCurrentProgress(progress);
            }
        });
        endAnimator.addListener(listener);
        endAnimator.start();
    }
}
