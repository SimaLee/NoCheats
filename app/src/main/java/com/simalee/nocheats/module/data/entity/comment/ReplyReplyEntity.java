package com.simalee.nocheats.module.data.entity.comment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lee Sima on 2017/6/21.
 */

public class ReplyReplyEntity implements ICommentEntity,Serializable{

    private static final long serialVersionUID = 10001L;

    public ReplyReplyEntity() {
    }


    /**
     *  回复人ID
     */
    @SerializedName("u_id")
    String subjectId;
    /**
     *  回复人昵称
     */
    @SerializedName("u_name")
    String subjectName;

    /**
     *  回复人是否楼主
     */
    @SerializedName("u_host")
    boolean isHost;


    /**
     *  有回复人
     */
    boolean isReply = false;
    /**
     *  被回复人昵称
     */
    @SerializedName("r_name")
    String objectName;


    /**
     * 被回复人id
     */
    @SerializedName("r_id")
    String objectId;
    /**
     *  回复内容
     */
    @SerializedName("content")
    String content;

    /**
     * 回复时间
     */
    @SerializedName("time")
    String replyTime;


    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "ReplyReplyEntity{" +
                "content='" + content + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", isHost=" + isHost +
                ", isReply=" + isReply +
                ", objectName='" + objectName + '\'' +
                ", objectId='" + objectId + '\'' +
                ", replyTime='" + replyTime + '\'' +
                '}';
    }
}
