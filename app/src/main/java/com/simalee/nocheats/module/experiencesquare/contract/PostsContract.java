package com.simalee.nocheats.module.experiencesquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.post.PostEntity;

import java.util.List;


/**
 * Created by Lee Sima on 2017/6/26.
 */

public interface PostsContract {

    interface View extends BaseView<PostsContract.Presenter>{
        void showPosts(List<PostEntity> postEntities);

        void showPostDetail(String postId);
        void showNoPosts();
        void showLoadingProgress();
        void hindLoadingProgress();
        void showLoadingMoreProgress();
        void hindLoadingMoreProgress();
        void showLoadingFailure();

        //用于异步任务中判断是否可以操作界面
        boolean isActive();
    }


    interface Presenter extends BasePresenter{

        void loadPosts(int pageIndex);
        void loadMorePosts(int pageIndex);
        void openPostDetails(PostEntity postEntity);

    }
}
