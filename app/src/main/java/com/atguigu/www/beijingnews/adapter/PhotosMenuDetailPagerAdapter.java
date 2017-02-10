package com.atguigu.www.beijingnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.activity.PicassoSampleActivity;
import com.atguigu.www.beijingnews.bean.PhotosMenuDetailPagerBean;
import com.atguigu.www.beijingnews.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lenovo on 2017/2/9.
 */
public class PhotosMenuDetailPagerAdapter extends RecyclerView.Adapter<PhotosMenuDetailPagerAdapter.ViewHolder> {
    private final List<PhotosMenuDetailPagerBean.DataBean.NewsBean> datas;
    private final Context mContext;
    private final DisplayImageOptions options;

    public PhotosMenuDetailPagerAdapter(Context mContext, List<PhotosMenuDetailPagerBean.DataBean.NewsBean> news) {
        this.mContext = mContext;
        this.datas = news;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.news_pic_default)
                .showImageForEmptyUri(R.drawable.news_pic_default)
                .showImageOnFail(R.drawable.news_pic_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_photosmenu_detail_pager, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //1.根据位置得到数据
        PhotosMenuDetailPagerBean.DataBean.NewsBean newsBean = datas.get(position);
        holder.tvTitle.setText(newsBean.getTitle());
        //设置图片和加载图片
//        Glide.with(mContext).load(Constants.BASE_URL+newsBean.getListimage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.news_pic_default)
//                .error(R.drawable.news_pic_default)
//                .into(holder.ivIcon);

        ImageLoader.getInstance().displayImage(Constants.BASE_URL+newsBean.getListimage(), holder.ivIcon, options);


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.iv_icon)
        ImageView ivIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            //设置点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PicassoSampleActivity.class);
                    intent.putExtra("url",Constants.BASE_URL+datas.get(getLayoutPosition()).getListimage());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
