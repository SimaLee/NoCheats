package com.simalee.nocheats.module.data.entity.post;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostEntity {


    /**
     *  帖子Id
     */
    String id;
    /**
     * 用户头像url
     */
    String avatar;
    /**
     * 用户名
     */
    String userName;


    /**
     * 用户的经验值
     */
    String point;
    /**
     * 帖子类型
     */
    String postType;
    /**
     * 帖子标题
     */
    String postTitle;
    /**
     * 帖子内容
     */
    String postContent;

    /**
     * 帖子的图片url
     */
    String postPhotoUrl;

    /**
     * 帖子评论数
     */
    int postCommentCount;

    public PostEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(int postCommentCount) {
        this.postCommentCount = postCommentCount;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostPhotoUrl() {
        return postPhotoUrl;
    }

    public void setPostPhotoUrl(String postPhotoUrl) {
        this.postPhotoUrl = postPhotoUrl;
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

    @Override
    public String toString() {
        return "PostEntity{" +
                "avatar='" + avatar + '\'' +
                ", id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", postType='" + postType + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postPhotoUrl='" + postPhotoUrl + '\'' +
                ", postCommentCount=" + postCommentCount +
                '}';
    }

}
