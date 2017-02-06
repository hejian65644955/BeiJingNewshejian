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

    /**
     * 缓存文本信息
     * @param mContext
     * @param key
     * @param value
     */

    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sp =mContext.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     * 得到文本信息
     * @param mContext
     * @param key
     * @return
     */
    public static String getString(Context mContext, String key) {
        SharedPreferences sp =mContext.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

}
