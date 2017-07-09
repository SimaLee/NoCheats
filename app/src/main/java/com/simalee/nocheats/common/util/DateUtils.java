package com.simalee.nocheats.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public class DateUtils {

    private DateUtils(){
        throw new UnsupportedOperationException("u can't instantiate DateUtils!");
    }


    /**
     * 将输入的日期加一秒返回 用于获取数据
     * @param oldDateStr
     * @return
     */
    public static String plusOneSecond(String oldDateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldDate = null;

        try {
            oldDate = sdf.parse(oldDateStr);

        } catch (ParseException e) {

            e.printStackTrace();

        }
        return plusOneSecond(oldDate);
    }

    /**
     * 将输入的日期加一秒返回 用于获取数据
     * @param oldDate
     * @return
     */
    public static String plusOneSecond(Date oldDate){

        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.setTime(oldDate);

        oldCalendar.add(Calendar.SECOND,1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateString(oldCalendar.getTime(),sdf);

    }


    /**
     * @param date 日期字符串
     * @param format 转化成的日期格式
     * @return
     */
    public static String getDateString(String date,SimpleDateFormat format){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = "";
        Date currentDate ;
        try {
            currentDate = sdf.parse(date);
            res = format.format(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**将date转换为指定格式的String
     * @param date
     * @param format
     * @return
     */
    public static String getDateString(Date date,SimpleDateFormat format){
        String res = format.format(date);
        return res;
    }

}
