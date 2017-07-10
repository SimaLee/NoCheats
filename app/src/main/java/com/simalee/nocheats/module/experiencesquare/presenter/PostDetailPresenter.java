package com.simalee.nocheats.module.experiencesquare.presenter;

import com.simalee.nocheats.common.util.DateUtils;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.model.CommentModel;
import com.simalee.nocheats.module.data.model.ICommentModel;
import com.simalee.nocheats.module.data.model.IPostModel;
import com.simalee.nocheats.module.data.model.PostModel;
import com.simalee.nocheats.module.experiencesquare.contract.PostDetailContract;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/6.
 */

public class PostDetailPresenter implements PostDetailContract.Presenter {

    private static final String TAG = PostDetailPresenter.class.getSimpleName();

    private PostDetailContract.PostDetailView mPostDetailView;
    private IPostModel mPostModel;
    private ICommentModel mCommentModel;


    public PostDetailPresenter(PostDetailContract.PostDetailView postDetailView){
        if (postDetailView == null){
            throw new NullPointerException("postDetailView can't be null");
        }

        mPostDetailView = postDetailView;

        mPostModel = new PostModel();
        mCommentModel = new CommentModel();
    }

    /**
     * 加载帖子详情
     * @param postId
     * @param postTime
     */
    @Override
    public void loadPostDetail(String postId, String postTime) {

        mPostModel.loadPostDetail(postId, postTime, new IPostModel.LoadPostDetailCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());
                if (mPostDetailView != null){
                    mPostDetailView.hideLoadingProgress();
                    mPostDetailView.showLoadPostDetailFailure();
                }
            }

            @Override
            public void onLoadPostDetailSuccess(List<ICommentEntity> postDetail) {
                if (mPostDetailView != null){
                    mPostDetailView.hideLoadingProgress();
                    mPostDetailView.showPostDetail(postDetail);
                }
            }

            @Override
            public void onLoadPostDetailFailure() {
                if (mPostDetailView!=null){
                    mPostDetailView.hideLoadingProgress();
                    mPostDetailView.showLoadPostDetailFailure();
                }
            }
        });
    }

    /**
     * 加载更多数据
     * @param postId
     * @param postTime
     */
    @Override
    public void loadMoreComments(String postId, String postTime) {
        if (mPostDetailView != null){
            mPostDetailView.showLoadingMoreProgress();
        }

        LogUtils.d(TAG,"before : "+ postTime);
        postTime = formatTimeString(postTime);
        LogUtils.d(TAG,"after : "+ postTime);

        mPostModel.loadPostDetail(postId, postTime, new IPostModel.LoadPostDetailCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());

                if (mPostDetailView != null){
                    mPostDetailView.hideLoadingMoreProgress();
                    mPostDetailView.showLoadMoreCommentsFailure();
                }
            }

            @Override
            public void onLoadPostDetailSuccess(List<ICommentEntity> postDetail) {
                if (mPostDetailView != null){
                    mPostDetailView.hideLoadingMoreProgress();
                    mPostDetailView.showLoadMoreComments(postDetail);
                }
            }

            @Override
            public void onLoadPostDetailFailure() {
                if (mPostDetailView != null){
                    mPostDetailView.hideLoadingMoreProgress();
                    mPostDetailView.showLoadMoreCommentsFailure();
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
                LogUtils.e(TAG,"error: "+e.toString());
            }

            @Override
            public void onReleaseSuccess() {
                if (mPostDetailView!=null){
                    mPostDetailView.showCommentSuccess();
                }
            }

            @Override
            public void onReleaseFailure() {
                if (mPostDetailView!=null){
                    mPostDetailView.showLoadMoreCommentsFailure();
                }
            }
        });
    }

    /**
     * 回复评论 楼中楼开始
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
                if (mPostDetailView != null){
                    mPostDetailView.showCommentSuccess();
                }
            }

            @Override
            public void onReleaseFailure() {
                if (mPostDetailView != null){
                    mPostDetailView.showCommentFailure();
                }
            }
        });
    }

    @Override
    public void start() {
        //do nothing
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
