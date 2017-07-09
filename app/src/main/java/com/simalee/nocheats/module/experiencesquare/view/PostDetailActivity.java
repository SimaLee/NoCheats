package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
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
import com.simalee.nocheats.common.util.PreferenceUtil;
import com.simalee.nocheats.module.data.entity.post.PostDetailFloorEntity;
import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.post.PostDetailMainFloorConverter;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;
import com.simalee.nocheats.module.experiencesquare.contract.PostDetailContract;
import com.simalee.nocheats.module.experiencesquare.presenter.PostDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostDetailActivity extends BaseActivity implements PostDetailContract.PostDetailView,PostDetailAdapter.OnMoreReplyClickListener{
    private static final String TAG = PostDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private PostDetailAdapter mPostDetailAdapter;


    private RelativeLayout rl_comment;
    private EditText et_comment;
    private ImageView ib_send;

    private TextView tv_back;


    PostDetailContract.Presenter mPostDetailPresenter;

    String postId;
    String postTime;
    String postTitle;

    /**
     *  用于响应下方的发表评论的功能
     */
    String mainFloorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_2);

        postId = getIntent().getStringExtra("postId");
        postTime = getIntent().getStringExtra("postTime");
        postTitle = getIntent().getStringExtra("postTitle");
        LogUtils.d(TAG,"postDetail: postId is -> " + postId);
        LogUtils.d(TAG,"postDetail: postTime is -> " + postTime);
        LogUtils.d(TAG,"postDetail: postTitle is -> " + postTitle);

        mPostDetailPresenter = new PostDetailPresenter(this);

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((mPostDetailPresenter != null) && (postId != null) && (postTime != null)){
            mPostDetailPresenter.loadPostDetail(postId,postTime);
        }

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
                    rl_comment.setAlpha(1.0F);
                }else{
                    rl_comment.setAlpha(0.5F);
                }
            }
        });
        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 发送评论
                releaseComment();

            }
        });
        //InputMethodManager imm =(InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(et_search_teacher.getWindowToken(), 0);
    }


    private void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mPostDetailAdapter = new PostDetailAdapter(this,new ArrayList<ICommentEntity>(0));


        mPostDetailAdapter.setOnMoreReplyClickListener(this);
        mRecyclerView.setAdapter(mPostDetailAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动完成
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    rl_comment.setAlpha(0.5F);
                }else{
                    rl_comment.setAlpha(0.02F);
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
        // 不用下拉刷新
        mRefreshLayout.setEnableRefresh(false);
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
                if (mPostDetailPresenter != null){

                    ICommentEntity lastComment = mPostDetailAdapter.getLastEntity();

                    String lastTimeStr = "";

                    if (lastComment instanceof PostDetailFloorEntity ){
                        lastTimeStr = ((PostDetailFloorEntity) lastComment).getReplyTime();
                    }else if (lastComment instanceof PostDetailMainFloorConverter){
                        lastTimeStr = ((PostDetailMainFloorConverter) lastComment).getReplyTime();
                    }
                    LogUtils.d(TAG,"current time is : "+ lastTimeStr);

                    mPostDetailPresenter.loadMoreComments(postId,lastTimeStr);
                }
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

    /*private List<ICommentEntity> testData(){
        ArrayList<ICommentEntity> data = new ArrayList<>();
        PostDetailMainFloorConverter one = new PostDetailMainFloorConverter();
        one.setUserName("赵日天");
        one.setPostTitle("我要开飞机");
        one.setPostType("傻帽诈骗");
        one.setPostContent("没什么好说的祝大家新年大吉");
        one.setPoint("2");

        data.add(one);

        PostDetailFloorEntity comment = new PostDetailFloorEntity();
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
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMoreReplyClick(View v, List<ReplyReplyEntity> replyReplyEntities) {

        LogUtils.d(TAG,"replyReplyList: "+ replyReplyEntities.toString());
        Intent intent = new Intent(this,ReplyDetailActivity.class);
        startActivity(intent);

    }

    @Override
    public void showPostDetail(List<ICommentEntity> postCommentEntityList) {
        //封装第一个entity 特殊
        List<ICommentEntity> convertData = convertEntittList(postCommentEntityList);

        mPostDetailAdapter.replaceData(convertData);
    }

    @Override
    public void showLoadPostDetailFailure() {
        Snackbar.make(mRecyclerView,"数据获取失败",1500).show();
    }

    @Override
    public void showLoadMoreComments(List<ICommentEntity> appendCommentEntityList) {
        mPostDetailAdapter.appendData(appendCommentEntityList);
    }

    @Override
    public void showLoadMoreCommentsFailure() {
        Snackbar.make(mRecyclerView,"获取更多数据失败！",1500).show();
    }

    @Override
    public void showCommentSuccess() {
        et_comment.setText("");
        rl_comment.setAlpha(0.02F);
        shortToast("发表评论成功！");
    }

    @Override
    public void showCommentFailure() {
        shortToast("发表评论失败！");
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
    public void setPresenter(PostDetailContract.Presenter presenter) {
        //do nothing
    }


    /**
     * 发送评论 对于当前经历
     */
    private void releaseComment() {
        String userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);
        String commentStr = et_comment.getText().toString().trim();
        String photoUrls = "";
        if (validatInput(commentStr)){
            LogUtils.d(TAG,"comment content is :" + commentStr);

            rl_comment.setBackgroundColor(Color.TRANSPARENT);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(et_comment.getWindowToken(),0);

            mPostDetailPresenter.releaseComment(userId,mainFloorId,commentStr,photoUrls);

        }


    }

    /**
     * 判断输入是否有效
     * @param commentStr
     * @return
     */
    private boolean validatInput(String commentStr) {
        if(commentStr == null || commentStr.length() == 0){
            shortToast("对不起，评论不能为空");
            return false;
        }
        if (commentStr.length() > 150){
            shortToast("对不起，评论不能为空");
            return  false;
        }
        return true;
    }

    /**
     * 转化 第一楼的entity
     * @param postCommentEntityList
     * @return
     */
    private List<ICommentEntity> convertEntittList(List<ICommentEntity> postCommentEntityList){
        if (postCommentEntityList == null || postCommentEntityList.size() == 0){
            throw new NullPointerException("postCommentEntityList is empty !");
        }
        PostDetailFloorEntity mainFloor = (PostDetailFloorEntity) postCommentEntityList.get(0);

        //Attention !
        mainFloorId = mainFloor.getCommentId();

        PostDetailMainFloorConverter mainFloorConverter = new PostDetailMainFloorConverter(postTitle,mainFloor);

        postCommentEntityList.set(0,mainFloorConverter);

        return postCommentEntityList;
    }

    /**
     * hhh
     * @param msg
     */
    private void shortToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * hh
     * @param msg
     */
    private void longToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
