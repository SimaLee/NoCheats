package com.simalee.nocheats.module.data.model;

import android.graphics.Bitmap;

import com.simalee.nocheats.common.callback.BitmapCallback;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.module.data.entity.user.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class UserModel implements IUserModel{

    private static final String TAG = UserModel.class.getSimpleName();



    @Override
    public void login(UserInfo userInfo, final LoginCallback callback) {
        //TODO 接入API
        OkHttpUtils.post()
                .url(Constant.Url.URL_LOGIN)
                .addParams("phone",userInfo.getAccunt())
                .addParams("pwd",userInfo.getPassword())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }
                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            String uesrId = jsonObject.getString("id");

                            if ("0".equals(msg)){
                                callback.onLoginSuccess();
                            }else if ("1".equals(msg)){
                                callback.onLoginFailure("密码错误");
                            }else if ("2".equals(msg)){
                                callback.onLoginFailure("账号不存在");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


        /*if (userInfo.getAccunt().equals("123") && userInfo.getPassword().equals("123")){
            callback.onLoginSuccess();
        }else{
            callback.onLoginFailure("账号或密码有错!");
        }*/
    }

    @Override
    public void register(String account, String passwd, String code, RegisterCallback callback) {
        //TODO 接入网络操作

        if ("123".equals(account) && "123".equals(passwd) && "123".equals(code)){
            callback.onRegisterSuccess();
        }else{
            callback.onRegisterFailed("出错了");
        }
    }


    @Override
    public void changePasswd(String account, String oldPasswd, String newPasswd, ChangePasswdCallback callback) {

    }

    @Override
    public void setNickName(String id, String nickName, ChangeNickNameCallback callback) {
        if (callback == null){
            return;
        }
        //TODO 接入网络操作
        if ("123".equals(nickName)){
            callback.onChangeSuccess();
        }else{
            callback.onChangFailure("该昵称已存在");
        }
    }

    @Override
    public void checkNickName(String nickName, ChangeNickNameCallback callback) {
        if (callback == null){
            return;
        }
        //TODO 接入网络操作
        if (!"123".equals(nickName)){
            callback.onNickNameExist();
        }
    }

    @Override
    public void loadPcode(final LoadPcodeCallback callback) {
        if (callback == null){
            return;
        }
        OkHttpUtils.get()
                .url(Constant.Url.URL_GET_VERIFY_CODE)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        callback.onResponse(response);
                    }
                });
    }


}
