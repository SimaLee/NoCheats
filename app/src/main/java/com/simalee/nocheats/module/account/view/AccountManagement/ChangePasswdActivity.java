package com.simalee.nocheats.module.account.view.AccountManagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.callback.BitmapCallback;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.module.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Yang on 2017/7/2.
 */

public class ChangePasswdActivity extends BaseActivity implements View.OnClickListener{
    private final String TAG = "ChangePassedActivity";
    private TextInputLayout textInputLayoutPasswd;
    private TextInputLayout textInputLayoutPasswdAgain;
    private TextInputLayout textInputLayoutCode;

    private TextInputEditText text_passwd_again;
    private TextInputEditText text_passwd;
    private TextInputEditText text_code;
    private ImageView iv_code;
    private Button bt_changepasswd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        init();
        getPCode();
    }
    public void init(){
        textInputLayoutPasswd = (TextInputLayout)findViewById(R.id.text_layout_passwd);
        textInputLayoutPasswdAgain = (TextInputLayout)findViewById(R.id.text_layout_passwd_again);
        textInputLayoutCode =(TextInputLayout)findViewById(R.id.text_input_layout_pcode);
        text_passwd = (TextInputEditText)findViewById(R.id.text_input_passwd);
        text_passwd_again = (TextInputEditText)findViewById(R.id.text_input_passwd_again);
        text_code = (TextInputEditText)findViewById(R.id.text_input_code);
        iv_code = (ImageView)findViewById(R.id.iv_pcode);
        bt_changepasswd = (Button)findViewById(R.id.bt_change_passwd);

        iv_code.setOnClickListener(this);
        bt_changepasswd.setOnClickListener(this);
    }

    public void getPCode(){
        OkHttpUtils.post()
                .url(Constant.Url.URL_GET_VERIFY_CODE)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        iv_code.setImageBitmap(response);
                    }
                });
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

    private void changePasswd(){
        String passwd = text_passwd.getText().toString().trim();
        String passwdAgain = text_passwd_again.getText().toString().trim();
        String pCode = text_code.getText().toString().trim();
        if(passwd.equals(passwdAgain)){
            if(passwd.length()<6||passwd.length()>20){
                Toast.makeText(ChangePasswdActivity.this,"密码不得少于6位或多于20位",Toast.LENGTH_SHORT).show();
                return;
            }else{
                if(pCode.length()<4){
                    Toast.makeText(ChangePasswdActivity.this,"请输入完整图片验证码",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    LogUtils.d(TAG,"eee");
                    OkHttpUtils.post()
                            .url(Constant.Url.URL_CHANGE_USER_PASSWORD)
                            .addParams("id", PreferenceUtil.getString(ChangePasswdActivity.this,PreferenceUtil.USER_ID))
                            .addParams("pwd",passwd)
                            .addParams("pic_code",pCode)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    LogUtils.d(TAG, e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        LogUtils.d(TAG,response);
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("msg");
                                        if(msg.equals("0")){
                                            Toast.makeText(ChangePasswdActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ChangePasswdActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else if(msg.equals("1")){
                                            Toast.makeText(ChangePasswdActivity.this,"图片验证码错误",Toast.LENGTH_SHORT).show();
                                        }else if(msg.equals("2")){
                                            Toast.makeText(ChangePasswdActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                        }else if(msg.equals("3")){
                                            Toast.makeText(ChangePasswdActivity.this,"其他",Toast.LENGTH_SHORT).show();
                                        }else if(msg.equals("4")){
                                            Toast.makeText(ChangePasswdActivity.this,"session 无验证码",Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        LogUtils.d(TAG, e.toString());
                                    }
                                }
                            });
                }
            }
        }else{
            Toast.makeText(ChangePasswdActivity.this,"请确保两次密码一致",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_pcode:
                getPCode();
                break;
            case R.id.bt_change_passwd:
                changePasswd();
                break;
        }
    }
}
