package com.simalee.nocheats.module.experiencesquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.ICommentEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/6.
 */

public interface PostDetailContract {


    interface PostDetailView extends BaseView<Presenter>{

        void showPostDetail(List<ICommentEntity> postCommentEntityList);
        void showLoadPostDetailFailure();

        void showLoadMoreComments(List<ICommentEntity> appendCommentEntityList);
        void showLoadMoreCommentsFailure();

        void showCommentSuccess();
        void showCommentFailure();

        void showLoadingMoreProgress();

        void hideLoadingMoreProgress();

    }

    interface Presenter extends BasePresenter{
        void loadPostDetail(String postId,String postTime);
        void loadMoreComments(String postId,String postTime);

        void releaseComment(String userId,String floorId,String content,String photoUrls);
    }
}
