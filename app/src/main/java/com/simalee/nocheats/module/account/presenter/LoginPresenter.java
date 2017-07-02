package com.simalee.nocheats.module.account.presenter;

import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.account.contract.LoginContract;
import com.simalee.nocheats.module.data.entity.user.UserInfo;
import com.simalee.nocheats.module.data.model.IUserModel;
import com.simalee.nocheats.module.data.model.UserModel;

/**
 * Created by Lee Sima on 2017/6/16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private IUserModel userModel;

    public LoginPresenter(LoginContract.View loginView){
        if (loginView == null){
            throw new NullPointerException("login view is null!");
        }
        mLoginView = loginView;
        userModel = new UserModel();
    }

    @Override
    public void login(UserInfo user) {
        //TODO 调用model层的数据进行网络操作

        userModel.login(user, new IUserModel.LoginCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e("LoginPresenter",e.toString());
            }

            @Override
            public void onLoginSuccess(String user_id) {
                mLoginView.onLoginSuccess(user_id);
            }

            @Override
            public void onLoginFailure(String reason) {
                LogUtils.d("LoginPresenter","登录失败："+reason);
                mLoginView.onLoginFailed(reason);
                mLoginView.clearUserName();
                mLoginView.clearPassword();
            }
        });

    }

    @Override
    public void toRegister() {
        mLoginView.toRegister();
    }

    @Override
    public void start() {
        //do nothing
    }
}
