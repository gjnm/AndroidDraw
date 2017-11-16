package com.gjnm.androiddraw.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.gjnm.androiddraw.R;
import com.gjnm.androiddraw.baseclass.BaseActivity;

/**
 * Created by gaojian12 on 17/8/27.
 */

public class VectorActivity extends BaseActivity {
    public ImageView arrow;
    public ImageView searchBar;
    public ImageView path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        init();
    }

    private void init() {
        arrow = (ImageView) findViewById(R.id.arrow);
        searchBar = (ImageView) findViewById(R.id.search_bar);
        path = (ImageView) findViewById(R.id.path);
        if(arrow.getDrawable() instanceof Animatable){
            ((Animatable)arrow.getDrawable()).start();
        }

        if(searchBar.getDrawable() instanceof Animatable){
            ((Animatable)searchBar.getDrawable()).start();
        }

        if(path.getDrawable() instanceof Animatable){
            ((Animatable)path.getDrawable()).start();
        }
    }
}
