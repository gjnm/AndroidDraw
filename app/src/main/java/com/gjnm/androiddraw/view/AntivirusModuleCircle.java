package com.gjnm.androiddraw.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.gjnm.androiddraw.application.MyApplication;
import com.gjnm.androiddraw.untils.UiUtils;

import java.util.ArrayList;

/**
 * Created by wudanfeng on 15/10/27.
 */
public class AntivirusModuleCircle {
    private RectF mRect;
    private int mStartAngle;
    private Point mCenterPoint;
    ArrayList<Point> mPoints;
    /**
     * 转一圈的时间
     **/

    private float mCircleSeparationTime = 1000;
    private float mOneCircleTime = 500;
    private long mStartTime;
    private RectF mBLRect;
    public  static RectF mRoundRect;
    public static int mBLMargin = UiUtils.dipToPx(33);
    public static int mPointsMargin = UiUtils.dipToPx(26);
    public static int mRoundMargin = UiUtils.dipToPx(9);
    /***罗盘外圈西线间隙*/
    public int mOutSideLineMargin = UiUtils.dipToPx(13);
    public float mR;
    int mWidth;
    int mHeight;
    private float mLittleCircleR;
    public AntivirusModuleCircle(RectF rect, Point centerPoint) {
        mCenterPoint = centerPoint;
        mRect = rect;
        mBLRect = new RectF();
        mBLRect.left = mBLMargin;
        mBLRect.right = mRect.right - mBLMargin;
        mR = (mBLRect.right - mBLRect.left) / 2;
        mBLRect.top = mCenterPoint.y - mR;
        mBLRect.bottom = mCenterPoint.y +mR;

        mRoundRect = new RectF();
        mRoundRect.left = mCenterPoint.x - mR + mRoundMargin;
        mRoundRect.top = mCenterPoint.y - mR + mRoundMargin;
        mRoundRect.bottom = mCenterPoint.y + mR - mRoundMargin;
        mRoundRect.right = mCenterPoint.x + mR - mRoundMargin;
        mStartTime = System.currentTimeMillis();
        mLittleCircleR= UiUtils.dip2px(MyApplication.getInstance(),30);
    }

    /**
     * 获得旋转的圆圈
     **/
    public Path getCirclePath(Path path) {
        long timeEsplise = System.currentTimeMillis() - mStartTime - (int)mCircleSeparationTime;
        if(timeEsplise < 0){
            return path;
        }
        float mod = timeEsplise % mOneCircleTime;
        float percent = mod / mOneCircleTime;
        mStartAngle = (int) (360 * percent);
        //addArc mRoundRect 为椭圆的大小，mStartAngle开始角度，80一共跨越多少度
        path.addArc(mRoundRect, mStartAngle, 80);
        path.addArc(mRoundRect, mStartAngle + 180, 80);
        return path;
    }

    /**
     * 获得旋转圆圈上所有的点
     **/
    public ArrayList<Point> getPointCircle() {
        if (mPoints != null) {
            return mPoints;
        }
        mPoints = new ArrayList<Point>();
        // 弧度对应角度的计算公式来自，http://www.cnblogs.com/xieon1986/archive/2013/01/28/2880367.html
        float r = (mRect.right - mRect.left) / 2 - mPointsMargin;
        for (int i = 0; i < 24; i++) {
            //计算角度对应的弧度
            double hudu = (2 * Math.PI / 360) * 15 * i;
            Point point = new Point();
            //通过弧度算对应的坐标
            point.x = (int) (mCenterPoint.x + r * Math.sin(hudu));
            point.y = (int) (mCenterPoint.y - r * Math.cos(hudu));
            mPoints.add(point);
        }

        return mPoints;
    }

