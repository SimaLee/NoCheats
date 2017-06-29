package com.simalee.nocheats.module.account.presenter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.account.contract.RegisterContract;
import com.simalee.nocheats.module.data.model.IUserModel;
import com.simalee.nocheats.module.data.model.UserModel;

/**
 * Created by Lee Sima on 2017/6/23.
 */

public class RegisterPresenter implements RegisterContract.Presenter {


    private RegisterContract.View mRegisterView;

    private IUserModel userModel;

    public RegisterPresenter(RegisterContract.View registerView){
        if (registerView == null){
            throw new NullPointerException("RegisterView is null!");
        }
        mRegisterView = registerView;
        userModel = new UserModel();
    }


    @Override
    public void register(String account, String passwd, String code) {
        //TODO 访问Model层数据
        userModel.register(account, passwd, code, new IUserModel.RegisterCallback() {
            @Override
            public void onRegisterSuccess(String id) {
                mRegisterView.toNextStep(id);
            }

            @Override
            public void onRegisterFailed(Response reason) {
                mRegisterView.showRegisterFailed(reason);
            }
        });

    }

    @Override
    public void setNickName(String id, String nickName) {

        userModel.setNickName(id, nickName, new IUserModel.ChangeNickNameCallback() {
            @Override
            public void onChangeSuccess() {
                mRegisterView.toNextStep("nothing");
            }

            @Override
            public void onChangFailure(String reason) {
                mRegisterView.showNickNameUpdateFailure(reason);
            }

            @Override
            public void onNickNameExist() {
                mRegisterView.showCheckNameError("该昵称已被使用！");
            }
        });
        //TODO 访问Model层数据

    }

    @Override
    public void checkNickName(String nickName) {
        userModel.checkNickName(nickName, new IUserModel.ChangeNickNameCallback() {
            @Override
            public void onChangeSuccess() {
                //do nothing
            }

            @Override
            public void onChangFailure(String reason) {
                //do nothing
            }

            @Override
            public void onNickNameExist() {
                mRegisterView.showCheckNameError("该昵称已存在");
            }
        });


    }

    @Override
    public void loadPcode() {
        LogUtils.d("RegisterPresenter","loading pcode");
        //TODO 访问Model层数据
        userModel.loadPcode(new IUserModel.LoadPcodeCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e("RegisterPresenter",e.toString());
            }

            @Override
            public void onResponse(Bitmap bitmap) {
                if (mRegisterView != null){
                    mRegisterView.showPCode(bitmap);
                }
            }
        });
    }


    @Override
    public void start() {
        //do nothing
    }
}
