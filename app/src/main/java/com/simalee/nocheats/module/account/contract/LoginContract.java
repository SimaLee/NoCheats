package com.simalee.nocheats.module.account.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.user.UserInfo;

/**
 * Created by Lee Sima on 2017/6/16.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{
        void clearUserName();
        void clearPassword();

        void onLoginSuccess(String user_id);
        void onLoginFailed(String reason);

        void toRegister();

    }

    interface Presenter extends BasePresenter{
        void login(UserInfo user);
        void toRegister();
    }

}
