package com.simalee.nocheats.module.data.model;

import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public interface ITopicModel {

    interface LoadTopicsCallback{
        void onError(Exception e);
        void onLoadTopicsSuccess(List<TopicEntity> topicEntities);
        void onLoadTopicsFailure();
    }

    interface ReleaseTopicCallback{
        void onError(Exception e);
        void onTopicReleasedSuccess();
        void onTopicReleasedFailure(String reason);
    }

    interface LoadTopicDetailCallback{
        void onError(Exception e);
        void onLoadTopicDetailSuccess(List<ICommentEntity> topicDetail);
        void onLoadTopicDetailFailure();
    }

    interface LoadMyTopicCallback{
        void onError(Exception e);
        void onLoadTopicsSuccess(List<TopicEntity> topicEntities);
        void onLoadTopicFailure();
    }


    void loadTopics(String lastTimeStr,LoadTopicsCallback callback);

    void releaseTopic(String userId, String topicTitle, String topicContent,
                      String topicPicUrls, IPostModel.ReleasePostCallback callback);

    void loadTopicDetail(String topicId,String lastTimeStr,LoadTopicDetailCallback callback);

    void loadMyTopics(String userId,String lastTimeStr,LoadMyTopicCallback callback);

}
