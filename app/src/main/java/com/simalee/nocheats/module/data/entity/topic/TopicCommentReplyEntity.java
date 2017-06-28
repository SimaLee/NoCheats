package com.simalee.nocheats.module.data.entity.topic;

import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicCommentReplyEntity implements ICommentEntity {
    /**
     * 评论的帖子的Id
     */
    String topicId;

    /**
     * 评论的Id
     */
    String commentId;
    /**
     * 发出评论的用户的Id
     */
    String commentUserId;
    /**
     * 发出评论的用户的名字
     */
    String commentUserName;
    /**
     * 发出评论的用户的头像
     */
    String commentUserAvatar;
    /**
     * 发出评论的是否是楼主
     */
    boolean isHost;

    /**
     * 发出评论的用户的经验值
     */
    String commentUserPoint;

    /**
     *  评论楼层数
     */
    int commentStorey;

    /**
     *  评论内容
     */
    String commentContent;

    /**
     *  楼中楼回复数据 //此处可以判断楼中楼回复数量
     */
    List<ReplyReplyEntity> repliesList;

    public TopicCommentReplyEntity() {
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getCommentStorey() {
        return commentStorey;
    }

    public void setCommentStorey(int commentStorey) {
        this.commentStorey = commentStorey;
    }

    public String getCommentUserAvatar() {
        return commentUserAvatar;
    }

    public void setCommentUserAvatar(String commentUserAvatar) {
        this.commentUserAvatar = commentUserAvatar;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserPoint() {
        return commentUserPoint;
    }

    public void setCommentUserPoint(String commentUserPoint) {
        this.commentUserPoint = commentUserPoint;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public List<ReplyReplyEntity> getRepliesList() {
        return repliesList;
    }

    public void setRepliesList(List<ReplyReplyEntity> repliesList) {
        this.repliesList = repliesList;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "TopicCommentReplyEntity{" +
                "commentContent='" + commentContent + '\'' +
                ", topicId='" + topicId + '\'' +
                ", commentId='" + commentId + '\'' +
                ", commentUserId='" + commentUserId + '\'' +
                ", commentUserName='" + commentUserName + '\'' +
                ", commentUserAvatar='" + commentUserAvatar + '\'' +
                ", isHost=" + isHost +
                ", commentUserPoint='" + commentUserPoint + '\'' +
                ", commentStorey=" + commentStorey +
                ", repliesList=" + repliesList +
                '}';
    }
}
