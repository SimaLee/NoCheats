package com.simalee.nocheats.module.experiencesquare.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.util.LogUtils;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/26.
 */

public class NewPostActivity extends BaseActivity {

    private static final String TAG = NewPostActivity.class.getSimpleName();

    private TextInputEditText text_input_title;
    private TextInputEditText text_input_content;
    private ImageView iv_add_photo;
    private NiceSpinner mPostSpinner;
    private TextView tv_release_post;
    private TextView tv_back;

    LinkedList<String> postTypes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
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
            }
        });

        text_input_content = (TextInputEditText) findViewById(R.id.text_input_title);
        text_input_title = (TextInputEditText) findViewById(R.id.text_input_title);
        tv_back = (TextView) findViewById(R.id.tv_back);

        tv_release_post = (TextView) findViewById(R.id.tv_release_post);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_release_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"点击了发布按钮",1000).show();
            }
        });
    }
}
