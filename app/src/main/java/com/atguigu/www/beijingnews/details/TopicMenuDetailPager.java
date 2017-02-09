package com.atguigu.www.beijingnews.details;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.activity.MainActivity;
import com.atguigu.www.beijingnews.base.basepager.MenuDetailBasePager;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/2/6.
 */

public class TopicMenuDetailPager extends MenuDetailBasePager {

    /**
     * 新闻详情页面的数据
     */
    private final List<NewsCenterBean.DataBean.ChildrenBean> childrenData;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    /**
     * 页签页面的集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    @InjectView(R.id.ib_next)
    ImageButton ibNext;

    public TopicMenuDetailPager(Context mContext, NewsCenterBean.DataBean dataBean) {
        super(mContext);
        this.childrenData = dataBean.getChildren();
    }


    @Override
    public View initView() {
        //新闻详细页面的视图
        View view = View.inflate(mContext, R.layout.topic_menu_detail_pager, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //准备数据-页面
        tabDetailPagers = new ArrayList<>();
        for (int i = 0; i < childrenData.size(); i++) {
            tabDetailPagers.add(new TabDetailPager(mContext, childrenData.get(i)));
        }

        //设置适配器
        viewpager.setAdapter(new MyPagerAdapter());

        tabLayout.setupWithViewPager(viewpager);
        //设置滑动的模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainActivity mainActivity = (MainActivity) mContext;
            if (position == 0) {
                //北京可以滑动
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            } else {
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @OnClick(R.id.ib_next)
    public void onClick() {
        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return childrenData.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            tabDetailPager.initData();
            View rootView = tabDetailPager.rootView;
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
