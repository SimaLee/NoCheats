package com.simalee.nocheats.module.topicsquare.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicDetailFloorEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicDetailMainFloorConverter;
import com.simalee.nocheats.module.experiencesquare.view.DividerItemDecoration;
import com.simalee.nocheats.module.topicsquare.contract.TopicDetailContract;
import com.simalee.nocheats.module.topicsquare.presenter.TopicDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicDetailActivity extends BaseActivity implements TopicDetailContract.TopicDetailView{

    private static final String TAG = TopicDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TwinklingRefreshLayout mRefreshLayout;

    private RelativeLayout rl_comment;
    private EditText et_comment;
    private ImageView ib_send;


    private TextView tv_back;

    private TopicDetailAdapter mTopicDetailAdapter;

    private TopicDetailContract.Presenter mTopicDetailPresenter;

    String topicId;
    String topicTime;
    String topicTitle;

    /**
     *  用于响应下方的发表评论的功能
     */
    String mainFloorId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        topicId = getIntent().getStringExtra("topicId");
        topicTime = getIntent().getStringExtra("topicTime");
        topicTitle = getIntent().getStringExtra("topicTitle");

        LogUtils.d(TAG,topicTitle);

        mTopicDetailPresenter = new TopicDetailPresenter(this);
        initViews();

    }

    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_back = (TextView) findViewById(R.id.tv_back);
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);

        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
       // rl_comment.setAlpha(0.8F);

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


    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((mTopicDetailPresenter != null) && (topicId != null) && (topicTime != null)){
            mTopicDetailPresenter.loadTopicDetail(topicId,topicTime);
        }
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

        // 不用下拉刷新
        mRefreshLayout.setEnableRefresh(false);

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

                if (mTopicDetailPresenter != null){
                    ICommentEntity lastComment = mTopicDetailAdapter.getLastEntity();
                    String lastTimeStr = "";

                    if (lastComment instanceof TopicDetailMainFloorConverter){
                        lastTimeStr = ((TopicDetailMainFloorConverter) lastComment).getReplyTime();
                    }else if (lastComment instanceof TopicDetailFloorEntity){
                        lastTimeStr = ((TopicDetailFloorEntity) lastComment).getReplyTime();
                    }
                    LogUtils.d(TAG,"current time is : "+ lastTimeStr);
                    mTopicDetailPresenter.loadMoreComments(topicId,lastTimeStr);
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

    @Override
    public void showTopicDetail(List<ICommentEntity> topicCommentEntityList) {
        //封装第一个entity 特殊
        List<ICommentEntity> convertData = convertEntittList(topicCommentEntityList);
        mTopicDetailAdapter.replaceData(convertData);
    }

    @Override
    public void showLoadTopicDetailFailure() {
        Snackbar.make(mRecyclerView,"数据获取失败",1500).show();
    }

    @Override
    public void showLoadMoreComments(List<ICommentEntity> appendCommentEntityList) {
        mTopicDetailAdapter.appendData(appendCommentEntityList);
    }

    @Override
    public void showLoadMoreCommentsFailure() {
        Snackbar.make(mRecyclerView,"获取更多数据失败！",1500).show();
    }

    @Override
    public void showCommentSuccess() {
        et_comment.setText("");
        rl_comment.setAlpha(0.02F);
        Toast.makeText(getApplicationContext(),"发表评论成功!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommentFailure() {

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
    public void setPresenter(TopicDetailContract.Presenter presenter) {
        //do nothing
    }

   /* private ArrayList<ICommentEntity> testData(){

        ArrayList<ICommentEntity> data = new ArrayList<>();
        TopicDetailMainFloorConverter one = new TopicDetailMainFloorConverter();
        one.setUserName("赵日天2");
        one.setTopicTitle("我要开飞机");
        one.setTopicType("傻帽诈骗");
        one.setTopicContent("没什么好说的祝大家新年大吉");
        one.setPoint("2");
        data.add(one);

        TopicDetailFloorEntity comment = new TopicDetailFloorEntity();
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


        TopicDetailFloorEntity comment2 = new TopicDetailFloorEntity();
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

    /**
     * 转化 第一楼的entity
     * @param topicCommentEntityList
     * @return
     */
    private List<ICommentEntity> convertEntittList(List<ICommentEntity> topicCommentEntityList){
        if (topicCommentEntityList == null || topicCommentEntityList.size() == 0){
            throw new NullPointerException("postCommentEntityList is empty !");
        }
        TopicDetailFloorEntity mainFloor = (TopicDetailFloorEntity) topicCommentEntityList.get(0);

        //Attention !
        mainFloorId = mainFloor.getCommentId();

        TopicDetailMainFloorConverter mainFloorConverter = new TopicDetailMainFloorConverter(topicTitle,mainFloor);

        topicCommentEntityList.set(0,mainFloorConverter);
        return topicCommentEntityList;
    }


    /**
     * 发送评论 对于当前经历
     */
    private void releaseComment() {
        String userId = PreferenceUtil.getString(this,PreferenceUtil.USER_ID);

        String commentStr = et_comment.getText().toString().trim();

        //TODO 添加图片
        String photoUrls = "";

        if (validatInput(commentStr)){
            LogUtils.d(TAG,"comment content is :" + commentStr);

            rl_comment.setBackgroundColor(Color.TRANSPARENT);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(et_comment.getWindowToken(),0);

            mTopicDetailPresenter.releaseComment(userId,mainFloorId,commentStr,photoUrls);

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
            shortToast("对不起，评论不能超过150字");
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
}
