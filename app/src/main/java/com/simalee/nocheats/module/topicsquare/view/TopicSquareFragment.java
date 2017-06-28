package com.simalee.nocheats.module.topicsquare.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.base.BaseFragment;
import com.simalee.nocheats.common.base.OnFabClickListener;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;
import com.simalee.nocheats.module.experiencesquare.view.DividerItemDecoration;
import com.simalee.nocheats.module.experiencesquare.view.PostAdapter;
import com.simalee.nocheats.module.experiencesquare.view.PostDetailActivity;

import java.util.ArrayList;


/**
 * Created by Lee Sima on 2017/6/15.
 */

public class TopicSquareFragment extends BaseFragment implements OnFabClickListener{
    private static final String TAG = TopicSquareFragment.class.getSimpleName();

    private Context mContext;

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private TopicAdapter mTopicAdapter;

    public static TopicSquareFragment newInstance(){
        Bundle args = new Bundle();
        TopicSquareFragment fragment = new TopicSquareFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_square,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRefreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);

        setupRecyclerView();
        setupRefreshLayout();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private void setupRecyclerView(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        //TODO 使用 presenter 获取数据填充.

        mTopicAdapter = new TopicAdapter(testData());
        mTopicAdapter.setRecyclerItemClickListener(new TopicAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, TopicEntity topicEntity) {
                Intent intent = new Intent(mContext,TopicDetailActivity.class);
                intent.putExtra("123",topicEntity.getTopicTitle());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mTopicAdapter);
    }

    private void setupRefreshLayout(){
        //设置顶部刷新view
        ProgressLayout header = new ProgressLayout(mContext);
        mRefreshLayout.setHeaderView(header);
        mRefreshLayout.setFloatRefresh(true);
        mRefreshLayout.setOverScrollTopShow(false);
        header.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        /*SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setTextColor(0xff745D5C);
        mSwipeRefreshLayout.setHeaderView(headerView);*/

        //设置底部刷新view
        LoadingView loadingView = new LoadingView(mContext);
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

    private ArrayList<TopicEntity> testData(){
        TopicEntity one = new TopicEntity();
        one.setUserName("李狗蛋");
        one.setPoint("3");
        one.setTopicTitle("社会主义好");
        one.setTopicContent("我是一条小青龙");
        one.setTopicType("叽叽叽叽");
        one.setTopicCommentCount(23);

        ArrayList<TopicEntity> data = new ArrayList<>();
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);

        return data;
    }

    @Override
    public void onFloatingActionButtonClick(View view) {
        showToastShort("发起新主题！");
    }

    private void showToastShort(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }


}
