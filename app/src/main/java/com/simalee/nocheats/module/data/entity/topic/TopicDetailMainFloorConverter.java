package com.simalee.nocheats.module.data.entity.topic;

import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;

import java.util.List;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicDetailMainFloorConverter implements ICommentEntity {

    //为了在RecyclerView中使用做的转换

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    /**
     *  主题的title
     */
    private String topicTitle;

    private TopicDetailFloorEntity mTopicDetailFloorEntity;

    public TopicDetailMainFloorConverter(String topicTitle,TopicDetailFloorEntity topicDetailFloorEntity){
        setTopicTitle(topicTitle);
        mTopicDetailFloorEntity = topicDetailFloorEntity;
    }

    public String getCommentContent() {
        return mTopicDetailFloorEntity.getCommentContent();
    }

    public void setCommentContent(String commentContent) {
        mTopicDetailFloorEntity.setCommentContent(commentContent);
    }

    public String getCommentId() {
        return mTopicDetailFloorEntity.getCommentId();
    }

    public void setCommentId(String commentId) {
        mTopicDetailFloorEntity.setCommentId(commentId);
    }

    public int getCommentStorey() {
        return mTopicDetailFloorEntity.getCommentStorey();
    }

    public void setCommentStorey(int commentStorey) {
       mTopicDetailFloorEntity.setCommentStorey(commentStorey);
    }

    public String getCommentUserAvatar() {
        return mTopicDetailFloorEntity.getCommentUserAvatar();
    }

    public void setCommentUserAvatar(String commentUserAvatar) {
        mTopicDetailFloorEntity.setCommentUserAvatar(commentUserAvatar);
    }

    public String getCommentUserId() {
        return mTopicDetailFloorEntity.getCommentUserId();
    }

    public void setCommentUserId(String commentUserId) {
        mTopicDetailFloorEntity.setCommentUserId(commentUserId);
    }

    public String getCommentUserName() {
        return mTopicDetailFloorEntity.getCommentUserName();
    }

    public void setCommentUserName(String commentUserName) {
        mTopicDetailFloorEntity.setCommentUserName(commentUserName);
    }

    public String getCommentUserPoint() {
        return mTopicDetailFloorEntity.getCommentUserPoint();
    }

    public void setCommentUserPoint(String commentUserPoint) {
        mTopicDetailFloorEntity.setCommentUserPoint(commentUserPoint);
    }

    public boolean isHost() {
        return mTopicDetailFloorEntity.isHost();
    }

    public void setHost(boolean host) {
        mTopicDetailFloorEntity.setHost(host);
    }

    public List<ReplyReplyEntity> getRepliesList() {
        return mTopicDetailFloorEntity.getRepliesList();
    }

    public void setRepliesList(List<ReplyReplyEntity> repliesList) {
        mTopicDetailFloorEntity.setRepliesList(repliesList);
    }

    public String getTopicId() {
        return mTopicDetailFloorEntity.getTopicId();
    }

    public void setTopicId(String topicId) {
        mTopicDetailFloorEntity.setTopicId(topicId);
    }

    public String getReplyTime() {
        return mTopicDetailFloorEntity.getReplyTime();
    }

    public void setReplyTime(String replyTime) {
        mTopicDetailFloorEntity.setReplyTime(replyTime);
    }

    public List<String> getPicUrlList() {
        return mTopicDetailFloorEntity.getPicUrlList();
    }

    public void setPicUrlList(List<String> picUrlList) {
        mTopicDetailFloorEntity.setPicUrlList(picUrlList);
    }

    public int getPraiseCount() {
        return mTopicDetailFloorEntity.getPraiseCount();
    }

    public void setPraiseCount(int praiseCount) {
        mTopicDetailFloorEntity.setPraiseCount(praiseCount);
    }

    @Override
    public String toString() {
        return "TopicDetailMainFloorConverter{" +
                "topicTitle='" + topicTitle + '\'' +
                ", mTopicDetailFloorEntity=" + mTopicDetailFloorEntity +
                '}';
    }
}
