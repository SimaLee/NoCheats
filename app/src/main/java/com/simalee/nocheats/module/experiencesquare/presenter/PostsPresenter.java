package com.simalee.nocheats.module.experiencesquare.presenter;

import android.os.Handler;
import android.os.Looper;

import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.data.model.IPostModel;
import com.simalee.nocheats.module.data.model.PostModel;
import com.simalee.nocheats.module.experiencesquare.contract.PostsContract;
import com.simalee.nocheats.module.experiencesquare.view.PostFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/26.
 */

public class PostsPresenter implements PostsContract.Presenter {


    private static final String TAG = PostsPresenter.class.getSimpleName();

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private int currentPage = PostFragment.PAGE_MAIN;

    private PostsContract.AllPostsView mPostView;

    private PostsContract.NewPostView mNewPostView;


    private IPostModel mPostModel;


    /**
     * @param postView
     * @param currentPage  当PostsPresenter 被newPost使用时 传递的currentPage 不会用到 设为-1
     */
    public PostsPresenter(BaseView<PostsContract.Presenter> postView, int currentPage){

        if (postView == null){
            throw new NullPointerException("postView can't be null");
        }
        if (postView instanceof  PostsContract.AllPostsView){
            this.mPostView = (PostsContract.AllPostsView) postView;
            setCurrentPage(currentPage);
        }

        if (postView instanceof PostsContract.NewPostView){
            mNewPostView = (PostsContract.NewPostView) postView;
        }

        mPostModel = new PostModel();
    }

    @Override
    public void loadPosts(int pageIndex) {
        LogUtils.d(TAG,"loadPosts :"+pageIndex+" invoked");

        if (mPostView != null && mPostView.isActive()){

            mPostView.showLoadingProgress();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String last_time = dateFormat.format(new Date());

            mPostModel.loadPosts(pageIndex,last_time, new IPostModel.LoadPostsCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error:"+ e.toString());
                    if (mPostView.isActive()){
                        mPostView.hideLoadingProgress();
                        mPostView.showLoadingFailure();
                    }
                }

                @Override
                public void onLoadPostsSuccess(List<PostEntity> postEntities) {
                    if (mPostView.isActive()){
                        mPostView.hideLoadingProgress();
                        if (postEntities.size() == 0){
                            mPostView.showNoPosts();
                        }else{
                            mPostView.showPosts(postEntities);
                        }
                    }
                }

                @Override
                public void onLoadPostsFailure() {
                    if (mPostView.isActive()){
                        mPostView.hideLoadingProgress();
                        mPostView.showLoadingFailure();
                    }
                }
            });


        }
    }

    @Override
    public void loadMorePosts(int pageIndex,String lastTimeStr) {
        if (mPostView != null && mPostView.isActive()){
            mPostView.showLoadingMoreProgress();

            mPostModel.loadPosts(pageIndex, lastTimeStr, new IPostModel.LoadPostsCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error:"+ e.toString());
                    if (mPostView.isActive()){
                        mPostView.hideLoadingMoreProgress();
                        mPostView.showLoadingFailure();

                    }
                }

                @Override
                public void onLoadPostsSuccess(List<PostEntity> postEntities) {
                    if (mPostView.isActive()){
                        mPostView.hideLoadingMoreProgress();
                        mPostView.showLoadMorePosts(postEntities);
                    }
                }

                @Override
                public void onLoadPostsFailure() {
                    if (mPostView.isActive()){
                        mPostView.hideLoadingMoreProgress();
                        mPostView.showLoadingFailure();
                    }
                }
            });
        }
    }

    @Override
    public void openPostDetails(PostEntity postEntity) {
        if (postEntity == null){
            throw new NullPointerException(" postPresenter:postEntity is null");
        }
        if (mPostView!= null && mPostView.isActive()){
            mPostView.showPostDetail(postEntity.getId(),postEntity.getPostTime(),
                    postEntity.getPostTitle(),postEntity.getPostType());
        }
    }

    @Override
    public void releasePost(String userId, String postTitle, String postType, String postContent, String postPicUrl) {

        mNewPostView.showLoadingProgress();

        mPostModel.releasePost(userId, postTitle, postType, postContent, postPicUrl, new IPostModel.ReleasePostCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e("PostsPresenter","error: " + e.toString());
            }

            @Override
            public void onPostReleasedSuccess() {
                if (mNewPostView != null){
                    mNewPostView.hideLoadingProgress();
                    mNewPostView.showPostReleasedSuccess();
                }
            }

            @Override
            public void onPostReleasedFailure(String reason) {
                if (mNewPostView != null){
                    mNewPostView.hideLoadingProgress();
                    mNewPostView.showPostReleasedFailed(reason);
                }
            }
        });

    }


    @Override
    public void start() {
        if (mPostView != null){
            loadPosts(currentPage);
        }
    }


    private ArrayList<PostEntity> testDataMain(){
        PostEntity one = new PostEntity();
        one.setId("12314");
        one.setUserName("德玛西亚");
        one.setPoint("3");
        one.setPostTitle("大骗子");
        one.setPostType(1);
        one.setPostContent("今天被骗了，好气啊");
        one.setPostViewCount(12);
        ArrayList<PostEntity> data = new ArrayList<>();
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);

        return data;
    }

    private ArrayList<PostEntity> testDataOther(){
        PostEntity one = new PostEntity();
        one.setId("12314");
        one.setUserName("不是首页");
        one.setPoint("3");
        one.setPostTitle("大骗子");
        one.setPostType(2);
        one.setPostContent("今天被骗了，好气啊");
        one.setPostViewCount(12);
        ArrayList<PostEntity> data = new ArrayList<>();
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);

        return data;
    }

    private ArrayList<PostEntity> testDataMore(){
        PostEntity one = new PostEntity();
        one.setId("12314");
        one.setUserName("下拉刷新");
        one.setPoint("3");
        one.setPostTitle("大骗子");
        one.setPostType(3);
        one.setPostContent("今天被骗了，好气啊");
        one.setPostViewCount(12);
        ArrayList<PostEntity> data = new ArrayList<>();
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);
        data.add(one);

        data.add(one);
        data.add(one);
        data.add(one);

        return data;
    }

}
