package com.atguigu.www.beijingnews.base.basepager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.www.beijingnews.base.BasePager;

/**
 * Created by lenovo on 2017/2/5.
 * 主页面
 */

public class HomePager extends BasePager {
    public HomePager(Context mContext) {
        super(mContext);
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG","主页面加载数据了");

        //设置标题
        tv_title.setText("主页");

        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("主页面");
        textView.setTextColor(Color.RED);

        //和父类fragment结合
        fl_main.addView(textView);
    }
}
