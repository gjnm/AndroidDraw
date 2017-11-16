package com.gjnm.androiddraw.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.gjnm.androiddraw.R;
import com.gjnm.androiddraw.baseclass.BaseActivity;

public class ChangeArgbActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {
    public static String TAG = "AppCompatActivity";

    public ImageView icon;
    public SeekBar red;
    public SeekBar green;
    public SeekBar blue;
    private Bitmap afterBitmap;
    private Paint paint;
    private Canvas canvas;
    private Bitmap baseBitmap;

    private float redSeek, greenSeek, blueSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_change_argb);
        init();
        initPaint();
    }

    private void init() {
        icon = (ImageView) findViewById(R.id.image);
        red = (SeekBar) findViewById(R.id.red);
        green = (SeekBar) findViewById(R.id.gree);
        blue = (SeekBar) findViewById(R.id.blue);

        red.setOnSeekBarChangeListener(this);
        green.setOnSeekBarChangeListener(this);
        blue.setOnSeekBarChangeListener(this);
    }


    private void initPaint() {
        baseBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.floatwindow_trigger_bg);
        // 1.获取一个与baseBitmap大小一致的可编辑的空图片
        afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(),
                baseBitmap.getHeight(), baseBitmap.getConfig());
        // 2.使用Bitmap对象创建画布Canvas, 然后创建画笔Paint。
        canvas = new Canvas(afterBitmap);
        paint = new Paint();
        setArgb(1, 1, 1, 1);
    }


    public void setArgb(float alpha, float red, float green, float blue) {
        ColorMatrix mColorMatrix = new ColorMatrix(new float[]{
                red, 0, 0, 0, 0,
                0, green, 0, 0, 0,
                0, 0, blue, 0, 0,
                0, 0, 0, alpha, 0,
        });

        paint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        canvas.drawBitmap(baseBitmap, new Matrix(), paint);
        icon.setImageBitmap(afterBitmap);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float filter = (float) progress / 50;

        if (seekBar == red) {
            redSeek = filter;
        } else if (seekBar == green) {
            greenSeek = filter;
        } else if (seekBar == blue) {
            blueSeek = filter;
        }

        setArgb(1, redSeek, greenSeek, blueSeek);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
