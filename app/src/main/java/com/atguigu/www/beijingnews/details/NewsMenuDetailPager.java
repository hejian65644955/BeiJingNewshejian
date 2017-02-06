package com.atguigu.www.beijingnews.details;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.www.beijingnews.base.basepager.MenuDetailBasePager;

/**
 * Created by lenovo on 2017/2/6.
 */

public class NewsMenuDetailPager extends MenuDetailBasePager {
    public NewsMenuDetailPager(Context mContext) {
        super(mContext);
    }

    private TextView textView;

    @Override
    public View initView() {
        //新闻详细页面的视图
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("新闻详情页面内容");
    }
}
