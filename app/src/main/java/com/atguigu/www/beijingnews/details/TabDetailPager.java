package com.atguigu.www.beijingnews.details;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.adapter.TabDetailPagerAdapter;
import com.atguigu.www.beijingnews.base.basepager.MenuDetailBasePager;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;
import com.atguigu.www.beijingnews.bean.TabDetailPagerBean;
import com.atguigu.www.beijingnews.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by lenovo on 2017/2/6.
 */

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    ListView listview;
    private String url;
    private TabDetailPagerAdapter adapter;
    private List<TabDetailPagerBean.DataEntity.NewsEntity> news;

    public TabDetailPager(Context mContext, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(mContext);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        //图组详细页面的视图
        View view = View.inflate(mContext, R.layout.table_detail_pager, null);
        listview= (ListView) view.findViewById(R.id.listview);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL+childrenBean.getUrl();
        Log.e("TAG","url==TabDetailPager------------------"+url);
        //设置数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","请求数据成功+==TabDetailPager"+result);
                processData(result);

            }



            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","请求数据失败+==TabDetailPager"+ex.getMessage());

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
        Log.e("TAG","数据解析成功==TabDetailPager=="+pagerBean.getData().getNews().get(0).getTitle());
        news = pagerBean.getData().getNews();

        //设置适配器
        adapter = new TabDetailPagerAdapter(mContext,news);
        listview.setAdapter(adapter);

    }

}
