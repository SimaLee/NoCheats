package com.simalee.nocheats.module.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;

/**
 * Created by Lee Sima on 2017/6/23.
 */

public class RegisterStep3Activity extends BaseActivity {

    private static final String TAG = RegisterStep3Activity.class.getSimpleName();

    TextView tv_to_login ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step_3);
        initViews();

    }

    private void initViews(){
        tv_to_login = (TextView) findViewById(R.id.tv_to_login);
        tv_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterStep3Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
