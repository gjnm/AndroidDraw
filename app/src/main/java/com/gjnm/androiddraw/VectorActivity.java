package com.gjnm.androiddraw;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by gaojian12 on 17/8/27.
 */

public class VectorActivity extends AppCompatActivity {
    public ImageView arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        init();
    }

    private void init() {
        arrow = (ImageView) findViewById(R.id.arrow);
        Drawable drawable = arrow.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
    }
}
