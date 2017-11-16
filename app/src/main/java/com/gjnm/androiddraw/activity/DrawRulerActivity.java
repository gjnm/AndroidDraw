package com.gjnm.androiddraw.activity;

import android.app.Activity;
import android.os.Bundle;

import com.gjnm.androiddraw.R;
import com.gjnm.androiddraw.baseclass.BaseActivity;


/**
 * Created by gaojian12 on 17/8/15.
 */

public class DrawRulerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
        init();
    }

    private void init() {

    }

}
