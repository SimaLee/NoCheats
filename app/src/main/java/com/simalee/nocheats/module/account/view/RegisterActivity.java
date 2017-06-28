package com.simalee.nocheats.module.account.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.module.account.contract.RegisterContract;
import com.simalee.nocheats.module.account.presenter.RegisterPresenter;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View{

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private TextInputLayout textInputLayoutAccount;
    private TextInputLayout textInputLayoutPasswd;
    private TextInputLayout textInputLayoutCode;

    private TextInputEditText text_account;
    private TextInputEditText text_passwd;
    private TextInputEditText text_code;
    private ImageView image_code;
    private TextView tv_nextStep;

    private RegisterContract.Presenter mRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_1);

        mRegisterPresenter = new RegisterPresenter(this);

        initViews();

    }

    private void initViews(){

        textInputLayoutAccount = (TextInputLayout) findViewById(R.id.text_input_layout_account);
        textInputLayoutPasswd = (TextInputLayout) findViewById(R.id.text_input_layout_passwd);
        textInputLayoutCode = (TextInputLayout) findViewById(R.id.text_input_layout_pcode);

        text_account = (TextInputEditText) findViewById(R.id.text_input_account);
        text_passwd = (TextInputEditText) findViewById(R.id.text_input_passwd);
        text_code = (TextInputEditText) findViewById(R.id.text_input_code);
        tv_nextStep = (TextView) findViewById(R.id.tv_next_step);
        image_code = (ImageView) findViewById(R.id.iv_pcode);


        text_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutAccount.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        text_passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutPasswd.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = text_account.getText().toString().trim();
                String passwd = text_passwd.getText().toString().trim();
                String pcode = text_code.getText().toString().trim();
                if (validateInput(account,passwd)){
                    mRegisterPresenter.register(account,passwd,passwd);
                }

            }
        });

        image_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"获取验证码",1000).show();
                if (mRegisterPresenter != null){
                    mRegisterPresenter.loadPcode();
                }
            }
        });

        mRegisterPresenter.loadPcode();

    }


    @Override
    public void showRegisterFailed(String reason) {
        textInputLayoutAccount.setError(reason);
    }

    @Override
    public void showCheckNameError(String reason) {
        //do nothing
    }

    @Override
    public void showNickNameUpdateFailure(String reason) {
        //do nothing
    }


    @Override
    public void toNextStep() {
        Intent intent = new Intent(RegisterActivity.this,RegisterStep2Activity.class);
        startActivity(intent);
    }

    @Override
    public void showPCode(Bitmap bitmap) {
        image_code.setImageBitmap(bitmap);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        //do nothing
    }

    private boolean validateInput(String account,String passwd){
        if (account == null || passwd == null ){
            return false;
        }else{
            if (account.length() <=2){
                textInputLayoutAccount.setError("账号格式不正确!");
                return false;//测试用
            }
            if (passwd.length() <=2){
                textInputLayoutPasswd.setError("密码长度不能少于3位");
                return false;//测试用
            }

            return true;
        }

    }
}
