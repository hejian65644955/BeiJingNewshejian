package com.atguigu.www.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lenovo on 2017/2/6.
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //屏蔽滑动功能

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
