package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.experiencesquare.contract.PostsContract;
import com.simalee.nocheats.module.experiencesquare.presenter.PostsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostFragment extends BaseFragment implements PostsContract.AllPostsView {
    private static final String TAG = PostFragment.class.getSimpleName();

    private Context mContext;


    private static final String KEY_INDEX = "index";
    /**
     * 当前页面 Index
     */
    private int CURRENT_INDEX = 0;
    /**
     * 页面Index
     */
    public static final int PAGE_MAIN = 0;
    public static final int PAGE_FINANCE = 1;
    public static final int PAGE_TELECOM = 2;
    public static final int PAGE_INTERNET = 3;
    public static final int PAGE_STREET = 4;
    public static final int PAGE_OTHER = 5;


    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;
    private PostAdapter mPostAdapter;

    private  boolean shouldResume = true;

    private PostsContract.Presenter mPresenter;

    public static PostFragment newInstance(int pageIndex){
        Bundle args = new Bundle();
        PostFragment INSTANCE = new PostFragment();
        args.putInt(KEY_INDEX,pageIndex);
        LogUtils.d(TAG,"new instance current index: "+ pageIndex);
        INSTANCE.setArguments(args);
        return INSTANCE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            CURRENT_INDEX = savedInstanceState.getInt(KEY_INDEX,PAGE_MAIN);
        }else{

            CURRENT_INDEX = getArguments().getInt(KEY_INDEX,PAGE_MAIN);
        }

        LogUtils.d(TAG,"onCreate current page is ："+CURRENT_INDEX);
        //TODO 使用 presenter 获取数据填充.
        mPostAdapter = new PostAdapter(mContext,new ArrayList<PostEntity>(0));

        mPresenter = new PostsPresenter(this,CURRENT_INDEX);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX,CURRENT_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(TAG,"onCreateView current page is ："+CURRENT_INDEX);
        View rootView = inflater.inflate(R.layout.fragment_base,container,false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRefreshLayout = (TwinklingRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        setupRecyclerView();
        setupRefreshLayout();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        LogUtils.d(TAG,"onResume invoked");
        if (shouldResume){
            mPresenter.start();
            shouldResume = false;
        }

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

        mPostAdapter.setRecyclerItemClickListener(new PostAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position, PostEntity postEntity) {
                mPresenter.openPostDetails(postEntity);
            }
        });
        mRecyclerView.setAdapter(mPostAdapter);
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
                PostEntity lastPost = mPostAdapter.getLastPostEntity();
                mPresenter.loadMorePosts(CURRENT_INDEX,lastPost.getPostTime());
            }

            @Override
            public void onLoadmoreCanceled() {
                LogUtils.d(TAG,"取消加载");
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                mPresenter.loadPosts(CURRENT_INDEX);
                LogUtils.d(TAG,"下拉刷新");
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }


    @Override
    public void showPosts(List<PostEntity> postEntities) {
        mPostAdapter.replaceData(postEntities);
    }

    @Override
    public void showLoadMorePosts(List<PostEntity> appendPostEntities) {
        mPostAdapter.appendData(appendPostEntities);
    }


    @Override
    public void showPostDetail(String postId,String postTime,String postTitle) {
        Intent intent = new Intent(mContext,PostDetailActivity.class);
        intent.putExtra("postId",postId);
        intent.putExtra("postTime",postTime);
        intent.putExtra("postTitle",postTitle);
        startActivity(intent);
    }

    @Override
    public void showNoPosts() {
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
        Snackbar.make(mRecyclerView,"加载失败",2000).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    private void showToastShort(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(PostsContract.Presenter presenter) {
        // do nothing
    }
}
