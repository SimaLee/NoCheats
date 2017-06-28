package com.simalee.nocheats.common.util;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Lee Sima on 2017/6/16.
 */

public class Utils {

    private static Context context;

    private Utils(){
        throw new UnsupportedOperationException("Utils can't be instantiated!");
    }

    /**
     * 初始化工具类
     * @param context 上下文
     */
    public static void init(@NonNull Context context){
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取 ApplicationContext
     * @return ApplicationContext
     */
    public static Context getContext(){
        if (context  != null){
            return context;
        }
        throw new NullPointerException("Utils should be initialized first");
    }

}
