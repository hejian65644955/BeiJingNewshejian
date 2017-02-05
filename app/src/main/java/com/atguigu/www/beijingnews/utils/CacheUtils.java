package com.atguigu.www.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 2017/2/5.
 */

public class CacheUtils {

    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sp =mContext.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    //得到保存的参数
    public static Boolean getBoolean(Context mContext, String key) {
        SharedPreferences sp =mContext.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

}
