package com.simalee.nocheats.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yang on 2016/12/2.
 */
public class PreferenceUtil {
    public static final String USER_ID = "user_id";
    public static final String IS_LOGIN = "is_Login";// “0” 代表为登陆，“1”代表已登陆
    public static final String PHONE = "phone";
    /**
     * 存储信息
     */
    public static void setString(Context context, String key, String value){
        // 得到SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences(
                "preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取信息
     */
    public static String getString(Context context, String key)
    {
        SharedPreferences preferences = context.getSharedPreferences(
                "preference", Context.MODE_PRIVATE);
        // 返回key值，key值默认值是false
        return preferences.getString(key,"");
    }
}
