package com.atguigu.www.beijingnews.base.basepager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.www.beijingnews.base.BasePager;
import com.atguigu.www.beijingnews.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by lenovo on 2017/2/5.
 * 新闻中心
 */

public class NewsCenterPager extends BasePager {
    public NewsCenterPager(Context mContext) {
        super(mContext);
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG","新闻中心页面加载数据了");
        //设置标题
        tv_title.setText("新闻中心");
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("新闻中心");
        textView.setTextColor(Color.RED);

        //和父类fragment结合
        fl_main.addView(textView);
        //联网请求
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","请求成功=="+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","请求失败=="+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","onCancelled=="+cex.getMessage());

            }

            @Override
            public void onFinished() {
                Log.e("TAG","请求结束==");

            }
        });
    }
}
