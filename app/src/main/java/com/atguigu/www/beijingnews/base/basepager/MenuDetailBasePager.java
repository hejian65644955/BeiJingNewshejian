package com.atguigu.www.beijingnews.base.basepager;

import android.content.Context;
import android.view.View;

/**
 * Created by lenovo on 2017/2/5.
 * 作用：视图的基类
 * NewsMenuDetailPager、TopicMenuDetailPager、PhotosMenuDetailPager、InteracMenuDetailPager
 * 都继承该类
 */

public abstract class MenuDetailBasePager {

    //上下文
    public final Context mContext;

    public View rootView;

    public MenuDetailBasePager(Context mContext){
        this.mContext=mContext;
        rootView = initView();
    }

    public  abstract  View initView();


    /**
     1.绑定数据或者请求数据再绑定数据
     */
    public void  initData(){

    }

}
