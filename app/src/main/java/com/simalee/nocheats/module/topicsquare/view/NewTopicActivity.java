package com.simalee.nocheats.module.topicsquare.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.module.experiencesquare.view.NewPostActivity;
import com.simalee.nocheats.module.topicsquare.contract.TopicsContract;
import com.simalee.nocheats.module.topicsquare.presenter.TopicPresenter;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public class NewTopicActivity extends BaseActivity implements TopicsContract.NewTopicView{

    private static final String TAG = NewTopicActivity.class.getSimpleName();


    private TextInputLayout text_input_layout_title;
    private TextInputLayout text_input_layout_content;

    private TextInputEditText text_input_title;
    private TextInputEditText text_input_content;

    private ImageView iv_add_photo;
    private ImageView iv_topic_photo;

    private TextView tv_release_topic;
    private TextView tv_back;

    private TopicsContract.Presenter mTopicPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);

        mTopicPresenter = new TopicPresenter(this);

        initViews();
    }

    private void initViews() {

        text_input_layout_title = (TextInputLayout) findViewById(R.id.text_input_layout_title);
        text_input_layout_content = (TextInputLayout) findViewById(R.id.text_input_layout_content);

        text_input_title = (TextInputEditText) findViewById(R.id.text_input_title);
        text_input_content = (TextInputEditText) findViewById(R.id.text_input_content);

        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_release_topic = (TextView) findViewById(R.id.tv_release_topic);

        iv_add_photo = (ImageView) findViewById(R.id.add_picture);
        iv_topic_photo = (ImageView) findViewById(R.id.iv_topic_photo);

        text_input_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_input_layout_title.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        text_input_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text_input_layout_content.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        iv_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"点击了添加图片按钮",1000).show();
            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_release_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"点击了发布按钮",1000).show();
                releaseTopic();
            }
        });
    }

    @Override
    public void showLoadingProgress() {
        LogUtils.d(TAG,"正在上传");
    }

    @Override
    public void hideLoadingProgress() {
        LogUtils.d(TAG,"上传完毕");
    }

    @Override
    public void showPostReleasedSuccess() {
        Snackbar snackbar =  Snackbar.make(tv_release_topic,"主题发布成功，恭喜获得 3 点积分",2000).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTopicActivity.this.finish();
            }
        });

        snackbar.setCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {

                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT){
                    NewTopicActivity.this.finish();
                }
                super.onDismissed(transientBottomBar, event);
            }
        });
        snackbar.show();
    }

    @Override
    public void showPostReleasedFailed(String reason) {
        Snackbar.make(tv_release_topic,"帖子发布失败:"+ reason,1000).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseTopic();
            }
        }).show();
    }

    @Override
    public void setPresenter(TopicsContract.Presenter presenter) {
        //do nothing
    }

    /**
     * 发起主题讨论
     */
    private void releaseTopic() {
        String topicTitle = text_input_title.getText().toString().trim();
        String topicContent = text_input_content.getText().toString().trim();
        String userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);
        //TODO 图片发送
        String topicPhotoUrl = "";

        if (validateInput(topicTitle,topicContent)){
            mTopicPresenter.releaseTopic(userId,topicTitle,topicContent,topicPhotoUrl);
        }

    }

    /**
     * 验证输入是否有效
     * @param topicTitle
     * @param topicContent
     * @return
     */
    private boolean validateInput(String topicTitle, String topicContent) {
        LogUtils.d(TAG,"postContent :"+ topicContent);
        if (topicTitle == null || topicTitle.length() == 0){
            text_input_layout_title.setError("标题不能为空");
            return false;
        }else if (topicTitle.length() > 30){
            text_input_layout_title.setError("标题长度在1-30个字符之间");
            LogUtils.d(TAG,"topicTitle >");
            return false;
        }else if (topicContent == null || topicContent.length() == 0){
            text_input_layout_content.setError("主题详述不能为空！");
            LogUtils.d(TAG,"topicContent e " + topicContent);
            return  false;
        }else if (topicContent.length() > 1000){
            text_input_layout_content.setError("主题详述不能超过1000字!");
            LogUtils.d(TAG,"topicContent > "+ topicContent);
            return false;
        }else{
            return true;
        }
    }
}
