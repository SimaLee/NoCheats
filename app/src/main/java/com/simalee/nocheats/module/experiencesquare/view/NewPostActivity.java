package com.simalee.nocheats.module.experiencesquare.view;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.common.util.TakePhotoPickPhotoUtils;
import com.simalee.nocheats.module.experiencesquare.contract.PostsContract;
import com.simalee.nocheats.module.experiencesquare.presenter.PostsPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.angmarch.views.NiceSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Lee Sima on 2017/6/26.
 */

public class NewPostActivity extends BaseActivity implements PostsContract.NewPostView{

    private static final String TAG = NewPostActivity.class.getSimpleName();

    PostsContract.Presenter mPostPresenter;


    private TextInputLayout text_input_layout_title;
    private TextInputLayout text_input_layout_content;

    private TextInputEditText text_input_title;
    private TextInputEditText text_input_content;
    private ImageView iv_add_photo;
    private ImageView iv_post_photo;
    private NiceSpinner mPostSpinner;
    private TextView tv_release_post;
    private TextView tv_back;

    LinkedList<String> postTypes;

    /*
    * 类别： 1 金融
    *       2 电信
    *       3 网络
    *       4 街头
    *       5 其他
    * */
    private static final int TYPE_FINANCE = 1;
    private static final int TYPE_TELECOM = 2;
    private static final int TYPE_INTERNET = 3;
    private static final int TYPE_STREET = 4;
    private static final int TYPE_OTHER = 5;


    /**
     * 用户选择的post类型 默认为1
     */
    int selectPostType = 1;

    private TakePhotoPickPhotoUtils mTakePhotoPickPhotoUtils;
    private File targetFile;

    StringBuffer postPhotoUrlBuffer = new StringBuffer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Slide slide = new Slide();
        slide.setDuration(180);
        getWindow().setEnterTransition(slide);

        Explode explode = new Explode();
        explode.setDuration(180);
        getWindow().setExitTransition(explode);


        int defaultPostType = getIntent().getIntExtra("type",1);
        LogUtils.d(TAG,"current page index is: " + defaultPostType);
        if (defaultPostType == 0 || defaultPostType == 1){
            selectPostType = 1;
        }else{
            selectPostType = defaultPostType;
        }
        // 注：-1 无意义
        mPostPresenter = new PostsPresenter(this,-1);

        mTakePhotoPickPhotoUtils = new TakePhotoPickPhotoUtils(this,500);

        initViews();

    }

    private void initViews(){

        mPostSpinner = (NiceSpinner) findViewById(R.id.spinner_post_type);
        String[] postTypeFromXml = getResources().getStringArray(R.array.post_type);
        postTypes = new LinkedList<>(Arrays.asList(postTypeFromXml));

        //List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        mPostSpinner.attachDataSource(postTypes);
        //为了用户体验 将当前的选择设置为进入时的类别
        mPostSpinner.setSelectedIndex(selectPostType - 1);
        mPostSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(TAG,"当前类别:"+ postTypes.get(position) + ":"+ position);
                selectPostType = position + 1;
            }
        });


        text_input_layout_title = (TextInputLayout) findViewById(R.id.text_input_layout_title);
        text_input_layout_content = (TextInputLayout) findViewById(R.id.text_input_layout_content);

        text_input_content = (TextInputEditText) findViewById(R.id.text_input_content);
        text_input_title = (TextInputEditText) findViewById(R.id.text_input_title);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_release_post = (TextView) findViewById(R.id.tv_release_post);

        iv_add_photo = (ImageView) findViewById(R.id.add_picture);
        iv_post_photo = (ImageView) findViewById(R.id.iv_post_photo);

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

        tv_release_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Snackbar.make(v,"点击了发布按钮",1000).show();
                releasePost();
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
        if (resultCode != NewPostActivity.RESULT_OK) {
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

        iv_post_photo.setImageURI(mTakePhotoPickPhotoUtils.getImageCropUri());
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
                                    if (postPhotoUrlBuffer.length() == 0){
                                        postPhotoUrlBuffer.append(picUrl);
                                    }else{
                                        postPhotoUrlBuffer.append(";");
                                        postPhotoUrlBuffer.append(picUrl);
                                    }
                                    LogUtils.d(TAG, "postPhotoUrlBuffer: " + postPhotoUrlBuffer.toString());

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
    public void setPresenter(PostsContract.Presenter presenter) {
        //do nothing
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

       Snackbar snackbar =  Snackbar.make(tv_release_post,"帖子发布成功，恭喜获得 3 点积分",2000).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPostActivity.this.finish();
            }
        });

        snackbar.setCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {

                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT){
                    NewPostActivity.this.finish();
                }
                super.onDismissed(transientBottomBar, event);
            }
        });
        snackbar.show();

    }

    @Override
    public void showPostReleasedFailed(String reason) {
        Snackbar.make(tv_release_post,"帖子发布失败:"+ reason,1000).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePost();
            }
        }).show();
    }

    /**
     *  发布帖子
     */
    private void releasePost(){
        String postTitle = text_input_title.getText().toString().trim();
        String postContent = text_input_content.getText().toString().trim();
        String userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);
        //TODO 图片
        String postPhotoUrl = postPhotoUrlBuffer.toString();
        LogUtils.d(TAG,"图片url:"+postPhotoUrl);
        if (validateInput(postTitle,postContent)){
            mPostPresenter.releasePost(userId,postTitle,String.valueOf(selectPostType),postContent,postPhotoUrl);
        }
    }

    /**
     * 验证输入是否有效
     * @param postTitle
     * @param postContent
     * @return
     */
    private boolean validateInput(String postTitle, String postContent) {
        LogUtils.d(TAG,"postContent :"+ postContent);
        if (postTitle == null || postTitle.length() == 0){
            text_input_layout_title.setError("标题不能为空");
            return false;
        }else if (postTitle.length() > 30){
            text_input_layout_title.setError("标题长度在1-30个字符之间");
            LogUtils.d(TAG,"postTitle >");
            return false;
        }else if (postContent == null || postContent.length() == 0){
            text_input_layout_content.setError("经历描述不能为空！");
            LogUtils.d(TAG,"postContent e " + postContent);
            return  false;
        }else if (postContent.length() > 1000){
            text_input_layout_content.setError("经历描述不能超过1000字!");
            LogUtils.d(TAG,"postContent > "+ postContent);
            return false;
        }else{
            return true;
        }
    }

}
