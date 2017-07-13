package com.simalee.nocheats.module.experiencesquare.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
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
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.experiencesquare.contract.PreviousPostContract;
import com.simalee.nocheats.module.experiencesquare.presenter.PreviousPostPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/15.
 */

public class PreviousPostActivity extends BaseActivity implements PreviousPostContract.View{

    private static final String TAG = PreviousPostActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private TwinklingRefreshLayout mRefreshLayout;
    private PostAdapter mPostAdapter;

    private TextView tv_back;
    private PreviousPostPresenter mPreviousPostPresenter;

    String userId;

    /**
     * 用户发布的帖子类型 目前为 0 所有
     */
    int postType = 1;

    /**
     * 删除的项的index
     */
    int deleteItemIndex = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_post);

        userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);

        mPreviousPostPresenter = new PreviousPostPresenter(this);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreviousPostPresenter != null){
            mPreviousPostPresenter.loadMyPosts(userId,postType);
        }
    }

    private void initViews(){

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_back = (TextView) findViewById(R.id.tv_back);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPostAdapter = new PostAdapter(this,new ArrayList<PostEntity>(0));

        mPostAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, PostEntity postEntity) {
                mPreviousPostPresenter.openPostDetails(postEntity);
            }
        });

        mPostAdapter.setOnItemLongClickListener(new PostAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position, PostEntity postEntity) {
                deleteItemIndex = position;
                showPopupWindow(postEntity);
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
                PostEntity lastEntity = mPostAdapter.getLastPostEntity();
                mPreviousPostPresenter.loadMoreMyPosts(userId,postType,lastEntity.getPostTime());
            }

            @Override
            public void onLoadmoreCanceled() {
                LogUtils.d(TAG,"取消加载");
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

                mPreviousPostPresenter.loadMyPosts(userId,postType);
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }


    /**
     * 删除某一项
     * @param postEntity
     */
    private void showPopupWindow(final PostEntity postEntity) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_delete_post, null);
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


        Button bt_delete_post = (Button) contentView.findViewById(R.id.bt_delete_post);
        Button bt_cancel = (Button) contentView.findViewById(R.id.bt_cancel);

        bt_delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PreviousPostActivity.this)
                        .setTitle("警告")
                        .setMessage("删除该帖子？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LogUtils.d(TAG,"Dialog 确定 删除帖子");
                                if(mPreviousPostPresenter != null){
                                    mPreviousPostPresenter.deletePost(userId,postEntity.getId());
                                }
                                mPopWindow.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                LogUtils.d(TAG,"Dialog 取消");
                                mPopWindow.dismiss();
                            }
                        }).show();


            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });

        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_previous_post, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
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
    public void showPostDetail(String postId, String postTime, String postTitle) {
        Intent intent = new Intent(this,PostDetailActivity.class);
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
        Snackbar.make(mRefreshLayout,"加载失败",2000).show();
    }

    @Override
    public void showDeletePostSuccess() {
        Snackbar.make(mRefreshLayout,"删除帖子成功!",1000).show();
        LogUtils.d(TAG,"before: " + mPostAdapter.getItemCount());
        if (deleteItemIndex != -1){
            mPostAdapter.remove(deleteItemIndex);
            LogUtils.d(TAG,"after: " + mPostAdapter.getItemCount());
            deleteItemIndex = -1;
        }
    }

    @Override
    public void showDeletePostFailure(String reason) {
        Snackbar.make(mRefreshLayout,reason,1000).show();
    }

    @Override
    public void setPresenter(PreviousPostContract.Presenter presenter) {
        //do noting
    }

    private void showToastShort(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
