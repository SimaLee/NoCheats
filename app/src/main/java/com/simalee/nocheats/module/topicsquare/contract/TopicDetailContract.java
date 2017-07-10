package com.simalee.nocheats.module.topicsquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public interface TopicDetailContract {

    interface TopicDetailView extends BaseView<Presenter>{

        void showTopicDetail(List<ICommentEntity> topicCommentEntityList);
        void showLoadTopicDetailFailure();

        void showLoadMoreComments(List<ICommentEntity> appendCommentEntityList);
        void showLoadMoreCommentsFailure();

        void showCommentSuccess();
        void showCommentFailure();

        void showLoadingProgress();
        void hideLoadingProgress();

        void showLoadingMoreProgress();
        void hideLoadingMoreProgress();

    }

    interface Presenter extends BasePresenter{
        void loadTopicDetail(String topicId,String topicTime);
        void loadMoreComments(String topicId,String topicTime);

        void releaseComment(String userId,String floorId,String content,String photoUrls);

        void replyComment(String floorId,String userId,String content);

    }
}
