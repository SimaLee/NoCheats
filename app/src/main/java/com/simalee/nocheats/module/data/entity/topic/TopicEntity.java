package com.simalee.nocheats.module.data.entity.topic;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicEntity {

    /**
     * 主题Id
     */
    String id;

    /**
     * 用户头像url
     */
    String avatar;

    /**
     * 用户名字
     */
    String userName;

    /**
     * 用户经验值
     */
    String point;

    /**
     * 主题类型
     */
    String topicType;

    /**
     *  主题title
     */
    String topicTitle;

    /**
     *  主题内容
     */
    String topicContent;

    /**
     * 主题的图片url
     */
    String topicPhotoUrl;


    /**
     *  主题评论数
     */
    int topicCommentCount;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getTopicCommentCount() {
        return topicCommentCount;
    }

    public void setTopicCommentCount(int topicCommentCount) {
        this.topicCommentCount = topicCommentCount;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public String getTopicPhotoUrl() {
        return topicPhotoUrl;
    }

    public void setTopicPhotoUrl(String topicPhotoUrl) {
        this.topicPhotoUrl = topicPhotoUrl;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicType() {
        return topicType;
    }

    public void setTopicType(String topicType) {
        this.topicType = topicType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "avatar='" + avatar + '\'' +
                ", id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", point='" + point + '\'' +
                ", topicType='" + topicType + '\'' +
                ", topicTitle='" + topicTitle + '\'' +
                ", topicContent='" + topicContent + '\'' +
                ", topicPhotoUrl='" + topicPhotoUrl + '\'' +
                ", topicCommentCount=" + topicCommentCount +
                '}';
    }
}
