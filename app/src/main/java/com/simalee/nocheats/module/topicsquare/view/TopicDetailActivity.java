package com.simalee.nocheats.module.topicsquare.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicCommentEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicCommentReplyEntity;
import com.simalee.nocheats.module.experiencesquare.view.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicDetailActivity extends BaseActivity {

    private static final String TAG = TopicDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private TextView tv_back;


    private TopicDetailAdapter mTopicDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        initViews();

        String title = getIntent().getStringExtra("123");
        LogUtils.d(TAG,title);
    }

    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_back = (TextView) findViewById(R.id.tv_back);
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);


        setupRecyclerView();
        setupRefreshLayout();

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void setupRecyclerView(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mTopicDetailAdapter = new TopicDetailAdapter(this,new ArrayList<ICommentEntity>(0));
        mRecyclerView.setAdapter(mTopicDetailAdapter);
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

   /* private ArrayList<ICommentEntity> testData(){

        ArrayList<ICommentEntity> data = new ArrayList<>();
        TopicCommentEntity one = new TopicCommentEntity();
        one.setUserName("赵日天2");
        one.setTopicTitle("我要开飞机");
        one.setTopicType("傻帽诈骗");
        one.setTopicContent("没什么好说的祝大家新年大吉");
        one.setPoint("2");
        data.add(one);

        TopicCommentReplyEntity comment = new TopicCommentReplyEntity();
        comment.setHost(true);
        comment.setCommentStorey(10);
        comment.setCommentUserName("勇者");
        comment.setCommentUserPoint("20");
        comment.setCommentContent("你瞅啥瞅你咋地再瞅试试试试就试试");

        ArrayList<ReplyReplyEntity> replyReplyEntities = new ArrayList<>();
        replyReplyEntities.add(new ReplyReplyEntity("勇者 回复 李狗蛋：你好"));
        replyReplyEntities.add(new ReplyReplyEntity("李狗蛋 楼主 回复 李狗蛋：你好2"));
        replyReplyEntities.add(new ReplyReplyEntity("勇者 回复 李狗蛋：你好3"));
        replyReplyEntities.add(new ReplyReplyEntity("勇者 回复 李狗蛋：你好4"));

        comment.setRepliesList(replyReplyEntities);


        TopicCommentReplyEntity comment2 = new TopicCommentReplyEntity();
        comment2.setHost(false);
        comment2.setCommentStorey(10);
        comment2.setCommentUserName("叶良辰");
        comment2.setCommentUserPoint("204");
        comment2.setCommentContent("你瞅啥瞅你咋地再瞅试试试试就试试");

        ArrayList<ReplyReplyEntity> replyReplyEntities2 = new ArrayList<>();
        replyReplyEntities2.add(new ReplyReplyEntity("叶良辰 回复 李狗蛋：你好"));
        replyReplyEntities2.add(new ReplyReplyEntity("李狗蛋 楼主 回复 叶良辰：你好2"));

        comment2.setRepliesList(replyReplyEntities2);

        data.add(comment2);
        data.add(comment);
        data.add(comment);
        data.add(comment);
        data.add(comment2);

        return data;
    }*/

}
