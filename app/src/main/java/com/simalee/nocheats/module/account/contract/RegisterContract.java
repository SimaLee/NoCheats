package com.simalee.nocheats.module.account.contract;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.common.base.Response;

/**
 * Created by Lee Sima on 2017/6/23.
 */

public interface RegisterContract {


    interface View extends BaseView<Presenter>{

        void showRegisterFailed(Response reason);

        void showCheckNameError(String reason);

        void showNickNameUpdateFailure(String reason);

        void toNextStep(String id);

        void showPCode(Bitmap bitmap);

    }


    interface Presenter extends BasePresenter{

        void register(String account,String passwd,String code);

        void setNickName(String id,String nickName);

        void checkNickName(String nickName);

        void loadPcode();

    }

}
