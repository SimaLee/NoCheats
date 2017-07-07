package com.simalee.nocheats.module.data.entity.post;

import com.google.gson.annotations.SerializedName;
import com.simalee.nocheats.module.data.entity.ICommentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee Sima on 2017/7/6.
 */

public class PostDetailGson {

    /*{
        "msg": 0,
            "floors": [
        {
            "id": "53b6f8dcf8f349fdaafc47dc8982bd3f",
                "f_id": "c72dea4cd8a24582a367735de2fde97974a1a16a65b94d489ed1d30cfc6d668f",
                "u_id": "74a1a16a65b94d489ed1d30cfc6d668f",
                "u_name": "12345678901",
                "host": true,
                "head_logo": "noCheatsImage/head/74a1a16a65b94d489ed1d30cfc6d668f.jpg",
                "time": "2017-07-05 16:55:41",
                "level": 1,
                "content": "测试",
                "pic": null,
                "praise": 0,
                "status": 0,
                "reply": null
        }
        ]
    }*/

    /**
     * 状态码
     */
    @SerializedName("msg")
    String msg;

    @SerializedName("floors")
    List<PostDetailFloorEntity> floors;


    public PostDetailGson() {
    }

    public List<PostDetailFloorEntity> getFloors() {
        return floors;
    }

    public void setFloors(List<PostDetailFloorEntity> floors) {
        this.floors = floors;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * gson 返回适配
     * @return
     */
    public List<ICommentEntity> getFloorsWrapper(){

        List<ICommentEntity> floorsWrapper = new ArrayList<>();
        floorsWrapper.addAll(floors);

        return floorsWrapper;
    }

    @Override
    public String toString() {
        return "PostDetailGson{" +
                ", msg='" + msg + '\'' +
                "floors=" + floors +
                '}';
    }
}
