package com.atguigu.www.beijingnews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.fragment.ContentFragment;
import com.atguigu.www.beijingnews.fragment.LeftMenuFragment;
import com.atguigu.www.beijingnews.utils.DensityUtil;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    public static final String CONENT_TAG = "conent_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置主页面
        setContentView(R.layout.activity_main);
        //2.设置侧滑页面
        setBehindContentView(R.layout.leftmenu);
        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        //4.设置模式：左侧+主页；左侧+主页+右侧；主页+右侧
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置滑动模式，全屏，边缘，不可滑动
        slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_FULLSCREEN);
        //6.设置主页面占的宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(this,200));
        initFragment();
    }

    //初始化Fragment
    private void initFragment() {

        //1.得到事物
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.f1_leftmenu,new LeftMenuFragment(), LEFTMENU_TAG);
        ft.replace(R.id.f1_content,new ContentFragment(), CONENT_TAG);
        ft.commit();

    }

    public LeftMenuFragment getleftMenuFragment() {

        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }
}
