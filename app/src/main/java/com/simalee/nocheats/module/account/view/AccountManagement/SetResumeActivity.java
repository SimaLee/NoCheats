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

import org.json.JSONException;
import org.json.JSONObject;


import okhttp3.Call;

/**
 * Created by Yang on 2016/12/31.
 */

public class SetResumeActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SetResumeActivity";

    private TextView tv_cancel;
    private TextView tv_finish;
    private EditText et_resume;
    private TextView tv_tips;

    private String url ;
    private String parmas1;
    private String parmas2;
    private String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_resume);
        Bundle bundle = getIntent().getExtras();
        info = bundle.getString("info");

        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        et_resume = (EditText) findViewById(R.id.et_resume);
        tv_tips = (TextView) findViewById(R.id.tv_tips);

        if (info.equals("null")) {
            et_resume.setText("未设置");
        } else {
            et_resume.setText(info);
        }
        tv_tips.setText(et_resume.length() + "/300");
        tv_finish.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        et_resume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_tips.setText(editable.length() + "/300");
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
                if (et_resume.getText().toString().trim().equals("")) {
                    Toast.makeText(SetResumeActivity.this,"请先输入你的个人说明", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_resume.getText().toString().equals(info)) {
                    this.finish();
                    return;
                }
                String id =  PreferenceUtil.getString(SetResumeActivity.this, PreferenceUtil.USER_ID);
                if(!id.equals("")) {
                    OkHttpUtils.post()
                            .url(Constant.Url.URL_EDIT_USER_INTRO)
                            .addParams("u_id", id)
                            .addParams("introduction", et_resume.getText().toString().trim())
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

                                            Toast.makeText(SetResumeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            SetResumeActivity.this.finish();
                                        } else {
                                            Toast.makeText(SetResumeActivity.this, "由于系统原因，修改失败", Toast.LENGTH_SHORT).show();
                                            et_resume.setText("");
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
