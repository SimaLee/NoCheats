package com.simalee.nocheats.module.topicsquare.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseActivity;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;
import com.simalee.nocheats.module.experiencesquare.view.DividerItemDecoration;
import com.simalee.nocheats.module.experiencesquare.view.PostAdapter;
import com.simalee.nocheats.module.topicsquare.contract.PreviousTopicContract;
import com.simalee.nocheats.module.topicsquare.presenter.PreviousTopicPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/15.
 */

public class PreviousTopicActivity extends BaseActivity implements PreviousTopicContract.View{

    private static final String TAG = PreviousTopicActivity.class.getSimpleName();


    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private TextView tv_back;

    private TopicAdapter mTopicAdapter;

    private PreviousTopicContract.Presenter mPreviousTopicPresenter;

    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_topic);

        userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);

        mPreviousTopicPresenter = new PreviousTopicPresenter(this);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreviousTopicPresenter != null){
            mPreviousTopicPresenter.loadMyTopics(userId);
        }
    }

    private void initViews() {

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_back = (TextView) findViewById(R.id.tv_back);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTopicAdapter = new TopicAdapter(this,new ArrayList<TopicEntity>(0));

        mTopicAdapter.setRecyclerItemClickListener(new TopicAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, TopicEntity topicEntity) {
                LogUtils.d(TAG,"onItemClick:" + topicEntity.toString());
               mPreviousTopicPresenter.openTopicDetails(topicEntity);
            }
        });

        mRecyclerView.setAdapter(mTopicAdapter);

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
                LogUtils.d(TAG,"加载更多！");
                TopicEntity lastTopic = mTopicAdapter.getLastEntity();

                if (mPreviousTopicPresenter != null){
                    mPreviousTopicPresenter.loadMoreMyTopics(userId, lastTopic.getTopicTime());
                }
            }

            @Override
            public void onLoadmoreCanceled() {
                LogUtils.d(TAG,"取消加载");
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(TAG,"下拉刷新！");
                if (mPreviousTopicPresenter != null){
                    mPreviousTopicPresenter.loadMyTopics(userId);
                }
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }


    @Override
    public void showTopics(List<TopicEntity> topicEntities) {
        mTopicAdapter.replaceData(topicEntities);
    }

    @Override
    public void showLoadMoreTopics(List<TopicEntity> appendTopicEntities) {
        mTopicAdapter.appendData(appendTopicEntities);
    }

    @Override
    public void showTopicDetail(String topicId, String topicTime, String topicTitle) {
        Intent intent = new Intent(this,TopicDetailActivity.class);
        intent.putExtra("topicId",topicId);
        intent.putExtra("topicTime",topicTime);
        intent.putExtra("topicTitle",topicTitle);
        startActivity(intent);
    }

    @Override
    public void showNoTopic() {
        showToastShort("当前还没有数据哦！");
    }

    @Override
    public void showLoadingProgress() {
        mRefreshLayout.startRefresh();
    }

    @Override
    public void hideLoadingProgress() {
        mRefreshLayout.finishRefreshing();
    }

    @Override
    public void showLoadingMoreProgress() {
        mRefreshLayout.startLoadMore();
    }

    @Override
    public void hideLoadingMoreProgress() {
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void showLoadingFailure() {
        Snackbar.make(mRefreshLayout,"加载失败",2000).show();
    }

    @Override
    public void setPresenter(PreviousTopicContract.Presenter presenter) {
        //do nothing
    }

    private void showToastShort(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
