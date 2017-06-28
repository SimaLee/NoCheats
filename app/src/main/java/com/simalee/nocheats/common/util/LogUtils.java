package com.simalee.nocheats.common.util;

import android.util.Log;

/**
 * Created by Lee Sima on 2017/6/16.
 */

public class LogUtils {

    public static final int V = Log.VERBOSE;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int W = Log.WARN;
    public static final int E = Log.ERROR;
    public static final int A = Log.ASSERT;

    private static  boolean isDebug = true;

    public  static void enableDebug(){
        isDebug = true;
    }

    public static void disableDebug(){
        isDebug = false;
    }

    private LogUtils(){
        throw new UnsupportedOperationException("LogUtils can't be instantiated!");
    }

    public static void v(String tag,String msg){
        if (isDebug){
            Log.v(tag,msg);
        }
    }

    public static void v(String tag, String msg, Throwable throwable){
        if (isDebug){
            Log.v(tag,msg,throwable);
        }
    }

    public static void d(String tag,String msg){
        if (isDebug){
            Log.d(tag,msg);
        }
    }

    public static void d(String tag, String msg, Throwable throwable){
        if (isDebug){
            Log.d(tag,msg,throwable);
        }
    }

    public static void i(String tag,String msg){
        if (isDebug){
            Log.i(tag,msg);
        }
    }

    public static void i(String tag, String msg, Throwable throwable){
        if (isDebug){
            Log.i(tag,msg,throwable);
        }
    }

    public static void w(String tag,String msg){
        if (isDebug){
            Log.w(tag,msg);
        }
    }

    public static void w(String tag, String msg, Throwable throwable){
        if (isDebug){
            Log.w(tag,msg,throwable);
        }
    }

    public static void e(String tag,String msg){
        if (isDebug){
            Log.e(tag,msg);
        }
    }

    public static void e(String tag, String msg, Throwable throwable){
        if (isDebug){
            Log.e(tag,msg,throwable);
        }
    }

}
