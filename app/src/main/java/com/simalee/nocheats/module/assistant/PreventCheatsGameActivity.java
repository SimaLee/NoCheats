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

import java.util.ArrayList;
import java.util.HashMap;

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
    private TextView tv_advise;
    private TextView tv_next_step;
    private TextView tv;
    private String choice = "";
    private Game currentGame;
    private ArrayList<Situation> situations = new ArrayList<>();
    private Situation currentSituation;
    private int currentGameIndex = 0;
    private int currentSituationIndex = 0;
    private boolean isNextSituation = false;

    private ArrayList<Game> gameArrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevent_cheats_game);
        initView();
        setListener();
        initGame();
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
        tv_advise = (TextView) findViewById(R.id.tv_advise);
        tv = (TextView) findViewById(R.id.tv);
        rl_start.setVisibility(View.VISIBLE);
        rl_game_content.setVisibility(View.GONE);
        tv_advise.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
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
        if(gameArrayList.size() !=0) {
            currentGame = gameArrayList.get(currentGameIndex);
            if(currentGame!=null) {
                 situations = currentGame.getSituationArrayList();
                if (situations != null && situations.size() != 0) {
                    currentSituation = situations.get(0);
                    tv_situation.setText(currentSituation.getSituation());
                    ck_choice_one.setText(currentSituation.getChoiceOne());
                    ck_choice_two.setText(currentSituation.getChoiceTwo());
                    ck_choice_three.setText(currentSituation.getChoiceThree());
                    ck_choice_four.setText(currentSituation.getChoiceFour());
                }
            }
        }
    }

    /**
     * 当前场景点击下一步
     */
    private void next_step(){
        if(choice!="") {
            //判定是不是到了最后一个情景
            if (currentSituation.hasNextStepForTheChoice(choice)) {
                ck_choice_one.setChecked(false);
                ck_choice_two.setChecked(false);
                ck_choice_three.setChecked(false);
                ck_choice_four.setChecked(false);
                currentSituation = currentSituation.getNextSituationForTheChoice(choice);
                if (currentSituation != null) {
                    tv_situation.setText(currentSituation.getSituation());
                    ck_choice_one.setText(currentSituation.getChoiceOne());
                    ck_choice_two.setText(currentSituation.getChoiceTwo());
                    ck_choice_three.setText(currentSituation.getChoiceThree());
                    ck_choice_four.setText(currentSituation.getChoiceFour());
                    choice = "";
                }else{
                    Toast.makeText(PreventCheatsGameActivity.this, "场景库出错", Toast.LENGTH_SHORT).show();
                }
            }else{
                HashMap<String, String> noNextStep = currentSituation.getNoNextSituMap();
                if(noNextStep!=null) {
                    tv.setVisibility(View.VISIBLE);
                    tv_advise.setVisibility(View.VISIBLE);
                    Toast.makeText(PreventCheatsGameActivity.this, noNextStep.get(choice), Toast.LENGTH_SHORT).show();
                    tv_advise.setText(noNextStep.get(choice));
                }else{
                    Toast.makeText(PreventCheatsGameActivity.this, "并没有设置相对应的建议", Toast.LENGTH_SHORT).show();
                }
                isNextSituation = true;
                tv_next_step.setText("下一个场景");
            }

        } else {
            Toast.makeText(PreventCheatsGameActivity.this,
                        "请先选择一个选项，再点击下一步", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 下一个场景
     */
    public void nextSituation() {
        if(currentSituationIndex <=situations.size()-2) {
            if (tv_advise.getVisibility() == View.VISIBLE) {
                tv_advise.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
            }
            ck_choice_one.setChecked(false);
            ck_choice_two.setChecked(false);
            ck_choice_three.setChecked(false);
            ck_choice_four.setChecked(false);
            currentSituation = situations.get(++currentSituationIndex);
            if(currentSituation!=null){
                tv_situation.setText(currentSituation.getSituation());
                ck_choice_one.setText(currentSituation.getChoiceOne());
                ck_choice_two.setText(currentSituation.getChoiceTwo());
                ck_choice_three.setText(currentSituation.getChoiceThree());
                ck_choice_four.setText(currentSituation.getChoiceFour());
                choice = "";
                isNextSituation = false;
                tv_next_step.setText("下一步");
            }else{
                Toast.makeText(PreventCheatsGameActivity.this,
                        "场景库初始化错误", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(PreventCheatsGameActivity.this,
                    "没有场景啦，请选择另一主题的场景小游戏",Toast.LENGTH_SHORT).show();
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
                if(!isNextSituation) {
                    next_step();
                }else{
                   nextSituation();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initGame(){
        Situation situation = new Situation("假如你是xxx的父亲，一天，你接到一个电话，你好，您是xxx的父亲吗，我是xxx的老师，他在这边遭遇车祸了，需要紧急做手术，急需一笔手术费，然而我现在暂时譸不出来这么多的资金，您可以打过来嘛，事关您儿子的生命，请尽快"
                , "事关儿子生命，立刻打钱给他银行卡号", "打电话给自己的儿子确认此事", "让他说出儿子的特征，确认他是儿子的老师", "立马关掉电话");

        Situation situationA = new Situation("选择A之后的situation"
                , "A.事关儿子生命，立刻打钱给他银行卡号", "B.打电话给自己的儿子确认此事", "C.让他说出儿子的特征，确认他是儿子的老师", "D.立马关掉电话");
        HashMap<String, String> noNextStep = new HashMap<>();
        noNextStep.put("A", "在没有确认对方身份和儿子是否生命危急之前这样的做法很可能会有被骗的危险，建议先确认儿子是否真的生命危急");
        noNextStep.put("B", "这样的做法很明智");
        noNextStep.put("C", "很好，但是万一对方是骗子，而且知道特征，建议还是先打给儿子，看能不能确认此事");
        noNextStep.put("D", "在确认孩子没有危险的时候，这样的做法是对的，因为已经确认他是");
        situationA.setNoNextSituMap(noNextStep);
        Situation situationB = new Situation("选择B之后的situation"
                , "A.事关儿子生命，立刻打钱给他银行卡号", "B.打电话给自己的儿子确认此事", "C.让他说出儿子的特征，确认他是儿子的老师", "D.立马关掉电话");
        situationB.setNoNextSituMap(noNextStep);
        Situation situationC = new Situation("选择C之后的situation"
                , "A.事关儿子生命，立刻打钱给他银行卡号", "B.打电话给自己的儿子确认此事", "C.让他说出儿子的特征，确认他是儿子的老师", "D.立马关掉电话");
        situationC.setNoNextSituMap(noNextStep);
        Situation situationD = new Situation("选择D之后的situation"
                , "A.事关儿子生命，立刻打钱给他银行卡号", "B.打电话给自己的儿子确认此事", "C.让他说出儿子的特征，确认他是儿子的老师", "D.立马关掉电话");
        situationD.setChoice_one_situ(situationA);
        situationD.setChoice_two_situ(situationB);
        situationD.setChoice_three_situ(situationC);
        situationD.setChoice_four_situ(situationD);

        situation.setChoice_one_situ(situationA);
        situation.setChoice_two_situ(situationB);
        situation.setChoice_three_situ(situationC);
        situation.setChoice_four_situ(situationA);

        ArrayList<Situation> situationArrayList = new ArrayList<>();

        for(int i =0;i<7;i++)
        situationArrayList.add(situation);
        Game game = new Game(situationArrayList);
        for(int i = 0;i<4;i++)
        gameArrayList.add(game);
    }
}
