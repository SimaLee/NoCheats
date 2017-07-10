package com.simalee.nocheats.module.data.entity.topic;

import com.google.gson.annotations.SerializedName;
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public class TopicDetailGson {

    /*{
            "msg": 0,
                "floors": [
            {
                "id": "65fcc141f3ed4ecda04eb45b90ad4850",
                    "f_id": "ffc63f0243c74a5aa5fd8bfd2f206c6974a1a16a65b94d489ed1d30cfc6d668f",
                    "u_id": "74a1a16a65b94d489ed1d30cfc6d668f",
                    "u_name": "嘎嘎嘎",
                    "host": true,
                    "head_logo": "noCheatsImage/head/74a1a16a65b94d489ed1d30cfc6d668f.jpg",
                    "time": "2017-07-09 11:02:36",
                    "level": 1,
                    "content": "哈哈",
                    "pic": [
                ""
                ],
                "praise": 0,
                    "status": 0,
                    "reply": null
            }
            ]
        }*/
    @SerializedName("msg")
    String msg;

    @SerializedName("floors")
    List<TopicDetailFloorEntity> floors;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TopicDetailFloorEntity> getFloors() {
        return floors;
    }

    public void setFloors(List<TopicDetailFloorEntity> floors) {
        this.floors = floors;
    }

    /**
     * gson 返回适配
     * @return
     */
    public List<ICommentEntity> getFloorsWrapper(){

        List<ICommentEntity> floorsWrapper = new ArrayList<>();
        Collections.sort(floors);
        floorsWrapper.addAll(floors);
        return floorsWrapper;
    }

    @Override
    public String toString() {
        return "TopicDetailGson{" +
                "msg='" + msg + '\'' +
                ", floors=" + floors +
                '}';
    }
}
