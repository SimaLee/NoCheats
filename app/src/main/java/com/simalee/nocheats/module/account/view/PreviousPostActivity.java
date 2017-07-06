package com.simalee.nocheats.module.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.experiencesquare.view.DividerItemDecoration;
import com.simalee.nocheats.module.experiencesquare.view.PostAdapter;
import com.simalee.nocheats.module.experiencesquare.view.PostDetailActivity;

import java.util.ArrayList;

/**
 * Created by Lee Sima on 2017/6/15.
 */

public class PreviousPostActivity extends BaseActivity {

    private static final String TAG = PreviousPostActivity.class.getSimpleName();


    private RecyclerView mRecyclerView;

    private TwinklingRefreshLayout mRefreshLayout;
    private PostAdapter mPostAdapter;

    private TextView tv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_post);
        initViews();
    }

    private void initViews(){

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_back = (TextView) findViewById(R.id.tv_back);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPostAdapter = new PostAdapter(this,testData());
        mPostAdapter.setRecyclerItemClickListener(new PostAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, PostEntity postEntity) {
                Intent intent = new Intent(PreviousPostActivity.this,PostDetailActivity.class);
                intent.putExtra("postId",postEntity.getPostTitle());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mPostAdapter);

        setupRefreshLayout();

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        mRefreshLayout.setHeaderView(headerView);*/

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
                },5000);
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
                },5000);
                LogUtils.d(TAG,"下拉刷新");
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }

    private ArrayList<PostEntity> testData(){
        PostEntity one = new PostEntity();
        one.setUserName("德玛西亚");
        one.setPostTitle("大骗子");
        one.setPostType("网络诈骗");
        one.setPostContent("今天被骗了，好气啊");
        one.setPoint("150");
        one.setPostViewCount(12);
        ArrayList<PostEntity> data = new ArrayList<>();
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);

        return data;
    }
}
