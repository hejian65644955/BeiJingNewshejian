package com.atguigu.www.beijingnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.bean.PhotosMenuDetailPagerBean;

import java.util.List;

/**
 * Created by lenovo on 2017/2/9.
 */
public class PhotosMenuDetailPagerAdapter extends RecyclerView.Adapter {
    private final List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;
    private final Context mContext;

    public PhotosMenuDetailPagerAdapter(Context mContext, List<PhotosMenuDetailPagerBean.DataBean.NewsBean> news) {
        this.mContext =mContext;
        this.datas =news;

    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(View.inflate(mContext, R.layout.item_photosmenu_detail_pager,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    class ViewHoder extends RecyclerView.ViewHolder{

        public ViewHoder(View itemView) {
            super(itemView);
        }
    }
}
