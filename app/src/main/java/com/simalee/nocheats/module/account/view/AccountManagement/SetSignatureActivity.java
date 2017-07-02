package com.simalee.nocheats.module.account.view.AccountManagement;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


import okhttp3.Call;

/**
 * Created by Yang on 2016/12/31.
 */

public class SetSignatureActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SetSignatureActivity";

    private TextView tv_cancel;
    private TextView tv_finish;
    private EditText et_signature;
    private TextView tv_tips;

    private String url ;
    private String parmas1;
    private String parmas2;
    private String signature;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_signature);
        Bundle bundle = getIntent().getExtras();
        signature = bundle.getString("signature");

        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        et_signature = (EditText) findViewById(R.id.et_signature);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        if (signature .equals("null")) {
            et_signature.setText("未设置");
        }else {
            et_signature.setText(signature);
        }
        tv_tips.setText(et_signature.length() + "/25");
        tv_finish.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        et_signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = et_signature.length();
                tv_tips.setText(length+"/25");
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                this.finish();
                break;
            case R.id.tv_finish:
                if (et_signature.getText().toString().trim().equals("")) {
                    Toast.makeText(SetSignatureActivity.this,"请先输入你的签名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_signature.equals(signature)) {
                    this.finish();
                    return;
                }
                String id =  PreferenceUtil.getString(SetSignatureActivity.this, PreferenceUtil.USER_ID);
                if(!id.equals("")) {
                    OkHttpUtils.post()
                            .url(Constant.Url.URL_EDIT_USER_SIGN)
                            .addParams("u_id", id)
                                .addParams("sign", et_signature.getText().toString().trim())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    LogUtils.d(TAG, e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("msg");
                                        if (msg.equals("0")) {
                                            EventBus.getDefault().post(new EditPersonalInfoEvent(4,et_signature.getText().toString().trim()));
                                            Toast.makeText(SetSignatureActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            SetSignatureActivity.this.finish();
                                        } else {
                                            Toast.makeText(SetSignatureActivity.this, "由于系统原因，修改失败", Toast.LENGTH_SHORT).show();
                                            et_signature.setText("");
                                        }
                                    } catch (JSONException je) {
                                        je.printStackTrace();
                                        LogUtils.d(TAG, je.toString());
                                    }
                                }
                            });
                }
                break;
        }
    }
}

