package com.simalee.nocheats.module.experiencesquare.presenter;

import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.ICommentEntity;
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


    public PostDetailPresenter(PostDetailContract.PostDetailView postDetailView){
        if (postDetailView == null){
            throw new NullPointerException("postDetailView can't be null");
        }

        mPostDetailView = postDetailView;

        mPostModel = new PostModel();
    }

    @Override
    public void loadPostDetail(String postId, String postTime) {

        mPostModel.loadPostDetail(postId, postTime, new IPostModel.LoadPostDetailCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());
                if (mPostDetailView != null){
                    mPostDetailView.showLoadPostDetailFailure();
                }
            }

            @Override
            public void onLoadPostDetailSuccess(List<ICommentEntity> postDetail) {
                if (mPostDetailView != null){
                    mPostDetailView.showPostDetail(postDetail);
                }
            }

            @Override
            public void onLoadPostDetailFailure() {
                if (mPostDetailView!=null){
                    mPostDetailView.showLoadPostDetailFailure();
                }
            }
        });
    }

    @Override
    public void loadMoreComments(String postId, String postTime) {

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
                    mPostDetailView.showLoadMoreCommentsFailure();
                }
            }
        });
    }

    @Override
    public void releaseComment(String userId, String floorId, String content, String photoUrls) {
        mPostModel.releaseComment(userId, floorId, content, photoUrls, new IPostModel.ReleaseCommentCallback() {
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

    @Override
    public void start() {
        //do nothing
    }
}
