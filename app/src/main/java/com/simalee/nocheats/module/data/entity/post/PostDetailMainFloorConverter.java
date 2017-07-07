package com.simalee.nocheats.module.data.entity.post;

import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/6/21.
 */

public class PostDetailMainFloorConverter implements ICommentEntity {

    //为了在RecyclerView中使用做的转换

    private PostDetailFloorEntity mPostDetailFloorEntity;

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    /**
     *  帖子的title
     */
    private String postTitle;

    public PostDetailMainFloorConverter(String postTitle,PostDetailFloorEntity postDetailMainFloorEntity) {
        this.postTitle = postTitle;
        mPostDetailFloorEntity = postDetailMainFloorEntity;
    }

    public String getCommentContent() {
        return mPostDetailFloorEntity.getCommentContent();
    }

    public void setCommentContent(String commentContent) {
        mPostDetailFloorEntity.setCommentContent(commentContent);
    }

    public String getCommentId() {
        return mPostDetailFloorEntity.getCommentId();
    }

    public void setCommentId(String commentId) {
        mPostDetailFloorEntity.setCommentId(commentId);
    }

    public int getCommentStorey() {
        return mPostDetailFloorEntity.getCommentStorey();
    }

    public void setCommentStorey(int commentStorey) {
        mPostDetailFloorEntity.setCommentStorey(commentStorey);
    }

    public String getCommentUserAvatar() {
        return mPostDetailFloorEntity.getCommentUserAvatar();
    }

    public void setCommentUserAvatar(String commentUserAvatar) {
        mPostDetailFloorEntity.setCommentUserAvatar(commentUserAvatar);
    }

    public String getCommentUserId() {
        return mPostDetailFloorEntity.getCommentUserId();
    }

    public void setCommentUserId(String commentUserId) {
        mPostDetailFloorEntity.setCommentId(commentUserId);
    }

    public String getCommentUserName() {
        return mPostDetailFloorEntity.getCommentUserName();
    }

    public void setCommentUserName(String commentUserName) {
        mPostDetailFloorEntity.setCommentUserName(commentUserName);
    }

    public String getCommentUserPoint() {
        return mPostDetailFloorEntity.getCommentUserPoint();
    }

    public void setCommentUserPoint(String commentUserPoint) {
        mPostDetailFloorEntity.setCommentUserPoint(commentUserPoint);
    }

    public boolean isHost() {
        return mPostDetailFloorEntity.isHost();
    }

    public void setHost(boolean host) {
        mPostDetailFloorEntity.setHost(host);
    }


    public String getReplyTime() {
        return mPostDetailFloorEntity.getReplyTime();
    }

    public void setReplyTime(String replyTime) {
        mPostDetailFloorEntity.setReplyTime(replyTime);
    }

    public String getPostId() {
        return mPostDetailFloorEntity.getPostId();
    }

    public void setPostId(String postId) {
        mPostDetailFloorEntity.setPostId(postId);
    }

    public List<ReplyReplyEntity> getRepliesList() {
        return mPostDetailFloorEntity.getRepliesList();
    }

    public void setRepliesList(List<ReplyReplyEntity> repliesList) {
        mPostDetailFloorEntity.setRepliesList(repliesList);
    }


    @Override
    public String toString() {
        return "PostDetailMainFloorConverter{" +
                "mPostDetailFloorEntity=" + mPostDetailFloorEntity +
                ", postTitle='" + postTitle + '\'' +
                '}';
    }
}
