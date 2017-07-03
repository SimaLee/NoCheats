package com.simalee.nocheats.common.config;

import java.net.FileNameMap;

/**
 * Created by Lee Sima on 2017/6/27.
 */

public class Constant {


    public class Url{
        public static final String BASE_URL = "http://119.29.191.103:8080/NoCheats/";

        public static final String URL_CHECK_PHONE = BASE_URL + "checkPhone.action";
        public static final String URL_LOGIN = BASE_URL + "login.action";
        public static final String URL_GET_VERIFY_CODE = BASE_URL + "getVerifyCode.action";
        public static final String URL_REGISTER = BASE_URL + "register.action";
        public static final String URL_CHECK_NAME = BASE_URL + "checkName.action";
        public static final String URL_EDIT_NICKNAME = BASE_URL + "editUserName.action";


        //-----------------------------------------------------------
        public static final String URL_GET_USER_INFORMATION = BASE_URL +"checkUserInf.action";
        public static final String URL_EDIT_USER_NAME = BASE_URL+"editUserName.action";
        public static final String URL_EDIT_USER_SIGN = BASE_URL+"editUserSign.action";
        public static final String URL_EDIT_USER_BIRTHDAY = BASE_URL+"editUserBirthday.action";
        public static final String URL_EDIT_USER_INTRO = BASE_URL+"editUserIntro.action";
        public static final String URL_EDIT_USER_GENDER = BASE_URL+"editUserGender.action";
        public static final String URL_CHANGE_USER_PASSWORD = BASE_URL+"changePwd.action";
        public static final String URL_EDIT_USER_LOGO = BASE_URL+"editUserLogo.action";
        public static final String URL_UPLOAD_POST_PIC = BASE_URL+"uploadPostPic.action";
    }

    public class UserInfo{
        public static final String USER_NAME = "no cheats";
        public static final String GENDER = "1";
        public static final String BITTHDAY = "2000-01-01";
        public static final String SIGN ="愿世界再没有上当受骗之人";
        public static final String INTRO = "防骗专家";
    }

    public static class FOLDER{
        public static final String APP_BASE_FOLDER = "/no cheats";
        public static final String REPORT_FOLDER = APP_BASE_FOLDER + "/TaskReport/";
        public static final String PICTURES_FOLDER = APP_BASE_FOLDER +"/Photos/";
    }
    public static class CODE {
        //intent action
        public static final int PICK_FROM_CAMERA = 0;
        public static final int PICK_FROM_FILE = 1;
        public static final int ACTION_CROP = 2;

    }
}
