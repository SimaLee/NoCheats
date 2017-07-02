package com.simalee.nocheats.module.account.view.AccountManagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class SetSexActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG="SetSexActivity";
    private TextView tv_cancel;
    private TextView tv_finish;
    private RelativeLayout rl_man;
    private RelativeLayout rl_woman;
    private ImageView arrow_man;
    private ImageView arrow_woman;

    private String sex = "0";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sex);
        Bundle bundle = getIntent().getExtras();
        sex = bundle.getString("sex");

        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        rl_man = (RelativeLayout) findViewById(R.id.rl_man);
        rl_woman = (RelativeLayout) findViewById(R.id.rl_woman);
        arrow_man = (ImageView) findViewById(R.id.right_arrow1);
        arrow_woman = (ImageView) findViewById(R.id.right_arrow2);

        if (sex.equals("1")) {
            arrow_woman.setVisibility(View.GONE);
            arrow_man.setVisibility(View.VISIBLE);
        }else if(sex.equals("2")){
            arrow_man.setVisibility(View.GONE);
            arrow_woman.setVisibility(View.VISIBLE);
        }else{
            arrow_man.setVisibility(View.GONE);
            arrow_woman.setVisibility(View.GONE);
        }
        rl_man.setOnClickListener(this);
        rl_woman.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                this.finish();
                break;
            case R.id.tv_finish:
                OkHttpUtils.post()
                        .url(Constant.Url.URL_EDIT_USER_GENDER)
                        .addParams("u_id", PreferenceUtil.getString(SetSexActivity.this, PreferenceUtil.USER_ID))
                        .addParams("gender", sex)
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
                                        String message = "";
                                        if (sex.equals("1")) {
                                             message = "男";
                                        }else if(sex.equals("2")){
                                            message = "女";
                                        }else{
                                            message = "未设置";
                                        }
                                        EventBus.getDefault().post(new EditPersonalInfoEvent(3,message));
                                        Toast.makeText(SetSexActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        SetSexActivity.this.finish();
                                    }else {
                                        Toast.makeText(SetSexActivity.this, "由于系统原因，修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException je) {
                                    je.printStackTrace();
                                }
                            }
                        });
                this.finish();
                break;
            case R.id.rl_man:
                sex = "1";
                arrow_woman.setVisibility(View.GONE);
                arrow_man.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_woman:
                sex = "2";
                arrow_man.setVisibility(View.GONE);
                arrow_woman.setVisibility(View.VISIBLE);
                break;

        }
    }
}
