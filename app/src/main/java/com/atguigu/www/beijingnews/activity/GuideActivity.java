package com.atguigu.www.beijingnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.utils.CacheUtils;
import com.atguigu.www.beijingnews.utils.DensityUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.btn_start_main)
    Button btnStartMain;
    @InjectView(R.id.ll_group_point)
    LinearLayout llGroupPoint;
    @InjectView(R.id.iv_red_point)
    ImageView ivRedPoint;

    private int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

    /**
     * 间距 = 第1个点距离左边距离- 第0个点距离左边的距离
     * <p>
     * 红点移动的原理
     * 红点移动距离 ： 间距 = 手滑动的距离：屏幕宽 = 屏幕滑动的百分比
     * 红点移动距离 = 间距 * 屏幕滑动的百分比
     * <p>
     * 红点移动的坐标 = 起始坐标 + 红点移动距离
     */
    private int leftMagin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        //设置适配器
        viewpager.setAdapter(new MyPagerAdapter());

        //监听页面改变
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //根据多少页面添加多少点
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(DensityUtil.dip2px(this,10), DensityUtil.dip2px(this,10));
            if (i != 0) {
                parms.leftMargin = DensityUtil.dip2px(this,10);
            }
            imageView.setLayoutParams(parms);
            imageView.setImageResource(R.drawable.point_normal);

            //添加到线性布局
            llGroupPoint.addView(imageView);
        }

        //view的生命周期 测量--布局--绘制
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //间距 = 第1个点距离左边距离- 第0个点距离左边的距离
            leftMagin = llGroupPoint.getChildAt(1).getLeft() - llGroupPoint.getChildAt(0).getLeft();

        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 红点移动距离 ： 间距 = 手滑动的距离：屏幕宽 = 屏幕滑动的百分比
            // 红点移动距离 = 间距 * 屏幕滑动的百分比
            //int maginLeft = (int) (leftMagin * positionOffset);
            //红点移动的坐标 = 起始坐标 + 红点移动距离
            //maginLeft = position * leftMagin + (int) (leftMagin * positionOffset);
            int maginLeft = (int) (leftMagin*(position   +  positionOffset));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            params.leftMargin = maginLeft;
            ivRedPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if (position == ids.length - 1) {
                //显示按钮
                btnStartMain.setVisibility(View.VISIBLE);
            } else {
                btnStartMain.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @OnClick(R.id.btn_start_main)
    public void onClick() {
        //1.保存参数，记录已经进入的过引导页面，下次不进来
        CacheUtils.putBoolean(this,"start_main",true);
        //Toast.makeText(this, "进入主页面", Toast.LENGTH_SHORT).show();

        //2.进入主页面
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(ids[position]);
            //添加到ViewPager
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
