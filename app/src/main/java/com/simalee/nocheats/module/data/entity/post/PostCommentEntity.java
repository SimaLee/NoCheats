package com.simalee.nocheats.module.data.entity.post;

import com.simalee.nocheats.module.data.entity.ICommentEntity;

/**
 * Created by Lee Sima on 2017/6/21.
 */

public class PostCommentEntity extends PostEntity implements ICommentEntity {

    //为了在RecyclerView中使用做的转换

    public PostCommentEntity() {
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