    private float mStartDrawLinesTimeK = 0.45f;
    public int getLittleCirclePath(Path path, long currentTime) {
        long time = currentTime - mStartTime;
        float separation = mCircleSeparationTime;
        float k = time / separation;
        float r = (mBLRect.right - mBLRect.left) / 2;
        if (k < mStartDrawLinesTimeK) {
            r = k * r;
        } else if(k > mStartDrawLinesTimeK && k <1){
            float rR= r *mStartDrawLinesTimeK;
            float dr= (rR - mLittleCircleR)*(k - mStartDrawLinesTimeK)/(1f-mStartDrawLinesTimeK);
            r = rR - dr;
        }else {
            r = mLittleCircleR;
        }
        path.addCircle(mCenterPoint.x, mCenterPoint.y, r, Path.Direction.CCW);
        return (int)r;
    }

    public Path getBigCirclePath(Path path, long currentTime) {
        long time = currentTime - mStartTime;
        float separation = mCircleSeparationTime;
        float k = time / separation;
        float r = (mBLRect.right - mBLRect.left) / 2;
        if (k < 1f) {
            r = k * r;
        }
        path.addCircle(mCenterPoint.x, mCenterPoint.y, r, Path.Direction.CCW);
        return path;
    }

    public void getProgressCircle(float Progress, Canvas canvas, Paint paint, Path path) {
        float time = System.currentTimeMillis() - mStartTime - mCircleSeparationTime;
        if (time > 0) {
            path.addArc(mBLRect, -90, Progress * 360 / 100f);
            canvas.drawPath(path,paint);
        }
    }


    /**
     * 不可以再 ondraw 中调用
     **/
    public Path getCenterLines(Path path) {
        float r = (mBLRect.right - mBLRect.left) / 2;
        double rightTopHudu = (2 * Math.PI / 360) * 45;
        Point rightTopPoint = new Point();
        rightTopPoint.x = (int) (mCenterPoint.x + r * Math.sin(rightTopHudu));
        rightTopPoint.y = (int) (mCenterPoint.y - r * Math.cos(rightTopHudu));


        double rightBottomHudu = (2 * Math.PI / 360) * 135;
        Point rightBottomPoint = new Point();
        rightBottomPoint.x = (int) (mCenterPoint.x + r * Math.sin(rightBottomHudu));
        rightBottomPoint.y = (int) (mCenterPoint.y - r * Math.cos(rightBottomHudu));

        double leftBottomHudu = (2 * Math.PI / 360) * 225;
        Point leftBottomPoint = new Point();
        leftBottomPoint.x = (int) (mCenterPoint.x + r * Math.sin(leftBottomHudu));
        leftBottomPoint.y = (int) (mCenterPoint.y - r * Math.cos(leftBottomHudu));

        double leftTopHudu = (2 * Math.PI / 360) * 315;
        Point leftTopPoint = new Point();
        leftTopPoint.x = (int) (mCenterPoint.x + r * Math.sin(leftTopHudu));
        leftTopPoint.y = (int) (mCenterPoint.y - r * Math.cos(leftTopHudu));

        path.moveTo(leftTopPoint.x, leftTopPoint.y);
        path.lineTo(rightBottomPoint.x, rightBottomPoint.y);
        path.moveTo(leftBottomPoint.x, leftBottomPoint.y);
        path.lineTo(rightTopPoint.x, rightTopPoint.y);

        return path;
    }

