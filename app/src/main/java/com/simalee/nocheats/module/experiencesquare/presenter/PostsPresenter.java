package com.simalee.nocheats.module.experiencesquare.presenter;

import android.os.Handler;
import android.os.Looper;

import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.experiencesquare.contract.PostsContract;
import com.simalee.nocheats.module.experiencesquare.view.PostFragment;

import java.util.ArrayList;

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

    private PostsContract.View postView;

    public PostsPresenter(PostsContract.View postView,int currentPage){
        if (postView == null){
            throw new NullPointerException("postView can't be null");
        }
        this.postView = postView;
        setCurrentPage(currentPage);
    }

    @Override
    public void loadPosts(int pageIndex) {
        LogUtils.d(TAG,"loadPosts :"+pageIndex+" invoked");

        if (postView != null && postView.isActive()){
            switch (pageIndex){
                case PostFragment.PAGE_MAIN:
                    //TODO 访问model
                    LogUtils.d(TAG,"刷新主页！");
                    postView.showLoadingProgress();
                    new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            postView.showPosts(testDataMain());
                            postView.hindLoadingProgress();
                        }
                    },2000);
                    break;
                default:
                    postView.showPosts(testDataOther());
                    postView.hindLoadingProgress();
                    break;
            }

        }
    }

    @Override
    public void loadMorePosts(int pageIndex) {
        if (postView != null && postView.isActive()){
            switch (pageIndex){
                case PostFragment.PAGE_MAIN:
                    //TODO 访问model
                    postView.showLoadingMoreProgress();
                    new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            postView.showPosts(testDataMore());
                            postView.hindLoadingMoreProgress();
                        }
                    },2000);
                    break;
                default:
                    postView.showLoadingMoreProgress();
                    postView.showPosts(testDataMore());
                    postView.hindLoadingMoreProgress();
                    break;
            }

        }
    }

    @Override
    public void openPostDetails(PostEntity postEntity) {
        if (postEntity == null){
            throw new NullPointerException(" postPresenter:postEntity is null");
        }
        if (postView!= null && postView.isActive()){
            postView.showPostDetail(postEntity.getId());
        }
    }


    @Override
    public void start() {
        loadPosts(currentPage);
    }


    private ArrayList<PostEntity> testDataMain(){
        PostEntity one = new PostEntity();
        one.setId("12314");
        one.setUserName("德玛西亚");
        one.setPoint("3");
        one.setPostTitle("大骗子");
        one.setPostType("网络诈骗");
        one.setPostContent("今天被骗了，好气啊");
        one.setPostCommentCount(12);
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
        one.setPostType("网络诈骗");
        one.setPostContent("今天被骗了，好气啊");
        one.setPostCommentCount(12);
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
        one.setPostType("网络诈骗");
        one.setPostContent("今天被骗了，好气啊");
        one.setPostCommentCount(12);
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
