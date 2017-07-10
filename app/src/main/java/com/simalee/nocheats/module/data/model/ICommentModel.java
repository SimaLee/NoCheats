package com.simalee.nocheats.module.data.model;

import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public interface ICommentModel {

    interface ReleaseCommentCallback{
        void onError(Exception e);
        void onReleaseSuccess();
        void onReleaseFailure();
    }

    interface DeleteCommentCallback{
        void onError(Exception e);
        void onCommentDeleteSuccess();
        void onCommentDeleteFailure();
    }

    interface ReplyReplyCallback{
        void onError(Exception e);
        void onReplyReplySuccess();
        void onReplyReplyFailure();
    }

    interface LoadFloorReplyCallback{
        void onError(Exception e);
        void onLoadFloorReplySuccess(List<ReplyReplyEntity> replyReplyEntityList);
        void onLoadFloorReplyFailure();
    }


    void releaseComment(String userId, String floorId, String content,
                        String photoUrls,ICommentModel.ReleaseCommentCallback callback);

    void deleteComment(String userId,String commentId,
                       DeleteCommentCallback callback);

    void replyComment(String floorId,String userId,String content,
                      ICommentModel.ReleaseCommentCallback callback);

    void replyReply(String floorId,String subjectUserId,String objectUserId,
                    String objectUserName,String content, ReplyReplyCallback callback);

    void loadFloorReply(String floorId,LoadFloorReplyCallback callback);
}
