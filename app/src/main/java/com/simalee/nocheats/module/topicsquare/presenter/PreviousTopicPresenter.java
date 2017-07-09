package com.simalee.nocheats.module.topicsquare.presenter;

import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;
import com.simalee.nocheats.module.data.model.ITopicModel;
import com.simalee.nocheats.module.data.model.TopicModel;
import com.simalee.nocheats.module.experiencesquare.contract.PreviousPostContract;
import com.simalee.nocheats.module.topicsquare.contract.PreviousTopicContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public class PreviousTopicPresenter implements PreviousTopicContract.Presenter {

    private static final String TAG = PreviousTopicPresenter.class.getSimpleName();

    private PreviousTopicContract.View mPreviousTopicView;
    private ITopicModel mTopicModel;

    public PreviousTopicPresenter(PreviousTopicContract.View previousTopicView){
        if (previousTopicView == null){
            throw new NullPointerException("previousTopicView is empty!");
        }
        mPreviousTopicView = previousTopicView;
        mTopicModel = new TopicModel();
    }

    @Override
    public void loadMyTopics(String userId) {
        if (mPreviousTopicView != null){
            mPreviousTopicView.showLoadingProgress();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lastTimeStr = dateFormat.format(new Date());

            mTopicModel.loadMyTopics(userId, lastTimeStr, new ITopicModel.LoadMyTopicCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error: "+e.toString());
                    mPreviousTopicView.hideLoadingProgress();
                    mPreviousTopicView.showLoadingFailure();
                }

                @Override
                public void onLoadTopicsSuccess(List<TopicEntity> topicEntities) {
                    if (mPreviousTopicView != null){
                        mPreviousTopicView.hideLoadingProgress();
                        mPreviousTopicView.showTopics(topicEntities);
                    }
                }

                @Override
                public void onLoadTopicFailure() {
                    if (mPreviousTopicView != null){
                        mPreviousTopicView.hideLoadingProgress();
                        mPreviousTopicView.showLoadingFailure();
                    }
                }
            });
        }
    }

    @Override
    public void loadMoreMyTopics(String userId, String lastTimeStr) {
        if (mPreviousTopicView != null){
            mPreviousTopicView.showLoadingMoreProgress();


            mTopicModel.loadMyTopics(userId, lastTimeStr, new ITopicModel.LoadMyTopicCallback() {
                @Override
                public void onError(Exception e) {
                    LogUtils.e(TAG,"error: "+e.toString());
                    mPreviousTopicView.hideLoadingMoreProgress();
                    mPreviousTopicView.showLoadingFailure();
                }

                @Override
                public void onLoadTopicsSuccess(List<TopicEntity> topicEntities) {
                    if (mPreviousTopicView != null){
                        mPreviousTopicView.hideLoadingMoreProgress();
                        mPreviousTopicView.showLoadMoreTopics(topicEntities);
                    }
                }

                @Override
                public void onLoadTopicFailure() {
                    if (mPreviousTopicView != null){
                        mPreviousTopicView.hideLoadingMoreProgress();
                        mPreviousTopicView.showLoadingFailure();
                    }
                }
            });
        }
    }

    @Override
    public void openTopicDetails(TopicEntity topicEntity) {
        if (topicEntity != null && mPreviousTopicView != null){
            mPreviousTopicView.showTopicDetail(topicEntity.getId(),
                    topicEntity.getTopicTime(),topicEntity.getTopicTitle());
        }
    }

    @Override
    public void start() {
        // do nothing
    }



}
