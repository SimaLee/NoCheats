package com.simalee.nocheats.module.assistant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
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
 * Created by Yang on 2017/7/9.
 */

public class PreventCheatsTipsActivity extends BaseActivity {
    private final String TAG = "PreventCheatsTips";

    private ImageView iv_back;
    private TextView tv_tip_content;
    private FloatingActionButton fab_switch;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_cheats_tips);
        initView();
        setListener();
        getTips();
    }

    public void getTips() {
        OkHttpUtils.post()
                .url(Constant.Url.URL_CHECK_TIPS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.i(TAG, "tips: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            String msg = jsonObject.getString("msg");
//                            if (msg.equals("0")) {
                                String tips_content = jsonObject.getString("content");
                                tv_tip_content.setText(tips_content);
//                            }else{
//                                Toast.makeText(PreventCheatsTipsActivity.this, "获取防骗小技巧失败", Toast.LENGTH_SHORT).show();
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_tip_content = (TextView) findViewById(R.id.tv_tips_content);
        fab_switch = (FloatingActionButton) findViewById(R.id.fab_switch);
    }

    private void setListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTips();
            }
        });

    }
}
