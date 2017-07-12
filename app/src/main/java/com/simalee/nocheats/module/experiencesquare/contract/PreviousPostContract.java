package com.simalee.nocheats.module.experiencesquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.post.PostEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/7.
 */

public interface PreviousPostContract {


    interface View extends BaseView<Presenter>{

        void showPosts(List<PostEntity> postEntities);
        void showLoadMorePosts(List<PostEntity> appendPostEntities);

        void showPostDetail(String postId,String postTime,String postTitle);

        void showNoPosts();

        void showLoadingProgress();
        void hideLoadingProgress();

        void showLoadingMoreProgress();
        void hideLoadingMoreProgress();

        void showLoadingFailure();

        void showDeletePostSuccess();
        void showDeletePostFailure(String reason);
    }

    interface Presenter extends BasePresenter{

        void loadMyPosts(String userId,int postType);
        void loadMoreMyPosts(String userId,int postType,String lastTimeStr);

        void openPostDetails(PostEntity postEntity);

        void deletePost(String userId,String postId);
    }
}
