package com.simalee.nocheats.module.experiencesquare.contract;

import android.net.Uri;
import android.widget.ImageView;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.module.data.entity.post.PostEntity;

import java.util.List;


/**
 * Created by Lee Sima on 2017/6/26.
 */

public interface PostsContract {

    interface AllPostsView extends BaseView<PostsContract.Presenter>{

        void showPosts(List<PostEntity> postEntities);
        void showLoadMorePosts(List<PostEntity> appendPostEntities);

        void showPostDetail(String postId,String postTime,String postTitle,int postType);
        void showNoPosts();
        void showLoadingProgress();
        void hideLoadingProgress();
        void showLoadingMoreProgress();
        void hideLoadingMoreProgress();
        void showLoadingFailure();

        //用于异步任务中判断是否可以操作界面
        boolean isActive();
    }


    interface NewPostView extends BaseView<Presenter>{
        void showLoadingProgress();
        void hideLoadingProgress();

        void showPostReleasedSuccess();
        void showPostReleasedFailed(String reason);

    }

    interface Presenter extends BasePresenter{

        void loadPosts(int pageIndex);

        void loadMorePosts(int pageIndex,String lastTimeStr);

        void openPostDetails(PostEntity postEntity);


        void releasePost(String userId,String postTitle,String postType,String postContent,String postPicUrl);

    }
}
