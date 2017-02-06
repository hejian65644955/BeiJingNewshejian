package com.atguigu.www.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.www.beijingnews.R;
import com.atguigu.www.beijingnews.activity.MainActivity;
import com.atguigu.www.beijingnews.base.BaseFragment;
import com.atguigu.www.beijingnews.bean.NewsCenterBean;
import com.atguigu.www.beijingnews.utils.DensityUtil;

import java.util.List;

/**
 * Created by lenovo on 2017/2/5.
 * 作用：左侧菜单
 */

public class LeftMenuFragment extends BaseFragment {

    private ListView listView;
    private List<NewsCenterBean.DataBean> datas;
    private LeftMenuFramgmentAdapter adapter;
    private int prePosition;

    @Override
    public View initView() {
        listView =new ListView(mContext);
        listView.setPadding(0, DensityUtil.dip2px(mContext,40),0,0);
        listView.setBackgroundColor(Color.BLACK);
        //设置监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录位置和刷新适配器
                prePosition = position;
                adapter.notifyDataSetChanged();
                //2.关闭侧滑菜单
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.getSlidingMenu().toggle();
            }
        });
        return listView;
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void setData(List<NewsCenterBean.DataBean> dataBeanList) {
        this.datas =dataBeanList;
        //设置适配器

        adapter = new LeftMenuFramgmentAdapter();
        listView.setAdapter(adapter);
    }
    class LeftMenuFramgmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(mContext, R.layout.item_leftmenu, null);
            //设置内容
            textView.setText(datas.get(position).getTitle());

            if(prePosition ==position){
                textView.setEnabled(true);
            }else {
                textView.setEnabled(false);
            }
            return textView;
        }
    }
}
