package com.simalee.nocheats.module.experiencesquare.view;

import android.app.Activity;
import android.content.Context;
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
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;
import com.simalee.nocheats.module.data.entity.post.PostDetailFloorEntity;
import com.simalee.nocheats.module.data.entity.post.PostDetailMainFloorConverter;
import com.simalee.nocheats.module.experiencesquare.contract.ReplyDetailContract;
import com.simalee.nocheats.module.experiencesquare.presenter.ReplyDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/27.
 */

public class ReplyDetailActivity extends Activity implements ReplyDetailContract.View{

    private static final String TAG = ReplyDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private RelativeLayout rl_comment;
    private EditText et_comment;
    private ImageView ib_send;

    private TextView tv_back;


    private ReplyDetailAdapter mReplyDetailAdapter;

    private ReplyDetailContract.Presenter mReplyDetailPresenter;
    /**
     * 当前界面所属的楼层id
     */
    String floorId = "";

    /**
     * 没有点击Item的时候默认回复当前楼层
     */
    String commentId = "";

    /**
     * 当前用户的id
     */
    String userId = "";

    /**
     *  回复层主还是回复指定的人
     */
    private static final int MODE_REPLY_HOST = 0;
    private static final int MODE_REPLY_PEOPLE = 1;

    private int replyMode = MODE_REPLY_HOST;


    /**
     * 被选定的回复者Id
     */
    String selectObjectUserId;

    /**
     * 被选定的回复者昵称
     */
    String selectObjectUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_detail);

        floorId = getIntent().getStringExtra("floorId");
        commentId = getIntent().getStringExtra("commentId");
        LogUtils.d(TAG,"get floorId: " + floorId);
        LogUtils.d(TAG,"get commentId: " + commentId);

        userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);

        mReplyDetailPresenter = new ReplyDetailPresenter(this);

        //键盘不自动弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReplyDetailPresenter != null && (floorId != null)){
            mReplyDetailPresenter.loadFloorReply(floorId);
        }
    }

    private void initViews() {
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


        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 发送评论
                if (replyMode == MODE_REPLY_HOST){
                    replyComment();
                }else if (replyMode == MODE_REPLY_PEOPLE){
                    replyReply();
                }
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

    }

    private void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        mReplyDetailAdapter = new ReplyDetailAdapter(this,new ArrayList<ReplyReplyEntity>(0));

        mReplyDetailAdapter.setFloorId(floorId);
        mReplyDetailAdapter.setOnItemClickListener(new ReplyDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String floorId, String objectUserId, String objectUserName) {
                //todo 设置评论事件
                LogUtils.d(TAG,"click floorId -> "+ floorId);

                selectObjectUserId = objectUserId;
                selectObjectUserName = objectUserName;

                replyMode = MODE_REPLY_PEOPLE;

                String hintStr = "回复 "+ objectUserName + ":";
                et_comment.setHint(hintStr);
                et_comment.requestFocus();
                showSoftInput();
            }
        });
        mRecyclerView.setAdapter(mReplyDetailAdapter);

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
        // 下拉刷新
        // mRefreshLayout.setEnableRefresh(false);
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
            }

            @Override
            public void onLoadmoreCanceled() {
                LogUtils.d(TAG,"取消加载");
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(TAG,"刷新");
                if (mReplyDetailPresenter != null && (floorId != null)){
                    mReplyDetailPresenter.loadFloorReply(floorId);
                }
            }

            @Override
            public void onRefreshCanceled() {
                LogUtils.d(TAG,"取消刷新");
            }
        });
    }


    @Override
    public void showFloorReply(List<ReplyReplyEntity> replyEntityList) {
        if (replyEntityList != null && replyEntityList.size() != 0){
            mReplyDetailAdapter.replaceData(replyEntityList);
        }
    }

    @Override
    public void showLoadFloorReplyFailed() {
        shortToast("获取评论失败！");
    }

    @Override
    public void showNoReply() {
        shortToast("当前还没有数据呢");
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
    public void showCommentSuccess() {
        et_comment.setText("");
        shortToast("发表评论成功！");

        replyMode = MODE_REPLY_HOST;

        if (mReplyDetailPresenter != null && (floorId != null)){
            mReplyDetailPresenter.loadFloorReply(floorId);
        }

    }

    @Override
    public void showCommentFailure() {
        shortToast("发表评论失败！");
    }

    @Override
    public void setPresenter(ReplyDetailContract.Presenter presenter) {
        // do nothing
    }

    /**
     * 发送评论 对层主
     */
    private void replyComment() {
        String commentStr = et_comment.getText().toString().trim();
        if (validatInput(commentStr)){
            hideSoftInput();
            mReplyDetailPresenter.replyComment(floorId,userId,commentStr);
            replyMode = MODE_REPLY_HOST;
        }
    }

    /**
     *  回复指定的回复
     */
    private void replyReply(){
        String commentStr = et_comment.getText().toString().trim();

        if (validatInput(commentStr)){
            hideSoftInput();

            LogUtils.d(TAG,"发回复的：" + userId);
            LogUtils.d(TAG,"被回复的： id" + selectObjectUserId);
            LogUtils.d(TAG,"被回复的： name " + selectObjectUserName);

            mReplyDetailPresenter.replyReply(floorId,userId,selectObjectUserId
                    ,selectObjectUserName,commentStr);
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
