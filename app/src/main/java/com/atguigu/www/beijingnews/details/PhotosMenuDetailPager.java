package com.atguigu.www.beijingnews.details;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.adapter.PhotosMenuDetailPagerAdapter;
import com.atguigu.www.beijingnews.base.basepager.MenuDetailBasePager;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;
import com.atguigu.www.beijingnews.bean.PhotosMenuDetailPagerBean;
import com.atguigu.www.beijingnews.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2017/2/6.
 */

public class PhotosMenuDetailPager extends MenuDetailBasePager {
    private final NewsCenterBean.DataBean dataBean;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private String url;
    private PhotosMenuDetailPagerAdapter adapter;

    public PhotosMenuDetailPager(Context mContext, NewsCenterBean.DataBean dataBean) {
        super(mContext);
        this.dataBean =dataBean;
    }




    @Override
    public View initView() {
        //图组详细页面的视图
        View view = View.inflate(mContext, R.layout.photos_menudetail_pager, null);
        ButterKnife.inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        url = Constants.BASE_URL + dataBean.getUrl();
        Log.e("TAG",url);
        getDataFromNet(url);
    }

    private void getDataFromNet(String url) {
        RequestParams params =new RequestParams(this.url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
            //Log.e("TAG","请求图片成功========="+result);
                processData(result);

            }

            private void processData(String json) {
                PhotosMenuDetailPagerBean bean = new Gson().fromJson(json, PhotosMenuDetailPagerBean.class);
                Log.e("TAG","数组解析数据成功======"+ bean.getData().getNews().get(0).getTitle());
                //设置RecyclerView的适配器
                adapter = new PhotosMenuDetailPagerAdapter(mContext,bean.getData().getNews());
                recyclerview.setAdapter(adapter);

                //布局管理器
                recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            Log.e("TAG","请求图片失败========="+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private boolean isList = true;

    public void swichListGrid(ImageButton ib_swich_list_gird) {
        if(isList){
            //Grid
            isList = false;
            //设置布局管理器
            recyclerview.setLayoutManager(new GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false));

            //按钮设置List效果
            ib_swich_list_gird.setImageResource(R.drawable.icon_pic_list_type);
        }else{
            //List
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            isList = true;
            //按钮设置Grid效果
            ib_swich_list_gird.setImageResource(R.drawable.icon_pic_grid_type);
        }

    }
}
