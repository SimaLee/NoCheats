package com.simalee.nocheats.module.experiencesquare.presenter;

import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.data.model.IPostModel;
import com.simalee.nocheats.module.data.model.PostModel;
import com.simalee.nocheats.module.experiencesquare.contract.PreviousPostContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee Sima on 2017/7/7.
 */

public class PreviousPostPresenter implements PreviousPostContract.Presenter {

    private final static String TAG = PreviousPostPresenter.class.getSimpleName();

    private PreviousPostContract.View mPreviousPostView;
    private IPostModel mPostModel;

    public PreviousPostPresenter(PreviousPostContract.View previousPostView){
        if (previousPostView == null){
            throw new NullPointerException("previousPostView is empty!");
        }
        mPreviousPostView = previousPostView;

        mPostModel = new PostModel();
    }


    @Override
    public void loadMyPosts(String userId, int postType) {

        if (mPreviousPostView != null) {

            mPreviousPostView.showLoadingProgress();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lastTimeStr = dateFormat.format(new Date());

            mPostModel.loadMyPosts(userId, postType, lastTimeStr, new IPostModel.LoadMyPostCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error: "+ e.toString());
                    mPreviousPostView.hideLoadingProgress();
                    mPreviousPostView.showLoadingFailure();
                }

                @Override
                public void onLoadPostsSuccess(List<PostEntity> postEntities) {
                    if (mPreviousPostView != null){
                        mPreviousPostView.hideLoadingProgress();
                        mPreviousPostView.showPosts(postEntities);
                    }
                }

                @Override
                public void onLoadPostsFailure() {
                    if (mPreviousPostView != null){
                        mPreviousPostView.hideLoadingProgress();
                        mPreviousPostView.showLoadingFailure();
                    }
                }
            });

        }
    }

    @Override
    public void loadMoreMyPosts(String userId, int postType, String lastTimeStr) {
        //TODO
        if (mPreviousPostView != null){
            mPreviousPostView.showLoadingMoreProgress();

            mPostModel.loadMyPosts(userId, postType, lastTimeStr, new IPostModel.LoadMyPostCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error: "+ e.toString() );
                    mPreviousPostView.showLoadingFailure();
                }

                @Override
                public void onLoadPostsSuccess(List<PostEntity> postEntities) {
                    mPreviousPostView.hideLoadingMoreProgress();
                    mPreviousPostView.showLoadMorePosts(postEntities);
                }

                @Override
                public void onLoadPostsFailure() {
                    mPreviousPostView.hideLoadingMoreProgress();
                    mPreviousPostView.showLoadingFailure();
                }
            });
        }
    }

    @Override
    public void openPostDetails(PostEntity postEntity) {
        if (postEntity == null){
            throw new NullPointerException(" PreviousPostPresenter:postEntity is null");
        }
        if (mPreviousPostView != null){
            mPreviousPostView.showPostDetail(postEntity.getId(),
                    postEntity.getPostTime(),postEntity.getPostTitle());
        }
    }

    @Override
    public void start() {
        //do nothing
    }
}
