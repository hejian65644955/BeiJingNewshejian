package com.atguigu.www.beijingnews.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.www.beijingnews.base.BaseFragment;

/**
 * Created by lenovo on 2017/2/5.
 * 作用：主页
 */

public class ContentFragment extends BaseFragment {

    private TextView textView;
    @Override
    public View initView() {
        textView =new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("主页--Fragment");
    }
}
