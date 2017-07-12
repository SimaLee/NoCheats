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
import com.simalee.nocheats.common.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
                situations = getRandomList(situations);
                if (situations != null && situations.size() != 0) {
                    currentSituation = situations.get(0);
                    tv_situation.setText(currentSituation.getSituation());
                    if(!currentSituation.getChoiceOne().equals("")) {
                        ck_choice_one.setVisibility(View.VISIBLE);
                        ck_choice_one.setText(currentSituation.getChoiceOne());
                    }else{
                        LogUtils.i(TAG, "ck1 mei cun zai");
                        ck_choice_one.setVisibility(View.GONE);
                    }
                    if(!currentSituation.getChoiceTwo().equals("")) {
                        ck_choice_two.setVisibility(View.VISIBLE);
                        ck_choice_two.setText(currentSituation.getChoiceTwo());
                    }else{
                        ck_choice_two.setVisibility(View.GONE);
                    }
                    if(!currentSituation.getChoiceThree().equals("")) {
                        ck_choice_three.setVisibility(View.VISIBLE);
                        ck_choice_three.setText(currentSituation.getChoiceThree());
                    }else{
                        ck_choice_three.setVisibility(View.GONE);
                    }
                    if(!currentSituation.getChoiceFour().equals("")) {
                        ck_choice_four.setVisibility(View.VISIBLE);
                        ck_choice_four.setText(currentSituation.getChoiceFour());
                    }else{
                        ck_choice_four.setVisibility(View.GONE);
                    }

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
                    if(!currentSituation.getChoiceOne().equals("")) {
                        ck_choice_one.setVisibility(View.VISIBLE);
                        ck_choice_one.setText(currentSituation.getChoiceOne());
                    }else{
                        LogUtils.i(TAG, "ck1 mei cun zai");
                        ck_choice_one.setVisibility(View.GONE);
                    }
                    if(!currentSituation.getChoiceTwo().equals("")) {
                        ck_choice_two.setVisibility(View.VISIBLE);
                        ck_choice_two.setText(currentSituation.getChoiceTwo());
                    }else{
                        ck_choice_two.setVisibility(View.GONE);
                    }
                    if(!currentSituation.getChoiceThree().equals("")) {
                        ck_choice_three.setVisibility(View.VISIBLE);
                        ck_choice_three.setText(currentSituation.getChoiceThree());
                    }else{
                        ck_choice_three.setVisibility(View.GONE);
                    }
                    if(!currentSituation.getChoiceFour().equals("")) {
                        ck_choice_four.setVisibility(View.VISIBLE);
                        ck_choice_four.setText(currentSituation.getChoiceFour());
                    }else{
                        ck_choice_four.setVisibility(View.GONE);
                    }
                    choice = "";
                }else{
                    Toast.makeText(PreventCheatsGameActivity.this, "场景库出错", Toast.LENGTH_SHORT).show();
                }
            }else{
                HashMap<String, String> noNextStep = currentSituation.getNoNextSituMap();
                if(noNextStep!=null) {
                    tv.setVisibility(View.VISIBLE);
                    tv_advise.setVisibility(View.VISIBLE);
//                    Toast.makeText(PreventCheatsGameActivity.this, noNextStep.get(choice), Toast.LENGTH_SHORT).show();
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
        choice = "";
        if(currentSituationIndex%situations.size() >situations.size()-2) {
            situations = getRandomList(situations);
        }

            if (tv_advise.getVisibility() == View.VISIBLE) {
                tv_advise.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
            }
            ck_choice_one.setChecked(false);
            ck_choice_two.setChecked(false);
            ck_choice_three.setChecked(false);
            ck_choice_four.setChecked(false);
            currentSituation = situations.get(++currentSituationIndex%situations.size());
            if(currentSituation!=null){
                tv_situation.setText(currentSituation.getSituation());
                if(!currentSituation.getChoiceOne().equals("")) {
                    ck_choice_one.setVisibility(View.VISIBLE);
                    ck_choice_one.setText(currentSituation.getChoiceOne());
                }else{
                    LogUtils.i(TAG, "ck1 mei cun zai");
                    ck_choice_one.setVisibility(View.GONE);
                }
                if(!currentSituation.getChoiceTwo().equals("")) {
                    ck_choice_two.setVisibility(View.VISIBLE);
                    ck_choice_two.setText(currentSituation.getChoiceTwo());
                }else{
                    ck_choice_two.setVisibility(View.GONE);
                }
                if(!currentSituation.getChoiceThree().equals("")) {
                    ck_choice_three.setVisibility(View.VISIBLE);
                    ck_choice_three.setText(currentSituation.getChoiceThree());
                }else{
                    ck_choice_three.setVisibility(View.GONE);
                }
                if(!currentSituation.getChoiceFour().equals("")) {
                    ck_choice_four.setVisibility(View.VISIBLE);
                    ck_choice_four.setText(currentSituation.getChoiceFour());
                }else{
                    ck_choice_four.setVisibility(View.GONE);
                }
                choice = "";
                isNextSituation = false;
                tv_next_step.setText("下一步");
            }else{
                Toast.makeText(PreventCheatsGameActivity.this,
                        "场景库初始化错误", Toast.LENGTH_SHORT).show();
            }

//        else{
//            Toast.makeText(PreventCheatsGameActivity.this,
//                    "没有场景啦，请选择另一主题的场景小游戏",Toast.LENGTH_SHORT).show();
//        }
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

    private ArrayList<Situation> getRandomList(ArrayList<Situation> situationArrayList) {
        if(isEmpty(situationArrayList)){
            return situationArrayList;
        }

        ArrayList<Situation> randomList = new ArrayList<>(situationArrayList.size());
        do {
            int randomIndex = Math.abs(new Random().nextInt(situationArrayList.size()));
            randomList.add(situationArrayList.remove(randomIndex));
        } while (situationArrayList.size() > 0);
        return randomList;
    }

    private boolean isEmpty(ArrayList<Situation> situationArrayList) {
        return (situationArrayList == null || situationArrayList.size() == 0);
    }
    private void initGame(){
        Situation situation = new Situation("假如你是xxx的父亲，一天，你接到一个电话，你好，您是xxx的父亲吗，我是xxx的老师，他在这边遭遇车祸了，需要紧急做手术，急需一笔手术费，然而我现在暂时譸不出来这么多的资金，您可以打过来嘛，事关您儿子的性命，请尽快将资金打到我的银行卡账号"
                , "事关儿子生命，立刻打钱给他银行卡号", "打电话给自己的儿子确认此事", "让他说出儿子的特征，确认他是儿子的老师", "立马关掉电话");


        HashMap<String, String> noNextStep = new HashMap<>();
        noNextStep.put("A", "在没有确认对方身份和儿子是否生命危急之前这样的做法很可能会有被骗的危险，建议先确认儿子是否真的生命危急");
        noNextStep.put("B", "这样的做法很明智");
        noNextStep.put("C", "很好，但是万一对方是骗子，而且知道特征，建议还是先打给儿子，看能不能确认此事");
        noNextStep.put("D", "在确认孩子没有危险的时候，这样的做法是对的，因为已经确认他是骗子");
        situation.setNoNextSituMap(noNextStep);

        Situation situationA = new Situation("今天你在下班回家的路上看到一个小孩子一直哭，很可怜,然后就过去问那小朋友怎么了.小朋友就对你说:\"我迷路了,可以请你带我回家吗?\"然后拿一张纸条给你看,说那是他家地址.你会"
                , "带着小朋友去警察局找警察", "带着小朋友去他家", "不管他，转身就走", "");
        HashMap<String, String> noNextStepA = new HashMap<>();
        noNextStepA.put("A", "小朋友委婉的拒绝了你的帮助，开始寻找下一个目标…");
        noNextStepA.put("B", "你带着小朋友到那个所谓小孩子的家里以后，一按铃，门铃像是有高压电，你就失去知觉了。隔天醒来你身边什么都没有了,甚至连犯人长啥样子都没看见…");
        noNextStepA.put("C", "你拒绝了小朋友的请求，小朋友大声哭了起来，你在旁人指责的目光下默默的离开了…");
        situationA.setNoNextSituMap(noNextStepA);


        Situation situationC = new Situation("今天你在楼下的工行自动取款机取钱时，后面来了个老妇女，问你能不能取钱，还说好像取款机有个键可能坏了。这时，另一边不知什么时候来了一个可爱的小女孩，一直往你身边蹭，你会"
                , "对小女孩笑一笑，继续取钱", "将小女孩推开再取钱", "", "");
        Situation situationC1 = new Situation("当取款机弹出现金时，小女孩迅速的拿走了你的钱往远处跑开了，你会", "快速去追那个小女孩", "看着小女孩远去", "", "");
        HashMap<String, String> noNextStepC = new HashMap<>();
        noNextStepC.put("B", "你成功取到了现金，离开了这个是非之地…");
        situationC.setNoNextSituMap(noNextStepC);
        situationC.setChoice_one_situ(situationC1);
        HashMap<String, String> noNextStepC1 = new HashMap<>();
        noNextStepC1.put("A", "你抓住了小女孩，成功抢回她手上的现金，可回到取款机却发现卡里的金额都不翼而飞了…");
        noNextStepC1.put("B", "小女孩拿着你的钱逃之夭夭，你心疼的拿着银行卡离开了…");
        situationC1.setNoNextSituMap(noNextStepC1);


        Situation situationD = new Situation("大清早，你去网吧打游戏，坐得比较偏，没什么人。正嗨皮并且完全投入战斗的时候，有位大哥突然拍拍你，往地上指指，你看到几个硬币在地上，可能是你口袋浅掉出来的，于是你会"
                , "弯腰捡起地上的硬币", "无视硬币，继续打游戏", "先拿起桌面上的手机，同时捡起地上的硬币", "");

        HashMap<String, String> noNextStepD = new HashMap<>();
        noNextStepD.put("A", "当你捡起硬币坐直后，发现你桌上的手机不翼而飞了…qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        noNextStepD.put("B", "大哥捡走了硬币寻找下一关目标…");
        noNextStepD.put("C", "你成功获得几枚硬币，中午吃面时特地加了个蛋…");
        situationD.setNoNextSituMap(noNextStepD);

        ArrayList<Situation> situationArrayList = new ArrayList<>();
        situationArrayList.add(situation);
        situationArrayList.add(situationA);
        situationArrayList.add(situationC);
        situationArrayList.add(situationD);

        Game game = new Game(situationArrayList);
        for(int i = 0;i<4;i++)
        gameArrayList.add(game);
    }
}
