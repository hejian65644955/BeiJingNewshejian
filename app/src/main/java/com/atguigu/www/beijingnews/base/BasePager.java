package com.atguigu.www.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.activity.MainActivity;

/**
 * Created by lenovo on 2017/2/5.
 * 作用：视图的基类
 * HomePager,NewsCenterPager,settingPager都继承该类
 */

public class BasePager {

    //上下文
    public final Context mContext;
    public ImageButton ib_menu;
    public TextView tv_title;
    public FrameLayout fl_main;

    public View rootView;

    public BasePager(Context mContext){
        this.mContext=mContext;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.basepager, null);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        fl_main = (FrameLayout) view.findViewById(R.id.fl_main);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();//关<->开
            }
        });

        return view;
    }

    /**
     * 1.在子类重新initData方法，实现子类的视图，并且视图在该方法中和基类的Fragmelayout布局结合在一起
     2.绑定数据或者请求数据再绑定数据
     */
    public void  initData(){

    }

}
