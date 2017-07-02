package com.simalee.nocheats.module.data.model;

import android.graphics.Bitmap;

import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.module.data.entity.user.UserInfo;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public interface IUserModel {

    interface LoginCallback{
        void onError(Exception e);
        void onLoginSuccess(String user_id);
        void onLoginFailure(String reason);
    }

    interface RegisterCallback{
        void onRegisterSuccess(String id);
        void onRegisterFailed(Response reason);
    }


    interface ChangePasswdCallback{
        void onChangeSuccess();
        void onChangeFailure(String reason);
    }

    interface ChangeNickNameCallback{
        void onChangeSuccess();
        void onChangFailure(String reason);
        void onNickNameExist();
    }

    interface LoadPcodeCallback{
        void onError(Exception e);
        void onResponse(Bitmap bitmap);
    }
    void login(UserInfo userInfo,LoginCallback callback);

    void register(String account,String passwd,String code ,RegisterCallback callback);

    void changePasswd(String account,String oldPasswd,String newPasswd,
                      ChangePasswdCallback callback);

    void setNickName(String id,String nickName,ChangeNickNameCallback callback);

    void checkNickName(String nickName,ChangeNickNameCallback callback);

    void loadPcode(LoadPcodeCallback callback);
}
