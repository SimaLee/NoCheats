package com.simalee.nocheats.module.experiencesquare.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.common.util.TakePhotoPickPhotoUtils;
import com.simalee.nocheats.module.experiencesquare.contract.PostsContract;
import com.simalee.nocheats.module.experiencesquare.presenter.PostsPresenter;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // 注：-1 无意义
        mPostPresenter = new PostsPresenter(this,-1);

        mTakePhotoPickPhotoUtils = new TakePhotoPickPhotoUtils(this,500);

        initViews();
    }

    private void initViews(){

        mPostSpinner = (NiceSpinner) findViewById(R.id.spinner_post_type);
        String[] postTypeFromXml = getResources().getStringArray(R.array.post_type);
        postTypes = new LinkedList<>(Arrays.asList(postTypeFromXml));
        LogUtils.d(TAG,"postType :"+ postTypes.size());
        //List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        mPostSpinner.attachDataSource(postTypes);

        mPostSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(TAG,"当前类别:"+ postTypes.get(position) + ":"+ position);
                selectPostType = position;
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

        tv_release_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Snackbar.make(v,"点击了发布按钮",1000).show();
                releasePost();
            }
        });
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
        String postPhotoUrl = "";

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
