package com.simalee.nocheats.module.data.model;

import android.graphics.Bitmap;

import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.common.callback.BitmapCallback;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.Utils;
import com.simalee.nocheats.module.data.entity.user.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class UserModel implements IUserModel{

    private static final String TAG = UserModel.class.getSimpleName();

    public UserModel(){
        initOkhttp();
    }
    private void initOkhttp(){
        //配置OkHttp
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(Utils.getContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
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
                            String userId = jsonObject.getString("id");

                            if ("0".equals(msg)){
                                //TODO 保存用户信息 用于登录后操作
                                callback.onLoginSuccess(userId);
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
    public void register(String account, String passwd, String code, final RegisterCallback callback) {
        //TODO 接入网络操作
        initOkhttp();
        OkHttpUtils.post()
                .url(Constant.Url.URL_REGISTER)
                .addParams("phone",account)
                .addParams("pwd",passwd)
                .addParams("pic_code",code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e(TAG,e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"register response: "+ response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Response failureReason = new Response();

                            if ("0".equals(msg)){
                                //String userId = jsonObject.getString("id");
                                String userId = "123";
                                callback.onRegisterSuccess(userId);
                            }else if ("1".equals(msg)){
                                failureReason.setType(Response.TYPE_WRONG_PCODE);
                                failureReason.setError("图片验证码错误");
                                callback.onRegisterFailed(failureReason);

                            }else if ("2".equals(msg)){
                                failureReason.setType(Response.TYPE_ACCOUNT_EXIST);
                                failureReason.setError("该手机号已被使用");
                                callback.onRegisterFailed(failureReason);

                            }
                            else if ("3".equals(msg)){
                                failureReason.setType(Response.TYPE_SYSTEM_ERROR);
                                failureReason.setError("系统正在维护升级，暂停服务");
                                callback.onRegisterFailed(failureReason);

                            }else if ("4".equals(msg)){
                                // do nothing
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        /*if ("123".equals(account) && "123".equals(passwd) && "123".equals(code)){
            callback.onRegisterSuccess("123");
        }else{
            callback.onRegisterFailed("出错了");
        }*/
    }


    @Override
    public void changePasswd(String account, String oldPasswd, String newPasswd, ChangePasswdCallback callback) {

    }

    @Override
    public void setNickName(String id, String nickName, final ChangeNickNameCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_EDIT_NICKNAME)
                .addParams("u_id",id)
                .addParams("u_name",nickName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.d("UserModel",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if ("0".equals(msg)){
                                callback.onChangeSuccess();
                            }else if ("1".equals(msg)){
                                callback.onNickNameExist();
                            }else if ("2".equals(msg)){
                                callback.onChangFailure("系统维护中");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public void checkNickName(String nickName, final ChangeNickNameCallback callback) {
        if (callback == null){
            return;
        }
        //TODO 接入网络操作

        OkHttpUtils.post()
                .url(Constant.Url.URL_CHECK_NAME)
                .addParams("name",nickName)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e("UserModel",e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    if ("0".equals(msg)){
                        //可用 do nothing

                    }else if ("1".equals(msg)){
                        //已存在
                        callback.onNickNameExist();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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
