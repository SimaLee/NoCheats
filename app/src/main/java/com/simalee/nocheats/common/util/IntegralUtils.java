package com.simalee.nocheats.common.util;

/**
 * Created by Lee Sima on 2017/6/23.
 */

/**
 * 积分体系换算规则
 */
public class IntegralUtils {

    private static final int BASE_POINT = 10;


    private static final int POINT_LEVEL_0 = 0;
    private static final int POINT_LEVEL_1 = BASE_POINT;
    private static final int POINT_LEVEL_2 = POINT_LEVEL_1 * 2;
    private static final int POINT_LEVEL_3 = POINT_LEVEL_2 * 2;
    private static final int POINT_LEVEL_4 = POINT_LEVEL_3 * 2;
    private static final int POINT_LEVEL_5 = POINT_LEVEL_4 * 2;
    private static final int POINT_LEVEL_6 = POINT_LEVEL_5 * 2;
    private static final int POINT_LEVEL_7 = POINT_LEVEL_6 * 2;
    private static final int POINT_LEVEL_8 = POINT_LEVEL_7 * 2;
    private static final int POINT_LEVEL_9 = POINT_LEVEL_8 * 2;
    private static final int POINT_LEVEL_10 = POINT_LEVEL_9 * 2;



    private IntegralUtils(){}


    public static int getLevel(String pointStr){
        return getLevel(Integer.parseInt(pointStr));
    }

    public static int getLevel(int point){

        if (pointIsIn(POINT_LEVEL_0,POINT_LEVEL_1,point)){
            return 1;
        }else if (pointIsIn(POINT_LEVEL_1,POINT_LEVEL_2,point)){
            return 2;
        }else if (pointIsIn(POINT_LEVEL_2,POINT_LEVEL_3,point)){
            return 3;
        }else if (pointIsIn(POINT_LEVEL_3,POINT_LEVEL_4,point)){
            return 4;
        }else if (pointIsIn(POINT_LEVEL_4,POINT_LEVEL_5,point)){
            return 5;
        }else if (pointIsIn(POINT_LEVEL_5,POINT_LEVEL_6,point)){
            return 6;
        }else if (pointIsIn(POINT_LEVEL_6,POINT_LEVEL_7,point)){
            return 7;
        }else if (pointIsIn(POINT_LEVEL_7,POINT_LEVEL_8,point)){
            return 8;
        }else if (pointIsIn(POINT_LEVEL_8,POINT_LEVEL_9,point)){
            return 9;
        }else if (pointIsIn(POINT_LEVEL_9,POINT_LEVEL_10,point)){
            return 10;
        }else {
            return 10086;
        }
    }

    private static boolean pointIsIn(int start,int end,int target){
        return start<target  && end >= target;
    }

}
