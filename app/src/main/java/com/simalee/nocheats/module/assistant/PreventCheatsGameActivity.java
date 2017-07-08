package com.simalee.nocheats.module.assistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigmercu.cBox.CheckBox;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;

/**
 * Created by Yang on 2017/7/3.
 */

public class PreventCheatsGameActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "PreventCheatsGameActivity";
    private ImageView iv_back;
    private TextView tv_tips;
    private TextView tv_start_game;
    private RelativeLayout rl_start;
    private RelativeLayout rl_game_content;
    private TextView tv_situation;
    private CheckBox ck_choice_one;
    private CheckBox ck_choice_two;
    private CheckBox ck_choice_three;
    private CheckBox ck_choice_four;
    private TextView tv_next_step;
    private String choice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_cheats_game);
        initView();
        setListener();
    }
    private void initView(){
        rl_start = (RelativeLayout)findViewById(R.id.rl_game_start);
        rl_game_content = (RelativeLayout)findViewById(R.id.rl_game_content);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_situation = (TextView)findViewById(R.id.tv_situation);
        ck_choice_one = (CheckBox)findViewById(R.id.ck_choice_one);
        ck_choice_two = (CheckBox)findViewById(R.id.ck_choice_two);
        ck_choice_three = (CheckBox)findViewById(R.id.ck_choice_three);
        ck_choice_four = (CheckBox)findViewById(R.id.ck_choice_four);
        tv_next_step = (TextView)findViewById(R.id.tv_next_step);
        tv_start_game = (TextView)findViewById(R.id.tv_start_game);
        iv_back =(ImageView)findViewById(R.id.iv_back);
        rl_start.setVisibility(View.VISIBLE);
        rl_game_content.setVisibility(View.GONE);
    }

    private void setListener()
    {
        tv_start_game.setOnClickListener(this);
        tv_next_step.setOnClickListener(this);
        ck_choice_one.setOnClickListener(this);
        ck_choice_two.setOnClickListener(this);
        ck_choice_three.setOnClickListener(this);
        ck_choice_four.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }    /**
     * 开始游戏
     */
    private void show_game_content(){
        rl_start.setVisibility(View.GONE);
        rl_game_content.setVisibility(View.VISIBLE);
    }

    private void next_step(){
        if(choice!="") {
            tv_situation.setText("111111");
            ck_choice_one.setText("A");
            ck_choice_two.setText("B");
            ck_choice_three.setText("C");
            ck_choice_four.setText("D");
            choice = "";
            ck_choice_one.setChecked(false);
            ck_choice_two.setChecked(false);
            ck_choice_three.setChecked(false);
            ck_choice_four.setChecked(false);
        }else{
            Toast.makeText(PreventCheatsGameActivity.this,
                    "请先选择一个选项，再点击下一步",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_game:
                show_game_content();
                break;
            case R.id.ck_choice_one:
                choice = "A";
                ck_choice_one.setChecked(true);
                ck_choice_two.setChecked(false);
                ck_choice_three.setChecked(false);
                ck_choice_four.setChecked(false);
                break;
            case R.id.ck_choice_two:
                ck_choice_two.setChecked(true);
                ck_choice_one.setChecked(false);
                ck_choice_three.setChecked(false);
                ck_choice_four.setChecked(false);
                choice = "B";
                break;
            case R.id.ck_choice_three:
                ck_choice_three.setChecked(true);
                ck_choice_two.setChecked(false);
                ck_choice_one.setChecked(false);
                ck_choice_four.setChecked(false);
                choice = "C";
                break;
            case R.id.ck_choice_four:
                ck_choice_four.setChecked(true);
                ck_choice_two.setChecked(false);
                ck_choice_three.setChecked(false);
                ck_choice_one.setChecked(false);
                choice = "D";
                break;
            case R.id.tv_next_step:
                next_step();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
