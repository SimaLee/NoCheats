package com.simalee.nocheats.module.experiencesquare.view;

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
import com.simalee.nocheats.common.util.IntegralUtils;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.PostEntity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lee Sima on 2017/6/19.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private static final String TAG = "PostAdapter";

    private Context mContext;
    private ArrayList<PostEntity> postEntityList;
    private OnRecyclerItemClickListener recyclerItemClickListener;


    public void setRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public void replaceData(List<PostEntity> postEntities){
        postEntityList.clear();
        postEntityList.addAll(postEntities);
        notifyDataSetChanged();
    }

    public PostAdapter(Context context, ArrayList<PostEntity> postEntityList){
        mContext = context;
        this.postEntityList = postEntityList;
        LogUtils.d("PostAdapter","Constructor invoked");
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        LogUtils.d("PostAdapter","onCreateViewHolder invoked");
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {

        holder.bindData(postEntityList.get(position));
        LogUtils.d("PostAdapter","onBindViewHolder invoked");
    }

    @Override
    public int getItemCount() {
        return postEntityList.size();
    }


    public class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView image_user;
        TextView tv_userName;
        TextView tv_userLevel;
        TextView tv_postType;
        TextView tv_postTitle;
        TextView tv_postContent;
        TextView tv_postViewCount;

        public PostHolder(View itemView) {
            super(itemView);
            initViews(itemView);
            itemView.setOnClickListener(this);
        }

        private void initViews(View view){
            image_user = (CircleImageView) view.findViewById(R.id.image_user);
            tv_userName = (TextView) view.findViewById(R.id.tv_user_name);
            tv_userLevel = (TextView) view.findViewById(R.id.tv_level);
            tv_postType = (TextView) view.findViewById(R.id.tv_post_type);
            tv_postTitle = (TextView) view.findViewById(R.id.tv_post_title);
            tv_postContent = (TextView) view.findViewById(R.id.tv_post_content);
            tv_postViewCount = (TextView) view.findViewById(R.id.tv_comment_count);
        }

        public void bindData(PostEntity postEntity){
            //TODO 访问网络数据
            Glide.with(mContext)
                    .load(postEntity.getAvatar())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image_user.setImageDrawable(glideDrawable);
                        }
                    });

            tv_userName.setText(postEntity.getUserName());
            tv_userLevel.setText(IntegralUtils.getLevel(postEntity.getPoint()) + "");
            tv_postType.setText(postEntity.getPostType());
            tv_postTitle.setText(postEntity.getPostTitle());
            tv_postContent.setText(postEntity.getPostContent());
            tv_postViewCount.setText(""+postEntity.getPostViewCount());
        }

        @Override
        public void onClick(View v) {

            if (recyclerItemClickListener != null){
                recyclerItemClickListener.onItemClick(getAdapterPosition(),postEntityList.get(getAdapterPosition()));
            }

        }

    }

    public interface OnRecyclerItemClickListener{
        void onItemClick(int position,PostEntity postEntity);
    }
}
