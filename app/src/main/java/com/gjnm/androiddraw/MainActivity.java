package com.gjnm.androiddraw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private static String TAG = "AppCompatActivity";
    private Button but_1;
    private Button but_2;
    private Button but_3;
    private Button but_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        but_1 = (Button) findViewById(R.id.but01);
        but_2 = (Button) findViewById(R.id.but02);
        but_3 = (Button) findViewById(R.id.but03);
        but_4 = (Button) findViewById(R.id.but04);

        but_1.setOnClickListener(this);
        but_2.setOnClickListener(this);
        but_3.setOnClickListener(this);
        but_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.but01:
                intent = new Intent(MainActivity.this, ChangeArgbActivity.class);
                startActivity(intent);
                break;
            case R.id.but02:
                intent = new Intent(MainActivity.this, DrawRulerActivity.class);
                startActivity(intent);
                break;
            case R.id.but03:
                intent = new Intent(MainActivity.this, DravHeartActivity.class);
                startActivity(intent);
                break;
            case R.id.but04:
                intent = new Intent(MainActivity.this, VectorActivity.class);
                startActivity(intent);
                break;
        }
    }


}
