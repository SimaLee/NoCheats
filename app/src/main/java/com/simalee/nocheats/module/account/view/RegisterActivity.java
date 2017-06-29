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
import android.widget.Toast;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.account.contract.RegisterContract;
import com.simalee.nocheats.module.account.presenter.RegisterPresenter;
import com.simalee.nocheats.module.data.model.IUserModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.OkHttpClient;

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

        initOkhttp();

        initViews();

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

        text_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutCode.setErrorEnabled(false);
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
                if (validateInput(account,passwd,pcode)){
                    //存在错误 需要修复
                    //mRegisterPresenter.register(account,passwd,passwd);
                    register(account,passwd,pcode);
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
    public void showRegisterFailed(Response reason) {

        if (reason.getType() == Response.TYPE_ACCOUNT_EXIST){
            textInputLayoutAccount.setError(reason.getError());
        }else if (reason.getType() == Response.TYPE_WRONG_PCODE){
            textInputLayoutCode.setError(reason.getError());
        }else if (reason.getType() == Response.TYPE_SYSTEM_ERROR){
            Toast.makeText(this,reason.getError(),Toast.LENGTH_SHORT).show();
        }

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
    public void toNextStep(String id) {
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this,RegisterStep2Activity.class);
        intent.putExtra("id",id);
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

    private boolean validateInput(String account,String passwd,String pcode){
        if (!validateAccount(account)){
            textInputLayoutAccount.setError("账号格式错误");
            return false;
        }else if(!validatePasswd(passwd)){
            textInputLayoutPasswd.setError("密码由6-20位字母或数字组成！");
            return false;
        }else if (!validatePCode(pcode)){
            textInputLayoutCode.setError("请输入验证码");
            return false;
        }else {
            return true;
        }

    }

    /**
     * 账号输入限制
     * @param account
     * @return
     */
    private boolean validateAccount(String account){
        boolean valid = false;

        if (account == null){
            valid = false;
        }else {
            String regExp = "^[0-9]{11}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(account);
            valid = m.matches();
            LogUtils.d(TAG,"account: "+ account);
            LogUtils.d(TAG,"account: match? "+ valid);
        }
        return valid;
    }

    private boolean validatePasswd(String password){

        boolean valid = false;

        if (password == null){
            valid = false;
        }else {
            String regExp = "^(\\w){6,20}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(password);
            valid = m.matches();
            LogUtils.d(TAG,"password: "+ password);
            LogUtils.d(TAG,"password: match? "+ valid);
        }
        return valid;
    }

    private boolean validatePCode(String pcode){

        if (pcode == null || pcode.length() == 0){
            return false;
        }
        return true;

    }

    /**
     *
     * model 处的访问存在问题 暂时用此处的替代
     * @param account
     * @param passwd
     * @param code
     */
    public void register(String account, String passwd, String code) {
        //TODO 接入网络操作
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
                                String userId = jsonObject.getString("id");
                                //String userId = "123";
                                toNextStep(userId);

                            }else if ("1".equals(msg)){
                                failureReason.setType(Response.TYPE_WRONG_PCODE);
                                failureReason.setError("图片验证码错误");
                                showRegisterFailed(failureReason);

                            }else if ("2".equals(msg)){
                                failureReason.setType(Response.TYPE_ACCOUNT_EXIST);
                                failureReason.setError("该手机号已被使用");
                                showRegisterFailed(failureReason);
                            }
                            else if ("3".equals(msg)){
                                failureReason.setType(Response.TYPE_SYSTEM_ERROR);
                                failureReason.setError("系统正在维护升级，暂停服务");
                                showRegisterFailed(failureReason);

                            }else if ("4".equals(msg)){
                                //do nothing
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



}
