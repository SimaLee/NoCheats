package com.simalee.nocheats.module.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.module.MainActivity;
import com.simalee.nocheats.module.account.contract.LoginContract;
import com.simalee.nocheats.module.account.presenter.LoginPresenter;
import com.simalee.nocheats.module.assistant.AssistantActivity;
import com.simalee.nocheats.module.data.entity.user.UserInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import okhttp3.OkHttpClient;

/**
 * Created by Lee Sima on 2017/6/16.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View{
    private static final String TAG = LoginActivity.class.getSimpleName();

    private TextInputEditText text_user;
    private TextInputEditText text_password;
    private TextView text_forgetPassword;
    private TextView text_to_register;
    private Button btnLogin;


    private LoginContract.Presenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isLogin();
        mLoginPresenter = new LoginPresenter(this);
        initOkhttp();
        initViews();
    }
    private void isLogin(){
        String is_login = PreferenceUtil.getString(LoginActivity.this,PreferenceUtil.IS_LOGIN);
        if(is_login.equals("1")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void initOkhttp(){
        //配置OkHttp
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
    private void initViews(){

        text_user = (TextInputEditText) findViewById(R.id.text_input_user);
        text_password = (TextInputEditText) findViewById(R.id.text_input_passwd);
        text_forgetPassword = (TextView) findViewById(R.id.tv_forget_passwd);
        text_to_register = (TextView) findViewById(R.id.tv_to_register);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mLoginPresenter != null){
                    UserInfo user = new UserInfo();
                    user.setAccunt(text_user.getText().toString());
                    user.setPassword(text_password.getText().toString());
                    mLoginPresenter.login(user);
                }
            }
        });

       text_to_register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (mLoginPresenter != null){

                   mLoginPresenter.toRegister();
               }
           }
       });

        // 便于测试
        String account = "12345678901";
        text_user.setText(account);
        text_user.setSelection(account.length());
        text_password.setText("123456");

    }

    @Override
    public void clearUserName() {
        text_user.setText("");
    }

    @Override
    public void clearPassword() {
        text_password.setText("");
    }

    @Override
    public void onLoginSuccess(String userId) {
        PreferenceUtil.setString(LoginActivity.this,PreferenceUtil.USER_ID,userId);
        PreferenceUtil.setString(LoginActivity.this,PreferenceUtil.IS_LOGIN,"1");
        PreferenceUtil.setString(LoginActivity.this,PreferenceUtil.PHONE,text_user.getText().toString().trim());
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(String reason) {
        Toast.makeText(this,"登陆失败:"+reason,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toRegister() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        //do nothing
    }
}
