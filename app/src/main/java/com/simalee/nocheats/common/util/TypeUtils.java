package com.simalee.nocheats.common.util;

/**
 * Created by Lee Sima on 2017/7/11.
 */

public class TypeUtils {

    private TypeUtils(){
        throw new UnsupportedOperationException("u can't instantiate TypeUtils");
    }
    static String[] TYPES = {"首页","金融骗术","电信骗术","网络骗术","街头骗术","其他骗术"};

    public static String getTypeString(int type){
        return TYPES[type];
    }
}
