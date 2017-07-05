package com.simalee.nocheats.module.data.entity.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostEntity implements Serializable{

    private static final long serialVersionUID = 10000L;

    /*{
         "p_id": "599cbdc9c0df4b59be09c4f13221f09720c1eac625414c2bbabfa92f2bbc3748",
         "p_title": "test",
         "u_id": "20c1eac625414c2bbabfa92f2bbc3748",
         "u_name": "15622357729",
         "head_logo": "20c1eac625414c2bbabfa92f2bbc3748.jpg",
         "point": 12,
         "content": "呜啦啦啦啦啦",
         "pic": [
            "fwq.jpg",
            "wwc.jpg"
          ],
         "time": "2017-06-24 12:16:53",
         "level": 0,
         "page_view": 1
    }*/


    /**
     *  帖子Id
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
     * 用户名
     */
    @SerializedName("u_name")
    String userName;


    /**
     * 用户的经验值
     */
    @SerializedName("point")
    String point;
    /**
     * 帖子类型
     */
    String postType;
    /**
     * 帖子标题
     */
    @SerializedName("p_title")
    String postTitle;
    /**
     * 帖子内容
     */
    @SerializedName("content")
    String postContent;


    /**
     * 帖子的图片url
     */
    @SerializedName("pic")
    List<String> postPhotoUrlList;

    /**
     * 帖子被查看数
     */
    @SerializedName("page_view")
    int postViewCount;

    /**
     * 帖子发布时间
     */
    @SerializedName("time")
    String postTime;


    public PostEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPostViewCount() {
        return postViewCount;
    }

    public void setPostViewCount(int postViewCount) {
        this.postViewCount = postViewCount;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }



    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public List<String> getPostPhotoUrlList() {
        return postPhotoUrlList;
    }

    public void setPostPhotoUrlList(List<String> postPhotoUrlList) {
        this.postPhotoUrlList = postPhotoUrlList;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "avatar='" + avatar + '\'' +
                ", id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", point='" + point + '\'' +
                ", postType='" + postType + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postPhotoUrlList=" + postPhotoUrlList +
                ", postViewCount=" + postViewCount +
                ", postTime='" + postTime + '\'' +
                '}';
    }
}
