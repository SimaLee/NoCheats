package com.simalee.nocheats.module.data.entity.topic;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicEntity {


    /*{
        "p_id": "2c1fd1cf9a5d4b8ab74f7c19eb9771ed74a1a16a65b94d489ed1d30cfc6d668f",
            "p_title": "#who are you#",
            "p_type": -1,
            "u_id": "74a1a16a65b94d489ed1d30cfc6d668f",
            "u_name": "嘎嘎嘎",
            "head_logo": "noCheatsImage/head/74a1a16a65b94d489ed1d30cfc6d668f.jpg",
            "point": 90,
            "content": "I'm Johny",
            "pic": [
        ""
        ],
        "time": "2017-07-09 10:27:29",
            "level": 1,
            "page_view": 0
    }*/
    /**
     * 主题Id
     */
    @SerializedName("p_id")
    String id;

    /**
     * 用户头像url
     */
    @SerializedName("head_logo")
    String avatar;

    /**
     * 用户Id
     */
    @SerializedName("u_id")
    String userId;

    /**
     * 用户名字
     */
    @SerializedName("u_name")
    String userName;

    /**
     * 用户经验值
     */
    @SerializedName("point")
    String point;

    /**
     * 主题类型
     */

    String topicType;

    /**
     *  主题title
     */
    @SerializedName("p_title")
    String topicTitle;

    /**
     *  主题内容
     */
    @SerializedName("content")
    String topicContent;

    /**
     * 主题的图片url
     */
    @SerializedName("pic")
    List<String> topicPhotoUrlList;


    /**
     *  主题评论数
     */
    @SerializedName("level")
    int topicCommentCount;

    /**
     * 主题被查看数
     */
    @SerializedName("page_view")
    int topicViewCount;

    /**
     * 主题发布时间
     */
    @SerializedName("time")
    String topicTime;

    public List<String> getTopicPhotoUrlList() {
        return topicPhotoUrlList;
    }

    public void setTopicPhotoUrlList(List<String> topicPhotoUrlList) {
        this.topicPhotoUrlList = topicPhotoUrlList;
    }

    public String getTopicTime() {
        return topicTime;
    }

    public void setTopicTime(String topicTime) {
        this.topicTime = topicTime;
    }

    public int getTopicViewCount() {
        return topicViewCount;
    }

    public void setTopicViewCount(int topicViewCount) {
        this.topicViewCount = topicViewCount;
    }

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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "avatar='" + avatar + '\'' +
                ", id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", point='" + point + '\'' +
                ", topicType='" + topicType + '\'' +
                ", topicTitle='" + topicTitle + '\'' +
                ", topicContent='" + topicContent + '\'' +
                ", topicPhotoUrlList=" + topicPhotoUrlList +
                ", topicCommentCount=" + topicCommentCount +
                ", topicViewCount=" + topicViewCount +
                ", topicTime='" + topicTime + '\'' +
                '}';
    }
}
