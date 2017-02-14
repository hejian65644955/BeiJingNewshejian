package com.atguigu.screenadapter_code;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.tv1)
    TextView tv1;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.tv3)
    TextView tv3;
    @InjectView(R.id.tv4)
    TextView tv4;

    private int screenWidth;
    private int screenHeight;
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //获取屏幕的高和宽
        screenHeight =metrics.heightPixels;
        screenWidth =metrics.widthPixels;

        params = new LinearLayout.LayoutParams((int)(screenWidth*0.25),(int)(screenHeight*0.1));
        tv1.setLayoutParams(params);
        params = new LinearLayout.LayoutParams((int)(screenWidth*0.5),(int)(screenHeight*0.1));
        tv2.setLayoutParams(params);
        params = new LinearLayout.LayoutParams((int)(screenWidth*0.725),(int)(screenHeight*0.1));
        tv3.setLayoutParams(params);
        params = new LinearLayout.LayoutParams((int)(screenWidth),(int)(screenHeight*0.1));
        tv4.setLayoutParams(params);

    }
}
