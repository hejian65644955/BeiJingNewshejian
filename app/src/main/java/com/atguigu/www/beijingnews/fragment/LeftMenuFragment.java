package com.atguigu.www.beijingnews.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.www.beijingnews.base.BaseFragment;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;

import java.util.List;

/**
 * Created by lenovo on 2017/2/5.
 * 作用：左侧菜单
 */

public class LeftMenuFragment extends BaseFragment {

    private TextView textView;
    private List<NewsCenterBean.DataBean> datas;

    @Override
    public View initView() {
        textView =new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("左侧菜单--Fragment");
    }

    public void setData(List<NewsCenterBean.DataBean> dataBeanList) {
        this.datas =dataBeanList;
        for(int i=0;i<datas.size();i++){
            Log.e("TAG",datas.get(i).getTitle());
        }
    }
}