    /**
     * 不可以再 ondraw 中调用
     **/
    public Path getOutsideLines(Path path) {
        float rO = (mRect.right - mRect.left) / 2;
        double rightTopHuduO = (2 * Math.PI / 360) * 45;
        Point rightTopPointO = new Point();
        rightTopPointO.x = (int) (mCenterPoint.x + rO * Math.sin(rightTopHuduO));
        rightTopPointO.y = (int) (mCenterPoint.y - rO * Math.cos(rightTopHuduO));


        double rightBottomHuduO = (2 * Math.PI / 360) * 135;
        Point rightBottomPointO = new Point();
        rightBottomPointO.x = (int) (mCenterPoint.x + rO * Math.sin(rightBottomHuduO));
        rightBottomPointO.y = (int) (mCenterPoint.y - rO * Math.cos(rightBottomHuduO));

        double leftBottomHuduO = (2 * Math.PI / 360) * 225;
        Point leftBottomPointO = new Point();
        leftBottomPointO.x = (int) (mCenterPoint.x + rO * Math.sin(leftBottomHuduO));
        leftBottomPointO.y = (int) (mCenterPoint.y - rO * Math.cos(leftBottomHuduO));

        double leftTopHuduO = (2 * Math.PI / 360) * 315;
        Point leftTopPointO = new Point();
        leftTopPointO.x = (int) (mCenterPoint.x + rO * Math.sin(leftTopHuduO));
        leftTopPointO.y = (int) (mCenterPoint.y - rO * Math.cos(leftTopHuduO));


        float rI = (mBLRect.right - mBLRect.left) / 2 + mOutSideLineMargin;
        double rightTopHuduI = (2 * Math.PI / 360) * 45;
        Point rightTopPointI = new Point();
        rightTopPointI.x = (int) (mCenterPoint.x + rI * Math.sin(rightTopHuduI));
        rightTopPointI.y = (int) (mCenterPoint.y - rI * Math.cos(rightTopHuduI));


        double rightBottomHuduI = (2 * Math.PI / 360) * 135;
        Point rightBottomPointI = new Point();
        rightBottomPointI.x = (int) (mCenterPoint.x + rI * Math.sin(rightBottomHuduI));
        rightBottomPointI.y = (int) (mCenterPoint.y - rI * Math.cos(rightBottomHuduI));

        double leftBottomHuduI = (2 * Math.PI / 360) * 225;
        Point leftBottomPointI = new Point();
        leftBottomPointI.x = (int) (mCenterPoint.x + rI * Math.sin(leftBottomHuduI));
        leftBottomPointI.y = (int) (mCenterPoint.y - rI * Math.cos(leftBottomHuduI));

        double leftTopHudu = (2 * Math.PI / 360) * 315;
        Point leftTopPointI = new Point();
        leftTopPointI.x = (int) (mCenterPoint.x + rI * Math.sin(leftTopHudu));
        leftTopPointI.y = (int) (mCenterPoint.y - rI * Math.cos(leftTopHudu));

        path.moveTo(rightBottomPointO.x, rightBottomPointO.y);
        path.lineTo(rightBottomPointI.x, rightBottomPointI.y);

        path.moveTo(rightTopPointO.x, rightTopPointO.y);
        path.lineTo(rightTopPointI.x, rightTopPointI.y);

        path.moveTo(leftTopPointO.x, leftTopPointO.y);
        path.lineTo(leftTopPointI.x, leftTopPointI.y);

        path.moveTo(leftBottomPointO.x, leftBottomPointO.y);
        path.lineTo(leftBottomPointI.x, leftBottomPointI.y);
        return path;
    }

    public Path getLightCirclePath(Path path){
        path.addCircle(mCenterPoint.x,mCenterPoint.y, mR*3/4, Path.Direction.CCW);
        //path.addCircle(mCenterPoint.x,mCenterPoint.y,mR,Path.Direction.CCW);
        return  path;
    }

    public void drawBigCircleAndPoints(Canvas canvas , Paint circle , Paint point){
        float r = (mBLRect.right - mBLRect.left) / 2;
        canvas.drawCircle(mCenterPoint.x,mCenterPoint.y,r,circle);
        float rP = (mRect.right - mRect.left) / 2 - mPointsMargin;
        for (int i = 0; i < 24; i++) {
            //计算角度对应的弧度
            double hudu = (2 * Math.PI / 360) * 15 * i;
            //通过弧度算对应的坐标
            int x = (int) (mCenterPoint.x + rP * Math.sin(hudu));
            int y = (int) (mCenterPoint.y - rP * Math.cos(hudu));
            canvas.drawPoint(x,y,point);
        }
    }
}
