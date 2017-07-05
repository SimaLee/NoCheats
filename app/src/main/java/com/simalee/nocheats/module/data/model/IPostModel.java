package com.simalee.nocheats.module.data.model;

import com.simalee.nocheats.common.base.Response;
import com.simalee.nocheats.module.data.entity.post.PostEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/5.
 */

public interface IPostModel {


    interface ReleasePostCallback{
        void onError(Exception e);
        void onPostReleasedSuccess();
        void onPostReleasedFailure(String reason);
    }


    interface LoadPostsCallback{
        void onError(Exception e);
        void onLoadPostsSuccess(List<PostEntity> postEntities);
        void onLoadPostsFailure();
    }


    void releasePost(String userId,String postTitle,String postType,
                     String postContent,String postPicUrl,ReleasePostCallback callback);

    void loadPosts(int pageIndex,LoadPostsCallback callback);

}
