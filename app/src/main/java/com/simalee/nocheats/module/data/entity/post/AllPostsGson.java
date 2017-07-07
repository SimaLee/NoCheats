package com.simalee.nocheats.module.data.entity.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/5.
 */

public class AllPostsGson {
    /*{
        "msg": 0,
        "posts": [
        {
            "p_id": "c5774acdc534486e967037f31e26a74920c1eac625414c2bbabfa92f2bbc3748",
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
            "time": "2017-06-24 15:08:45",
                "level": 0,
                "page_view": 2
        },
        {
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
        }
        ]
    }*/
    @SerializedName("msg")
    private String msg;

    @SerializedName("posts")
    private List<PostEntity> postEntityList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PostEntity> getPostEntityList() {
        return postEntityList;
    }

    public void setPostEntityList(List<PostEntity> postEntityList) {
        this.postEntityList = postEntityList;
    }

    @Override
    public String toString() {
        return "AllPostsGson{" +
                "msg='" + msg + '\'' +
                ", postEntityList=" + postEntityList +
                '}';
    }
}
