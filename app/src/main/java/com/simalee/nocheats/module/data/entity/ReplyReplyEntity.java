package com.simalee.nocheats.module.data.entity;

import java.io.Serializable;

/**
 * Created by Lee Sima on 2017/6/21.
 */

public class ReplyReplyEntity implements ICommentEntity,Serializable{

    private static final long serialVersionUID = 10001L;

    public ReplyReplyEntity() {
    }

    String testMsg;

    /**
     *  回复人ID
     */
    String subjectId;
    /**
     *  回复人昵称
     */
    String subjectName;

    /**
     *  回复人是否楼主
     */
    boolean isHost;


    /**
     *  有回复人
     */
    boolean isReply = false;
    /**
     *  被回复人昵称
     */
    String objectName;

    /**
     *  回复内容
     */
    String content;

    //暂无回复时间


    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    @Override
    public String toString() {
        return "ReplyReplyEntity{" +
                "content='" + content + '\'' +
                ", testMsg='" + testMsg + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", isHost=" + isHost +
                ", isReply=" + isReply +
                ", objectName='" + objectName + '\'' +
                '}';
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

    public String getTestMsg() {
        return testMsg;
    }

    public void setTestMsg(String testMsg) {
        this.testMsg = testMsg;
    }

    public ReplyReplyEntity(String testMsg) {
        this.testMsg = testMsg;
    }

}
