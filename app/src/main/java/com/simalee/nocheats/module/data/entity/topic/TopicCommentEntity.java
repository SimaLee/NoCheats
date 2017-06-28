package com.simalee.nocheats.module.data.entity.topic;

import com.simalee.nocheats.module.data.entity.ICommentEntity;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicCommentEntity extends TopicEntity implements ICommentEntity {

    //为了在RecyclerView中使用做的转换

    public TopicCommentEntity() {
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
