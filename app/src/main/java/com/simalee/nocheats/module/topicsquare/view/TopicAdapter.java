package com.simalee.nocheats.module.topicsquare.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.IntegralUtils;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicEntity;
import com.simalee.nocheats.module.experiencesquare.view.PostAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {

    private Context mContext;
    private List<TopicEntity> topicEntityList;

    private  OnItemClickListener mOnItemClickListener;
    private  OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public TopicAdapter(Context context,List<TopicEntity> topicEntities){
        if (topicEntities == null){
            throw new NullPointerException("Topic Entities can not be null");
        }
        topicEntityList = topicEntities;
        mContext = context;
    }


    @Override
    public TopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_topic,parent,false);
        return new TopicHolder(view);
    }

    @Override
    public void onBindViewHolder(TopicHolder holder, int position) {
        holder.bindData(topicEntityList.get(position));
    }

    @Override
    public int getItemCount() {
       return topicEntityList == null ? 0 : topicEntityList.size();
    }


    public class TopicHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        CircleImageView image_user;
        TextView tv_userName;
        TextView tv_userLevel;
        TextView tv_topicType;
        TextView tv_topicTitle;
        TextView tv_topicContent;
        TextView tv_topicCommentCount;

        public TopicHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        private void initViews(View view){
            image_user = (CircleImageView) view.findViewById(R.id.image_user);
            tv_userName = (TextView) view.findViewById(R.id.tv_user_name);
            tv_userLevel = (TextView) view.findViewById(R.id.tv_level);
            tv_topicType = (TextView) view.findViewById(R.id.tv_topic_type);
            tv_topicTitle = (TextView) view.findViewById(R.id.tv_topic_title);
            tv_topicContent = (TextView) view.findViewById(R.id.tv_topic_content);
            tv_topicCommentCount = (TextView) view.findViewById(R.id.tv_comment_count);
        }

        public void bindData(TopicEntity data){

            Glide.with(mContext)
                    .load(Constant.Url.BASE_URL + data.getAvatar())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image_user.setImageDrawable(glideDrawable);
                        }
                    });

            tv_userName.setText(data.getUserName());
            tv_userLevel.setText("Lv "+ IntegralUtils.getLevel(data.getPoint()) + "");
            tv_topicType.setText(data.getTopicType());
            tv_topicTitle.setText(data.getTopicTitle());
            tv_topicContent.setText(data.getTopicContent());
            tv_topicCommentCount.setText(data.getTopicCommentCount()+"");
        }



        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(getAdapterPosition(),
                        topicEntityList.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null){
                mOnItemLongClickListener.onItemLongClick(getAdapterPosition(),
                        topicEntityList.get(getAdapterPosition()));
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position,TopicEntity topicEntity);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position,TopicEntity topicEntity);
    }

    /**
     * 替换数据
     * @param topicEntities
     */
    public void replaceData(List<TopicEntity> topicEntities){
        topicEntityList.clear();
        topicEntityList.addAll(topicEntities);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param appendTopicEntities
     */
    public void appendData(List<TopicEntity> appendTopicEntities){
        topicEntityList.addAll(appendTopicEntities);
        notifyDataSetChanged();
    }

    /**
     * 获取最后一个entity
     * @return
     */
    public TopicEntity getLastEntity(){
        if (topicEntityList == null || topicEntityList.size() == 0){
            throw new NullPointerException("Attend to get lastEntity in an empty topicEntityList");
        }
        return topicEntityList.get(topicEntityList.size()-1);
    }

    /**
     * 移除指定位置的数据
     * @param index
     */
    public void remove(int index){
        if (index < 0 || index > topicEntityList.size()){
            return;
        }
        topicEntityList.remove(index);
        notifyItemRemoved(index);
    }
}
