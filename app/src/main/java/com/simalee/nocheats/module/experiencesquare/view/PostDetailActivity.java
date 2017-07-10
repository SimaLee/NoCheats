package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.entity.post.PostDetailMainFloorConverter;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;
import com.simalee.nocheats.module.experiencesquare.contract.PostDetailContract;
import com.simalee.nocheats.module.experiencesquare.presenter.PostDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostDetailActivity extends BaseActivity implements PostDetailContract.PostDetailView,
        PostDetailAdapter.OnMoreReplyClickListener, PostDetailAdapter.OnCommentClickListener {
    private static final String TAG = PostDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private PostDetailAdapter mPostDetailAdapter;


    private RelativeLayout rl_comment;
    private EditText et_comment;
    private ImageView ib_send;

    private TextView tv_back;


    PostDetailContract.Presenter mPostDetailPresenter;


    String userId;

    String postId;
    String postTime;
    String postTitle;

    /**
     *  用于响应下方的发表评论的功能
     */
    String mainFloorId;

    /**
     * 选择回复的楼层id
     */
    String selectReplyFloorId;

    /**
     *  回复楼主还是回复楼层
     */
    private static final int MODE_REPLY_HOST = 0;
    private static final int MODE_REPLY_FLOOR = 1;

    private int replyMode = MODE_REPLY_HOST;

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

        userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);

        //键盘不自动弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((mPostDetailPresenter != null) && (postId != null) && (postTime != null)){
            LogUtils.d(TAG,"postDetail: postTime is -> " + postTime);
            mPostDetailPresenter.loadPostDetail(postId,postTime);
        }

    }

    private void initViews(){

        tv_back = (TextView) findViewById(R.id.tv_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);

        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
        rl_comment.setBackgroundColor(getResources().getColor(R.color.white));

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

        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0){
                    hideSoftInput();
                    et_comment.setHint("说说你的看法吧");
                    replyMode = MODE_REPLY_HOST;
                }
            }
        });
        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 发送评论
                if (replyMode == MODE_REPLY_HOST){
                    releaseComment();
                }else if (replyMode == MODE_REPLY_FLOOR){
                    replyComment();
                }


            }
        });

    }


    private void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mPostDetailAdapter = new PostDetailAdapter(this,new ArrayList<ICommentEntity>(0));

        mPostDetailAdapter.setOnMoreReplyClickListener(this);
        mPostDetailAdapter.setOnCommentClickListener(this);
        mRecyclerView.setAdapter(mPostDetailAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动完成
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    //rl_comment.setAlpha(0.5F);
                }else{
                   // rl_comment.setAlpha(0.02F);
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

                if ((mPostDetailPresenter != null) && (postId != null) && (postTime != null)){
                    mPostDetailPresenter.loadPostDetail(postId,postTime);
                }
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

        shortToast("发表评论成功！");
        LogUtils.d(TAG,"postDetail: postTime is -> " + postTime);
        //TODO 优化交互逻辑
       // mPostDetailPresenter.loadPostDetail(postId,postTime);
    }

    @Override
    public void showCommentFailure() {
        shortToast("发表评论失败！");
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
    public void setPresenter(PostDetailContract.Presenter presenter) {
        //do nothing
    }

    @Override
    public void onMoreReplyClick(String floorId,String commentId) {

        LogUtils.d(TAG,"current floor id : "+ floorId);
        LogUtils.d(TAG,"current comment id : "+ commentId);
        Intent intent = new Intent(this,ReplyDetailActivity.class);

        intent.putExtra("floorId",floorId);
        intent.putExtra("commentId",commentId);
        startActivity(intent);

    }

    @Override
    public void onCommentClick(View v,String userName, String floorId) {
        LogUtils.d(TAG,"回复： " + userName + " floorId :" + floorId);

        replyMode = MODE_REPLY_FLOOR;
        selectReplyFloorId = floorId;

        String hintStr = "回复 "+ userName + ":";
        et_comment.setHint(hintStr);
        et_comment.requestFocus();
        showSoftInput();
    }

    /**
     * 发送评论 对于当前经历
     */
    private void releaseComment() {

        String commentStr = et_comment.getText().toString().trim();
        String photoUrls = "";
        if (validatInput(commentStr)){
            LogUtils.d(TAG,"comment content is :" + commentStr);
            hideSoftInput();
            mPostDetailPresenter.releaseComment(userId,mainFloorId,commentStr,photoUrls);
        }

    }

    /**
     * 回复楼层 楼中楼开始
     */
    private void replyComment() {
        String commentStr = et_comment.getText().toString().trim();
        if (validatInput(commentStr)){
            LogUtils.d(TAG,"comment reply floorId is : " + selectReplyFloorId);
            LogUtils.d(TAG,"comment reply content is :" + commentStr);

            hideSoftInput();
            mPostDetailPresenter.replyComment(selectReplyFloorId,userId,commentStr);
            replyMode = MODE_REPLY_HOST;
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
            shortToast("对不起，评论不能超出150字");
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

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et_comment.getWindowToken(),0);
    }

    /**
     * 弹出软键盘
     */
    private void showSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et_comment,0);
    }


}
