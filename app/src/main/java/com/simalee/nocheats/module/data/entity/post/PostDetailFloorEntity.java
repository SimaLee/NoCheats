package com.simalee.nocheats.module.data.entity.post;

import com.google.gson.annotations.SerializedName;
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/21.
 */

public class PostDetailFloorEntity implements ICommentEntity,Serializable,Comparable<PostDetailFloorEntity> {

    private static final long serialVersionUID = 10002L;

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


    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public List<ReplyReplyEntity> getRepliesList() {
        return repliesList;
    }

    public void setRepliesList(List<ReplyReplyEntity> repliesList) {
        this.repliesList = repliesList;
    }

    public PostDetailFloorEntity() {
    }

    @Override
    public String toString() {
        return "PostDetailFloorEntity{" +
                "commentContent='" + commentContent + '\'' +
                ", postId='" + postId + '\'' +
                ", commentId='" + commentId + '\'' +
                ", commentUserId='" + commentUserId + '\'' +
                ", commentUserName='" + commentUserName + '\'' +
                ", commentUserAvatar='" + commentUserAvatar + '\'' +
                ", isHost=" + isHost +
                ", replyTime='" + replyTime + '\'' +
                ", commentUserPoint='" + commentUserPoint + '\'' +
                ", commentStorey=" + commentStorey +
                ", picUrlList=" + picUrlList +
                ", praiseCount=" + praiseCount +
                ", repliesList=" + repliesList +
                '}';
    }

    /**
     * 评论的帖子的Id
     */
    @SerializedName("id")
    String postId;

    /**
     * 评论的Id
     */
    @SerializedName("f_id")
    String commentId;
    /**
     * 发出评论的用户的Id
     */
    @SerializedName("u_id")
    String commentUserId;
    /**
     * 发出评论的用户的名字
     */
    @SerializedName("u_name")
    String commentUserName;
    /**
     * 发出评论的用户的头像
     */
    @SerializedName("head_logo")
    String commentUserAvatar;
    /**
     * 发出评论的是否是楼主
     */
    @SerializedName("host")
    boolean isHost;

    /**
     * 发出评论的时间
     */
    @SerializedName("time")
    String replyTime;
    /**
     * 发出评论的用户的经验值
     */
    String commentUserPoint;

    /**
     *  评论楼层数
     */
    @SerializedName("level")
    int commentStorey;

    /**
     *  评论内容
     */
    @SerializedName("content")
    String commentContent;

    /**
     * 楼层回复的图片url
     */
    @SerializedName("pic")
    List<String> picUrlList;


    /**
     * 楼层获赞数
     */
    @SerializedName("praise")
    int praiseCount;

    /**
     *  楼中楼回复数据 //此处可以判断楼中楼回复数量
     */
    @SerializedName("reply")
     List<ReplyReplyEntity> repliesList;

    @Override
    public int compareTo(PostDetailFloorEntity o) {
        return this.getCommentStorey() - o.getCommentStorey();
    }
}
