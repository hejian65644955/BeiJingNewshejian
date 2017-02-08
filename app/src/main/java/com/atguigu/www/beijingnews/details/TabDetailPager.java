package com.atguigu.www.beijingnews.details;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.activity.NewsDetailActivity;
import com.atguigu.www.beijingnews.adapter.TabDetailPagerAdapter;
import com.atguigu.www.beijingnews.base.basepager.MenuDetailBasePager;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;
import com.atguigu.www.beijingnews.bean.TabDetailPagerBean;
import com.atguigu.www.beijingnews.utils.CacheUtils;
import com.atguigu.www.beijingnews.utils.Constants;
import com.atguigu.www.beijingnews.utils.DensityUtil;
import com.atguigu.www.beijingnews.view.HorizontalScrollViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

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

    public static final String ID_ARRAY = "id_array";
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    private ListView listview;
    private PullToRefreshListView refreshListView;
    @InjectView(R.id.viewpager)
    HorizontalScrollViewPager viewpager;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ll_group_point)
    LinearLayout llGroupPoint;
    private String url;
    private TabDetailPagerAdapter adapter;
    private List<TabDetailPagerBean.DataEntity.NewsEntity> news;
    private List<TabDetailPagerBean.DataEntity.TopnewsEntity> topnews;
    private int prePosition ;
    private String moreUrl;
    private boolean isMore =false;

    public TabDetailPager(Context mContext, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(mContext);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        //图组详细页面的视图
        View view = View.inflate(mContext, R.layout.table_detail_pager, null);
        refreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);//refreshListView被实例化了
        listview =refreshListView.getRefreshableView();


        View headView = View.inflate(mContext, R.layout.header_view, null);
        ButterKnife.inject(this,headView);
        listview.addHeaderView(headView);

        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(mContext);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        refreshListView.setOnPullEventListener(soundListener);

        //设置下拉和上拉刷新
        refreshListView.setOnRefreshListener(new MyOnRefreshListener2());

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到bean对象
                TabDetailPagerBean.DataEntity.NewsEntity newsEntity = news.get(position - 2);
                String title = newsEntity.getTitle();
                int id1 = newsEntity.getId();

                //获取是否存在，如果不存在才保存
                String idArray = CacheUtils.getString(mContext, ID_ARRAY);
                //如果不包含才保存
                if(!idArray.contains(id1+"")){
                    //保存点击过的item的对应的id
                    CacheUtils.putString(mContext,ID_ARRAY,idArray+id1+",");


                    adapter.notifyDataSetChanged();//getCount-->getView
                    //2.刷新适配器
                }
                //跳转到新闻的浏览页面
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("url",Constants.BASE_URL+newsEntity.getUrl());
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    class MyOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2<ListView> {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            //Toast.makeText(mContext, "下拉刷新", Toast.LENGTH_SHORT).show();
            isMore =false;
            getDataFromNet();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            //Toast.makeText(mContext, "上拉刷新", Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(moreUrl)){
                isMore =true;
                getMoreDataFromNet();
            }else {
                Toast.makeText(mContext, "没有加载更多", Toast.LENGTH_SHORT).show();
                refreshListView.onRefreshComplete();
            }


        }

        private void getMoreDataFromNet() {
            RequestParams params = new RequestParams(moreUrl);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("TAG", "请求数据更多成功+==TabDetailPager" + result);
                    processData(result);

                    //把下拉刷新和上拉刷新隐藏
                    refreshListView.onRefreshComplete();

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

                //把下拉刷新和上拉刷新隐藏
                refreshListView.onRefreshComplete();

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
        //Log.e("TAG", "数据解析成功==TabDetailPager==" + pagerBean.getData().getNews().get(0).getTitle());
        String more = pagerBean.getData().getMore();
        if(TextUtils.isEmpty(more)){
            moreUrl = "";
        }else{
            moreUrl =Constants.BASE_URL+more;
        }

        if(!isMore){
            //原来代码
            news = pagerBean.getData().getNews();

            //设置适配器
            adapter = new TabDetailPagerAdapter(mContext, news);
            listview.setAdapter(adapter);

            //设置顶部新闻（轮播图）
            topnews = pagerBean.getData().getTopnews();
            viewpager.setAdapter(new MyPagerAdapter());
            //监听vipager页面变化
            viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
            tvTitle.setText(topnews.get(prePosition).getTitle());

            //把之前的移除
            llGroupPoint.removeAllViews();
            //添加红点
            for(int i =0;i<topnews.size();i++){
                //添加线性布局
                ImageView point = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, ViewGroup.LayoutParams.WRAP_CONTENT);

                if(i!=0){
                    //设置距离左边距离
                    params.leftMargin = DensityUtil.dip2px(mContext,8);
                    point.setEnabled(false);
                }else{
                    point.setEnabled(true);
                }
                point.setLayoutParams(params);
                point.setBackgroundResource(R.drawable.point_selector);
                llGroupPoint.addView(point);
            }
        }else{
            //更多的
            isMore =false;
            List<TabDetailPagerBean.DataEntity.NewsEntity> moreNews = pagerBean.getData().getNews();
            news.addAll(moreNews);
            adapter.notifyDataSetChanged();
        }



    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //先把之前的变灰
            llGroupPoint.getChildAt(prePosition).setEnabled(false);
            //把当前变高亮
            llGroupPoint.getChildAt(position).setEnabled(true);
            prePosition = position;
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
