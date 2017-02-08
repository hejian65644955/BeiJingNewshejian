package com.atguigu.www.beijingnews.details;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.adapter.TabDetailPagerAdapter;
import com.atguigu.www.beijingnews.base.basepager.MenuDetailBasePager;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;
import com.atguigu.www.beijingnews.bean.TabDetailPagerBean;
import com.atguigu.www.beijingnews.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2017/2/6.
 */

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    private ListView listview;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ll_group_point)
    LinearLayout llGroupPoint;
    private String url;
    private TabDetailPagerAdapter adapter;
    private List<TabDetailPagerBean.DataEntity.NewsEntity> news;
    private List<TabDetailPagerBean.DataEntity.TopnewsEntity> topnews;

    public TabDetailPager(Context mContext, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(mContext);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        //图组详细页面的视图
        View view = View.inflate(mContext, R.layout.table_detail_pager, null);
        listview = (ListView) view.findViewById(R.id.listview);//listview被实例化了
        View headView = View.inflate(mContext, R.layout.header_view, null);
        ButterKnife.inject(this,headView);
        listview.addHeaderView(headView);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenBean.getUrl();
        Log.e("TAG", "url==TabDetailPager------------------" + url);
        //设置数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求数据成功+==TabDetailPager" + result);
                processData(result);

            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求数据失败+==TabDetailPager" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        TabDetailPagerBean pagerBean = new Gson().fromJson(json, TabDetailPagerBean.class);
        Log.e("TAG", "数据解析成功==TabDetailPager==" + pagerBean.getData().getNews().get(0).getTitle());
        news = pagerBean.getData().getNews();

        //设置适配器
        adapter = new TabDetailPagerAdapter(mContext, news);
        listview.setAdapter(adapter);

        //设置顶部新闻（轮播图）
        topnews = pagerBean.getData().getTopnews();
        viewpager.setAdapter(new MyPagerAdapter());
        //监听vipager页面变化
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        tvTitle.setText(topnews.get(0).getTitle());

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tvTitle.setText(topnews.get(position).getTitle());

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置默认和联网请求
            Glide.with(mContext).load(Constants.BASE_URL+topnews.get(position).getTopimage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //设置默认图片
                    .placeholder(R.drawable.news_pic_default)
                    .error(R.drawable.news_pic_default)
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }
    }



}
