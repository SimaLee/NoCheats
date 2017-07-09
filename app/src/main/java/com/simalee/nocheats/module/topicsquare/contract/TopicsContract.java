package com.simalee.nocheats.module.topicsquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/7.
 */

public interface TopicsContract {


    interface AllTopicsView extends BaseView<Presenter>{

        void showTopics(List<TopicEntity> topicEntityList);
        void showLoadMoreTopics(List<TopicEntity> appendTopicEntityList);

        void showLoadingProgress();
        void showLoadingMoreProgress();

        void hideLoadingProgress();
        void hideLoadingMoreProgress();

        void showLoadingFailure();
        void showLoadingMoreFailure();

        void showTopicDetail(String topicId,String topicTime,String topicTitle);

        boolean isActive();
    }

    interface NewTopicView extends BaseView<Presenter>{
        void showLoadingProgress();
        void hideLoadingProgress();

        void showPostReleasedSuccess();
        void showPostReleasedFailed(String reason);

    }

    interface Presenter extends BasePresenter{

        void loadTopics();
        void loadMoreTopics(String lastTimeStr);

        void openTopicDetail(TopicEntity topicEntity);

        void releaseTopic(String userId,String topicTitle,String topicContent,String topicPicUrls);
    }
}
