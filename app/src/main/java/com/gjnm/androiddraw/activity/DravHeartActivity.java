package com.gjnm.androiddraw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.gjnm.androiddraw.R;
import com.gjnm.androiddraw.baseclass.BaseActivity;
import com.gjnm.androiddraw.view.AnimaHeartView;

import java.util.ArrayList;

/**
 * Created by gaojian12 on 17/8/17.
 */

public class DravHeartActivity extends BaseActivity {

    private AnimaHeartView heartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        init();
    }

    private void init() {
        heartView = (AnimaHeartView) findViewById(R.id.heart);
        heartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartView.startPathAnim(1000);
            }
        });

        ArrayList<String> list = new ArrayList();
//        for (int i=0; ;i++){
//            String aa = "sdfsdgdsdsgdsfgsdg,dsgsdgsdgsg,sgsdgsgsg";
//            list.add(aa);
//            System.out.println("i = " + i);
//        }

    }
}
