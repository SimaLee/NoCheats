package com.simalee.nocheats.module.data.entity.topic;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public class MyTopicGson {

    /*{
       "msg": 0,
           "posts": [
       {
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
       }
       ]
   }*/
    @SerializedName("msg")
    String msg;

    @SerializedName("posts")
    List<TopicEntity> topicEntityList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TopicEntity> getTopicEntityList() {
        return topicEntityList;
    }

    public void setTopicEntityList(List<TopicEntity> topicEntityList) {
        this.topicEntityList = topicEntityList;
    }

    @Override
    public String toString() {
        return "AllTopicGson{" +
                "msg='" + msg + '\'' +
                ", topicEntityList=" + topicEntityList +
                '}';
    }

}
