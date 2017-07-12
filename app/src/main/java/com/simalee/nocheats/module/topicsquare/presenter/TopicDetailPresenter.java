package com.simalee.nocheats.module.topicsquare.presenter;

import com.simalee.nocheats.common.util.DateUtils;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.model.CommentModel;
import com.simalee.nocheats.module.data.model.ICommentModel;
import com.simalee.nocheats.module.data.model.ITopicModel;
import com.simalee.nocheats.module.data.model.TopicModel;
import com.simalee.nocheats.module.topicsquare.contract.TopicDetailContract;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public class TopicDetailPresenter implements TopicDetailContract.Presenter {

    private static final String TAG = TopicDetailPresenter.class.getSimpleName();

    private TopicDetailContract.TopicDetailView mTopicDetailView;
    private ITopicModel mTopicModel;
    private ICommentModel mCommentModel;


    public TopicDetailPresenter(TopicDetailContract.TopicDetailView topicDetailView){
        if (topicDetailView == null){
            throw new NullPointerException("topicDetailView can't be null");
        }
        mTopicDetailView = topicDetailView;
        mTopicModel = new TopicModel();
        mCommentModel = new CommentModel();
    }

    /**
     * 加载话题详情
     * @param topicId
     * @param topicTime
     */
    @Override
    public void loadTopicDetail(String topicId, String topicTime) {

        if (mTopicDetailView != null){
            mTopicDetailView.showLoadingProgress();
        }

        mTopicModel.loadTopicDetail(topicId, topicTime, new ITopicModel.LoadTopicDetailCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: "+e.toString());
                if (mTopicDetailView != null){
                    mTopicDetailView.hideLoadingProgress();
                    mTopicDetailView.showLoadTopicDetailFailure();
                }
            }

            @Override
            public void onLoadTopicDetailSuccess(List<ICommentEntity> topicDetail) {
                if (mTopicDetailView != null){
                    mTopicDetailView.hideLoadingProgress();
                    mTopicDetailView.showTopicDetail(topicDetail);
                }
            }

            @Override
            public void onLoadTopicDetailFailure() {
                if (mTopicDetailView != null){
                    mTopicDetailView.hideLoadingProgress();
                    mTopicDetailView.showLoadTopicDetailFailure();
                }
            }
        });
    }

    /**
     * 加载更多详情
     * @param topicId
     * @param topicTime
     */
    @Override
    public void loadMoreComments(String topicId, String topicTime) {

        if (mTopicDetailView != null){
            mTopicDetailView.showLoadingMoreProgress();
        }


        LogUtils.d(TAG,"before : "+ topicTime);
        topicTime = formatTimeString(topicTime);
        LogUtils.d(TAG,"after : "+ topicTime);

        mTopicModel.loadTopicDetail(topicId, topicTime, new ITopicModel.LoadTopicDetailCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: "+e.toString());
                if (mTopicDetailView != null){
                    mTopicDetailView.hideLoadingMoreProgress();
                    mTopicDetailView.showLoadTopicDetailFailure();
                }
            }

            @Override
            public void onLoadTopicDetailSuccess(List<ICommentEntity> topicDetail) {
                if (mTopicDetailView != null){
                    mTopicDetailView.hideLoadingMoreProgress();
                    mTopicDetailView.showLoadMoreComments(topicDetail);
                }
            }

            @Override
            public void onLoadTopicDetailFailure() {
                if (mTopicDetailView != null){
                    mTopicDetailView.hideLoadingMoreProgress();
                    mTopicDetailView.showLoadMoreCommentsFailure();
                }
            }
        });
    }

    /**
     * 发表评论
     * @param userId
     * @param floorId
     * @param content
     * @param photoUrls
     */
    @Override
    public void releaseComment(String userId, String floorId, String content, String photoUrls) {

        mCommentModel.releaseComment(userId, floorId, content, photoUrls, new ICommentModel.ReleaseCommentCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());
                if (mTopicDetailView != null){
                    mTopicDetailView.showCommentFailure();
                }
            }

            @Override
            public void onReleaseSuccess() {
                if (mTopicDetailView != null){
                    mTopicDetailView.showCommentSuccess();
                }
            }

            @Override
            public void onReleaseFailure() {
                if (mTopicDetailView != null){
                    mTopicDetailView.showCommentFailure();
                }
            }
        });

    }

    /**
     * 回复某一层楼
     * @param floorId
     * @param userId
     * @param content
     */
    @Override
    public void replyComment(String floorId, String userId, String content) {
        mCommentModel.replyComment(floorId, userId, content, new ICommentModel.ReleaseCommentCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());
            }

            @Override
            public void onReleaseSuccess() {
                if (mTopicDetailView != null){
                    mTopicDetailView.showCommentSuccess();
                }
            }

            @Override
            public void onReleaseFailure() {
                if (mTopicDetailView != null){
                    mTopicDetailView.showCommentFailure();
                }
            }
        });
    }

    @Override
    public void start() {
        // do nothing
    }

    /**
     * 为了后台访问做的处理
     * @param timeStr
     * @return
     */
    private String formatTimeString(String timeStr){
        return DateUtils.plusOneSecond(timeStr);
    }
}
