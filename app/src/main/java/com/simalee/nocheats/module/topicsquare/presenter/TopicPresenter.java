package com.simalee.nocheats.module.topicsquare.presenter;

import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;
import com.simalee.nocheats.module.data.model.IPostModel;
import com.simalee.nocheats.module.data.model.ITopicModel;
import com.simalee.nocheats.module.data.model.TopicModel;
import com.simalee.nocheats.module.topicsquare.contract.TopicsContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee Sima on 2017/7/7.
 */

public class TopicPresenter implements TopicsContract.Presenter {

    private static final String TAG = TopicPresenter.class.getSimpleName();

    private TopicsContract.AllTopicsView mAllTopicsView;

    private TopicsContract.NewTopicView mNewTopicView;


    private ITopicModel mTopicModel;

    public TopicPresenter(BaseView<TopicsContract.Presenter> topicView){
        if (topicView == null){
            throw new NullPointerException("topicView is empty!");
        }
        if (topicView instanceof TopicsContract.AllTopicsView){
            mAllTopicsView = (TopicsContract.AllTopicsView) topicView;
        }

        if (topicView instanceof TopicsContract.NewTopicView){
            mNewTopicView = (TopicsContract.NewTopicView) topicView;
        }
        mTopicModel = new TopicModel();
    }

    /**
     * 加载主题
     */
    @Override
    public void loadTopics() {

        if (mAllTopicsView != null && mAllTopicsView.isActive()){

            mAllTopicsView.showLoadingProgress();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String last_time = dateFormat.format(new Date());

            mTopicModel.loadTopics(last_time, new ITopicModel.LoadTopicsCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error :"+e.toString());
                    if (mAllTopicsView.isActive()){
                        mAllTopicsView.hideLoadingProgress();
                        mAllTopicsView.showLoadingFailure();
                    }
                }

                @Override
                public void onLoadTopicsSuccess(List<TopicEntity> topicEntities) {
                    if (mAllTopicsView.isActive()){
                        mAllTopicsView.hideLoadingProgress();
                        if (topicEntities == null || topicEntities.size() == 0){
                            mAllTopicsView.showNoTopics();
                        }else{

                            mAllTopicsView.showTopics(topicEntities);
                        }
                    }
                }

                @Override
                public void onLoadTopicsFailure() {
                    if (mAllTopicsView.isActive()){
                        mAllTopicsView.hideLoadingProgress();
                        mAllTopicsView.showLoadingFailure();
                    }
                }
            });

        }
    }

    @Override
    public void loadMoreTopics(String lastTimeStr) {
        if (mAllTopicsView != null && mAllTopicsView.isActive()){

            mAllTopicsView.showLoadingMoreProgress();

            mTopicModel.loadTopics(lastTimeStr, new ITopicModel.LoadTopicsCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error :"+e.toString());
                    if (mAllTopicsView.isActive()){
                        mAllTopicsView.hideLoadingMoreProgress();
                        mAllTopicsView.showLoadingMoreFailure();
                    }
                }

                @Override
                public void onLoadTopicsSuccess(List<TopicEntity> postEntities) {
                    if (mAllTopicsView.isActive()){
                        mAllTopicsView.hideLoadingMoreProgress();
                        mAllTopicsView.showLoadMoreTopics(postEntities);
                    }
                }

                @Override
                public void onLoadTopicsFailure() {
                    if (mAllTopicsView.isActive()){
                        mAllTopicsView.hideLoadingMoreProgress();
                        mAllTopicsView.showLoadingMoreFailure();
                    }
                }
            });

        }
    }

    @Override
    public void openTopicDetail(TopicEntity topicEntity) {
        if (mAllTopicsView != null){
            mAllTopicsView.showTopicDetail(topicEntity.getId(),
                    topicEntity.getTopicTime(),topicEntity.getTopicTitle());
        }
    }

    @Override
    public void releaseTopic(String userId, String topicTitle, String topicContent, String topicPicUrls) {
        if (mNewTopicView != null){
            mNewTopicView.showLoadingProgress();


            mTopicModel.releaseTopic(userId, topicTitle, topicContent, topicPicUrls, new IPostModel.ReleasePostCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error: "+e.toString());
                    if (mNewTopicView != null){
                        mNewTopicView.showPostReleasedFailed("系统发生了未知的错误");
                    }
                }

                @Override
                public void onPostReleasedSuccess() {
                    if (mNewTopicView != null){
                        mNewTopicView.hideLoadingProgress();
                        mNewTopicView.showPostReleasedSuccess();
                    }
                }

                @Override
                public void onPostReleasedFailure(String reason) {
                    if (mNewTopicView != null){
                        mNewTopicView.hideLoadingProgress();
                        mNewTopicView.showPostReleasedFailed(reason);
                    }
                }
            });

        }
    }

    @Override
    public void start() {
        // do nothing
    }
}
