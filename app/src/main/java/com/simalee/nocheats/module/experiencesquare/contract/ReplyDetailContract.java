package com.simalee.nocheats.module.experiencesquare.contract;

import com.simalee.nocheats.common.base.BasePresenter;
import com.simalee.nocheats.common.base.BaseView;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public interface ReplyDetailContract {

    interface View extends BaseView<Presenter>{
        void showFloorReply(List<ReplyReplyEntity> replyEntityList);
        void showLoadFloorReplyFailed();

        void showNoReply();

        void showLoadingProgress();
        void hideLoadingProgress();

        void showCommentSuccess();
        void showCommentFailure();
    }

    interface Presenter extends BasePresenter{
        void loadFloorReply(String floorId);

        void replyComment(String floorId, String userId, String content);

        void replyReply(String floorId,String subjectUserId,String objectUserId,
                        String objectUserName,String content);
    }
}
