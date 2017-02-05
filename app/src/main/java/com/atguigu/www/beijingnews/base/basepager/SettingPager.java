package com.atguigu.www.beijingnews.base.basepager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.www.beijingnews.base.BasePager;

/**
 * Created by lenovo on 2017/2/5.
 * 设置页面
 */

public class SettingPager extends BasePager {
    public SettingPager(Context mContext) {
        super(mContext);
    }

    @Override
    public void initData() {
        super.initData();

        //设置标题
        tv_title.setText("设置页面");
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("设置页面");
        textView.setTextColor(Color.RED);

        //和父类fragment结合
        fl_main.addView(textView);
    }
}
