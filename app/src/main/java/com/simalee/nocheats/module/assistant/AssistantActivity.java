package com.simalee.nocheats.module.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;

/**
 * Created by Lee Sima on 2017/6/14.
 */

public class AssistantActivity extends BaseActivity {


    private static final String TAG = AssistantActivity.class.getSimpleName();

    private TextView btnTips;
    private TextView btnGame;
    private TextView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        initViews();
    }


    protected void initViews(){
        btnTips = (TextView) findViewById(R.id.tv_tips);
        btnGame = (TextView) findViewById(R.id.tv_game);
        btnBack = (TextView) findViewById(R.id.tv_back);

        btnTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistantActivity.this, PreventCheatsTipsActivity.class);
                startActivity(intent);
            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssistantActivity.this,PreventCheatsGameActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
