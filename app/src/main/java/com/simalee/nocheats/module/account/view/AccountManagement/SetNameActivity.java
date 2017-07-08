package com.simalee.nocheats.module.account.view.AccountManagement;

import android.os.Bundle;
import android.util.Log;
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

public class SetNameActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SetNameActivity";

    private TextView tv_cancel;
    private TextView tv_finish;
    private EditText et_name;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setText(name);
        tv_finish.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

    }

    public void changeName(){
        if (et_name.getText().toString().trim().equals("")) {
            Toast.makeText(SetNameActivity.this,"请先输入你的名字", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_name.getText().toString().equals(name)) {
            this.finish();
            return;
        }
        String id =  PreferenceUtil.getString(SetNameActivity.this, PreferenceUtil.USER_ID);
        if(!id.equals("")){
            OkHttpUtils.post()
                    .url(Constant.Url.URL_EDIT_USER_NAME)
                    .addParams("u_id",id)
                    .addParams("u_name", et_name.getText().toString().trim())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            LogUtils.d(TAG, e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                Log.i(TAG, response);
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                if (msg.equals("0")) {
                                    EventBus.getDefault().post(new EditPersonalInfoEvent(2,et_name.getText().toString().trim()));
                                    Toast.makeText(SetNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    SetNameActivity.this.finish();
                                }else {
                                    Toast.makeText(SetNameActivity.this, "由于系统原因，修改失败", Toast.LENGTH_SHORT).show();
                                    et_name.setText("");
                                }
                            } catch (JSONException je) {
                                je.printStackTrace();
                                LogUtils.d(TAG,je.toString());
                            }
                        }
                    });
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                this.finish();
                break;
            case R.id.tv_finish:
                changeName();
                break;
        }
    }
}
