package com.simalee.nocheats.module.topicsquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public interface PreviousTopicContract {

    interface View extends BaseView<Presenter>{

        void showTopics(List<TopicEntity> topicEntities);
        void showLoadMoreTopics(List<TopicEntity> appendTopicEntities);

        void showTopicDetail(String topicId,String topicTime,String topicTitle);

        void showNoTopic();

        void showLoadingProgress();
        void hideLoadingProgress();

        void showLoadingMoreProgress();
        void hideLoadingMoreProgress();

        void showLoadingFailure();

        void showDeleteTopicSuccess();
        void showDeleteTopicFailure(String reason);

    }

    interface Presenter extends BasePresenter{

        void loadMyTopics(String userId);
        void loadMoreMyTopics(String userId,String lastTimeStr);

        void openTopicDetails(TopicEntity topicEntity);

        void deleteTopic(String userId,String topicId);
    }

}
