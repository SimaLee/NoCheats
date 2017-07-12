package com.simalee.nocheats.module.topicsquare.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.common.util.TakePhotoPickPhotoUtils;
import com.simalee.nocheats.module.experiencesquare.view.NewPostActivity;
import com.simalee.nocheats.module.topicsquare.contract.TopicsContract;
import com.simalee.nocheats.module.topicsquare.presenter.TopicPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

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

    private TakePhotoPickPhotoUtils mTakePhotoPickPhotoUtils;
    private File targetFile;

    StringBuffer topicPhotoUrlBuffer = new StringBuffer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);

        Slide slide = new Slide();
        slide.setDuration(180);
        getWindow().setEnterTransition(slide);

        Explode explode = new Explode();
        explode.setDuration(180);
        getWindow().setExitTransition(explode);

        mTopicPresenter = new TopicPresenter(this);

        mTakePhotoPickPhotoUtils = new TakePhotoPickPhotoUtils(this,500);

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


        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_release_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseTopic();
            }
        });

        iv_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }

    /**
     * 弹出选择图片界面
     */
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.popupwindow_took_photo, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置点击空白地方消失
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置空白地方的背景色
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.6f;
        getWindow().setAttributes(lp);
        //设置popupWindow消失的时候做的事情 即把背景色恢复
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
//        mPopWindow.setAnimationStyle(R.style.AnimationPreview);
        final Button bt_took_photo = (Button) contentView.findViewById(R.id.bt_delete_from_group);
        Button bt_cancel = (Button) contentView.findViewById(R.id.bt_cancel);
        Button bt_pick_photo = (Button) contentView.findViewById(R.id.bt_delete_student);
        bt_took_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                startActivityForResult(mTakePhotoPickPhotoUtils.takePhoto(), Constant.CODE.PICK_FROM_CAMERA);
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });
        bt_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
                startActivityForResult(mTakePhotoPickPhotoUtils.pickPhoto(), Constant.CODE.PICK_FROM_FILE);

            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(this)
                .inflate(R.layout.activity_account_management, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != NewTopicActivity.RESULT_OK) {
            LogUtils.d(TAG,"result not ok");
            return;
        }else {
            switch (requestCode) {
                case Constant.CODE.PICK_FROM_FILE:
                    Uri uriSelectInPhone = data.getData();
                    Intent b = mTakePhotoPickPhotoUtils.startPhotoZoom(uriSelectInPhone);
                    startActivityForResult(b, Constant.CODE.ACTION_CROP);
                    break;
                case Constant.CODE.PICK_FROM_CAMERA:
                    LogUtils.i(TAG, 222 + "");
                    Intent a = mTakePhotoPickPhotoUtils.startPhotoZoom(mTakePhotoPickPhotoUtils.getImageCaptureUri());
                    startActivityForResult(a, Constant.CODE.ACTION_CROP);
                    break;
                case Constant.CODE.ACTION_CROP:
                    postFile();
                    break;

            }
        }
    }

    private void postFile(){

        iv_topic_photo.setImageURI(mTakePhotoPickPhotoUtils.getImageCropUri());
        iv_add_photo.setVisibility(View.GONE);

        targetFile = new File(mTakePhotoPickPhotoUtils.getImageCropUri().getPath().toString());
        LogUtils.i(TAG, mTakePhotoPickPhotoUtils.getImageCropUri().getPath().toString());
        if (targetFile.exists()){
            OkHttpUtils.post()
                    .url(Constant.Url.URL_UPLOAD_POST_PIC)
                    .addFile("file", targetFile.getName(), targetFile)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            LogUtils.e(TAG,"error: " + e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                if ("0".equals(msg)){
                                    String picUrl = jsonObject.getString("id");
                                    LogUtils.d(TAG, "postPic url: " + picUrl);
                                    if (topicPhotoUrlBuffer.length() == 0){
                                        topicPhotoUrlBuffer.append(picUrl);
                                    }else{
                                        topicPhotoUrlBuffer.append(";");
                                        topicPhotoUrlBuffer.append(picUrl);
                                    }
                                    LogUtils.d(TAG, "topicPhotoUrlBuffer: " + topicPhotoUrlBuffer.toString());

                                }else{
                                    LogUtils.d(TAG,"上传帖子图片失败！");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
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

        EventBus.getDefault().post(new TopicReleaseEvent());

        Snackbar snackbar =  Snackbar.make(tv_release_topic,"话题发布成功，获得 3 点积分",2000).setAction("确定", new View.OnClickListener() {
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
        String topicPhotoUrl = topicPhotoUrlBuffer.toString();

        LogUtils.d(TAG,"图片url:"+topicPhotoUrl);

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
