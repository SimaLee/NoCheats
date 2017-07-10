package com.simalee.nocheats.module.data.entity.comment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public class AllReplyGson {

    @SerializedName("msg")
    String msg;

    @SerializedName("replies")
    List<ReplyReplyEntity> replyReplyEntityList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ReplyReplyEntity> getReplyReplyEntityList() {
        return replyReplyEntityList;
    }

    public void setReplyReplyEntityList(List<ReplyReplyEntity> replyReplyEntityList) {
        this.replyReplyEntityList = replyReplyEntityList;
    }

    @Override
    public String toString() {
        return "AllReplyGson{" +
                "msg='" + msg + '\'' +
                ", replyReplyEntityList=" + replyReplyEntityList +
                '}';
    }
}
