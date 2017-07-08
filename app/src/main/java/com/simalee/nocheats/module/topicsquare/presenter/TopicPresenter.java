package com.simalee.nocheats.module.topicsquare.presenter;

import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;
import com.simalee.nocheats.module.topicsquare.contract.TopicsContract;

/**
 * Created by Lee Sima on 2017/7/7.
 */

public class TopicPresenter implements TopicsContract.Presenter {

    private static final String TAG = TopicPresenter.class.getSimpleName();

    private TopicsContract.AllTopicsView mAllTopicsView;

    public TopicPresenter(BaseView<TopicsContract.Presenter> topicView){

        if (topicView instanceof TopicsContract.AllTopicsView){
            mAllTopicsView = (TopicsContract.AllTopicsView) topicView;
        }
    }

    /**
     * 加载主题
     */
    @Override
    public void loadTopics() {

    }

    @Override
    public void loadMoreTopics(String lastTimeStr) {

    }

    @Override
    public void openTopicDetail(TopicEntity topicEntity) {
        if (mAllTopicsView != null){

        }
    }

    @Override
    public void start() {

    }
}
