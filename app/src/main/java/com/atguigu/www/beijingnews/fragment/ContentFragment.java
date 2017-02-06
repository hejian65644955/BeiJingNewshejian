package com.atguigu.www.beijingnews.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.activity.MainActivity;
import com.atguigu.www.beijingnews.base.BaseFragment;
import com.atguigu.www.beijingnews.base.BasePager;
import com.atguigu.www.beijingnews.base.basepager.HomePager;
import com.atguigu.www.beijingnews.base.basepager.NewsCenterPager;
import com.atguigu.www.beijingnews.base.basepager.SettingPager;
import com.atguigu.www.beijingnews.view.NoScrollViewPager;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2017/2/5.
 * 作用：主页
 */

public class ContentFragment extends BaseFragment {

    @InjectView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_content, null);
        //把view注入到ButterKnife
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //初始化三个界面
        initPager();

        //设置适配器
        setAdapter();

        //设置监听
        initsetListener();


    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
           // BasePager basePager = basePagers.get(position);
            //调用initData()
            basePagers.get(position).initData();//孩子视图与父类视图结合

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initsetListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //先默认设置不可以滑动
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                switch (checkedId){
                    case R.id.rb_home:
                        viewpager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_news:
                        viewpager.setCurrentItem(1,false);
                        mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_setting:
                        viewpager.setCurrentItem(2,false);
                        break;
                }
            }
        });
        rgMain.check(R.id.rb_news);
        //监听页面的选中
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        basePagers.get(1).initData();
    }

    private void setAdapter() {
        viewpager.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);
            View rootView =basePager.rootView;
            //调用initData
            //basePager.initData();
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void initPager() {
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(mContext));//主页
        basePagers.add(new NewsCenterPager(mContext));//新闻中心
        basePagers.add(new SettingPager(mContext));//设置
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
