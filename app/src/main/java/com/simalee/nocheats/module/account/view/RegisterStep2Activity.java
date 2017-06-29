package com.simalee.nocheats.module.account.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.account.contract.RegisterContract;
import com.simalee.nocheats.module.account.presenter.RegisterPresenter;

/**
 * Created by Lee Sima on 2017/6/23.
 */

public class RegisterStep2Activity extends BaseActivity implements RegisterContract.View{

    private static final  String TAG =  RegisterStep2Activity.class.getSimpleName();

    private TextInputLayout textInputLayoutNickName;
    private TextInputEditText text_nickName;
    private TextView tv_nextStep;

    private RegisterContract.Presenter mRegisterPresenter;

    String nickName;
    String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_2);
        userId = getIntent().getStringExtra("id");
        LogUtils.d(TAG,"userId is :"+userId);
        initViews();

        mRegisterPresenter = new RegisterPresenter(this);
    }

    private void initViews(){
        textInputLayoutNickName = (TextInputLayout) findViewById(R.id.text_input_layout_nickName);
        text_nickName = (TextInputEditText) findViewById(R.id.text_input_nickName);
        tv_nextStep = (TextView) findViewById(R.id.tv_next_step);

        text_nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutNickName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null){
                    //每次字符发生变化时，移除上一次的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                nickName = s.toString();

                if (nickName.length() != 0){

                    //延迟1000ms，如果不再输入字符则开始调用接口
                    handler.postDelayed(delayRun,1000);
                }

            }
        });

        tv_nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRegisterPresenter != null){
                    String nickName = text_nickName.getText().toString();
                    mRegisterPresenter.setNickName(userId,nickName);
                }

            }
        });
    }

    @Override
    public void showRegisterFailed(Response reason) {
        //do nothing
    }

    @Override
    public void showCheckNameError(String reason) {
        textInputLayoutNickName.setError(reason);
    }

    @Override
    public void showNickNameUpdateFailure(String reason) {
        Toast.makeText(this,reason,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toNextStep(String id) {
        //此处id无用 为了区分 传递id值为"nothing";
        Intent intent = new Intent(RegisterStep2Activity.this,RegisterStep3Activity.class);
        startActivity(intent);
    }

    @Override
    public void showPCode(Bitmap bitmap) {
        //do nothing
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        //do nothing
    }



    private Handler handler = new Handler();
    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            if (mRegisterPresenter != null){
                mRegisterPresenter.checkNickName(nickName);
            }
        }
    };
}
