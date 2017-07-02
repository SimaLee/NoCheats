package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostCommentReplyEntity;
import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.post.PostCommentEntity;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostDetailActivity extends BaseActivity implements PostDetailAdapter.OnMoreReplyClickListener{
    private static final String TAG = PostDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private PostDetailAdapter mPostDetailAdapter;


    private RelativeLayout rl_comment;
    private EditText et_comment;
    private ImageView ib_send;

    TextView tv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_2);

        initViews();

        String title = getIntent().getStringExtra("123");
        LogUtils.d(TAG,title);
    }

    private void initViews(){


        tv_back = (TextView) findViewById(R.id.tv_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);

        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        rl_comment.setAlpha(0.8F);
        et_comment = (EditText) findViewById(R.id.et_comment);
        ib_send = (ImageView) findViewById(R.id.ib_send);

        setupRecyclerView();
        setupRefreshLayout();

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        et_comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    rl_comment.setBackgroundColor(getResources().getColor(R.color.white));
                }else{
                    rl_comment.setAlpha(0.7F);
                }
            }
        });
        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mPostDetailAdapter = new PostDetailAdapter(this,testData());
        mPostDetailAdapter.setOnMoreReplyClickListener(this);
        mRecyclerView.setAdapter(mPostDetailAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动完成
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    rl_comment.setAlpha(0.8F);
                }else{
                    rl_comment.setAlpha(0.1F);
                }
            }
        });
    }

    private void setupRefreshLayout(){
        //设置顶部刷新view
        ProgressLayout header = new ProgressLayout(this);
        mRefreshLayout.setHeaderView(header);
        mRefreshLayout.setFloatRefresh(true);
        mRefreshLayout.setOverScrollTopShow(false);
        header.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        /*SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setTextColor(0xff745D5C);
        mSwipeRefreshLayout.setHeaderView(headerView);*/

        //设置底部刷新view
        LoadingView loadingView = new LoadingView(this);
        mRefreshLayout.setBottomView(loadingView);

        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onFinishRefresh() {
                LogUtils.d(TAG,"finish refresh");
            }

            @Override
            public void onFinishLoadMore() {
                LogUtils.d(TAG,"finish load more");
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(TAG,"上拉加载更多");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }

            @Override
            public void onLoadmoreCanceled() {
                LogUtils.d(TAG,"取消加载");
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                },2000);
                LogUtils.d(TAG,"下拉刷新");
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }

    private List<ICommentEntity> testData(){
        ArrayList<ICommentEntity> data = new ArrayList<>();
        PostCommentEntity one = new PostCommentEntity();
        one.setUserName("赵日天");
        one.setPostTitle("我要开飞机");
        one.setPostType("傻帽诈骗");
        one.setPostContent("没什么好说的祝大家新年大吉");
        one.setPoint("2");

        data.add(one);

        PostCommentReplyEntity comment = new PostCommentReplyEntity();
        comment.setHost(true);
        comment.setCommentStorey(10);
        comment.setCommentUserName("勇者");
        comment.setCommentUserPoint("2");
        comment.setCommentContent("你瞅啥瞅你咋地再瞅试试试试就试试");

        ArrayList<ReplyReplyEntity> replyReplyEntities = new ArrayList<>();
        ReplyReplyEntity replyItem = new ReplyReplyEntity();
        replyItem.setSubjectName("勇者");
        replyItem.setHost(true);
        replyItem.setReply(true);
        replyItem.setObjectName("李狗蛋");
        replyItem.setContent("你好aaaaaaaaaaa");

        ReplyReplyEntity replyItem2 = new ReplyReplyEntity();
        replyItem2.setSubjectName("李狗蛋");
        replyItem2.setHost(false);
        replyItem2.setReply(false);
        replyItem2.setContent("我不好");

        replyReplyEntities.add(replyItem);
        replyReplyEntities.add(replyItem2);
        replyReplyEntities.add(replyItem);
        replyReplyEntities.add(replyItem2);

        comment.setRepliesList(replyReplyEntities);

        data.add(comment);
        data.add(comment);
        data.add(comment);

        return data;
    }

    @Override
    public void onMoreReplyClick(View v) {
        Intent intent = new Intent(this,ReplyDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
