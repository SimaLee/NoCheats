package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.util.SpannableStringUtils;
import com.simalee.nocheats.module.data.entity.post.PostCommentReplyEntity;
import com.simalee.nocheats.module.data.entity.ICommentEntity;
import com.simalee.nocheats.module.data.entity.post.PostCommentEntity;
import com.simalee.nocheats.module.data.entity.ReplyReplyEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lee Sima on 2017/6/21.
 */

public class PostDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = PostDetailAdapter.class.getSimpleName();


    public static enum ITEM_TYPE{
        ITEM_TYPE_HEAD,
        ITEM_TYPE_COMMENT
    }

    private final Context mContext;
    private final LayoutInflater inflater;
    private List<ICommentEntity> commentEntityList;


    public PostDetailAdapter(Context context,List<ICommentEntity> commentEntities){
        mContext = context;
        inflater = LayoutInflater.from(context);

        commentEntityList = commentEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEAD.ordinal()){
            return new HeaderViewHolder(inflater.inflate(R.layout.item_post_main,parent,false));
        }else{
            return new CommentViewHolder(inflater.inflate(R.layout.item_post_reply,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).bindData((PostCommentEntity) commentEntityList.get(position));
        }else if (holder instanceof CommentViewHolder){
            ((CommentViewHolder) holder).bindData((PostCommentReplyEntity) commentEntityList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE.ITEM_TYPE_HEAD.ordinal();
        }else{
            return ITEM_TYPE.ITEM_TYPE_COMMENT.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return commentEntityList.size();
    }


    /**
     *  帖子详情的第一个view
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView tv_postTitle;
        CircleImageView image_user;
        TextView tv_userName;
        TextView tv_isHost;
        TextView tv_level;
        TextView tv_postType;
        TextView tv_postContent;
        ImageView iv_postPic;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View rootView){
            tv_postTitle = (TextView) rootView.findViewById(R.id.tv_post_title);
            image_user = (CircleImageView) rootView.findViewById(R.id.image_user);
            tv_userName = (TextView) rootView.findViewById(R.id.tv_user_name);
            tv_isHost = (TextView) rootView.findViewById(R.id.tv_is_host);
            tv_level = (TextView) rootView.findViewById(R.id.tv_level);
            tv_postType = (TextView) rootView.findViewById(R.id.tv_post_type);
            tv_postContent = (TextView) rootView.findViewById(R.id.tv_post_content);
            iv_postPic = (ImageView) rootView.findViewById(R.id.iv_pic);
        }

        public void bindData(PostCommentEntity data){
            if (data == null){
                return;
            }
            tv_postTitle.setText(data.getPostTitle());
            //image_user.setImageDrawable();
            tv_userName.setText(data.getUserName());
            //tv_isHost 不用设置
            tv_level.setText(data.getPoint());
            tv_postType.setText(data.getPostType());
            tv_postContent.setText(data.getPostContent());
        }
    }


    /**
     *  其他回复的view
     */
    public class CommentViewHolder extends RecyclerView.ViewHolder{


        CircleImageView image_user;
        TextView tv_userName;
        TextView tv_isHost;
        TextView tv_level;
        TextView tv_postComment;
        TextView tv_postStorey;
        TextView tv_firstReply;
        TextView tv_secondReply;
        TextView tv_moreReply;


        public CommentViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View rootView){
            image_user = (CircleImageView) rootView.findViewById(R.id.image_user);
            tv_userName = (TextView) rootView.findViewById(R.id.tv_user_name);
            tv_isHost = (TextView) rootView.findViewById(R.id.tv_is_host);
            tv_level = (TextView) rootView.findViewById(R.id.tv_level);
            tv_postComment = (TextView) rootView.findViewById(R.id.tv_post_comment);

            tv_postStorey = (TextView) rootView.findViewById(R.id.tv_post_storey);
            tv_firstReply = (TextView) rootView.findViewById(R.id.tv_first_reply);
            tv_secondReply = (TextView) rootView.findViewById(R.id.tv_second_reply);
            tv_moreReply = (TextView) rootView.findViewById(R.id.tv_more_replies);

        }

        public void bindData(PostCommentReplyEntity data){
            //image_user;
            tv_userName.setText(data.getCommentUserName());

            if (data.isHost()){
                tv_isHost.setVisibility(View.VISIBLE);
            }else{
                tv_isHost.setVisibility(View.GONE);
            }
            //TODO 计算方法
            tv_level.setText(data.getCommentUserPoint());
            tv_postStorey.setText("第"+data.getCommentStorey()+"楼");
            tv_postComment.setText(data.getCommentContent());

            setCommentReply(data.getRepliesList());
        }

        private void setCommentReply(List<ReplyReplyEntity> replyList){
            if (replyList == null || replyList.size() == 0){
                return;
            }
            if (replyList.size() == 1){
                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));
                tv_firstReply.setMovementMethod(LinkMovementMethod.getInstance());

                tv_firstReply.setVisibility(View.VISIBLE);
                tv_secondReply.setVisibility(View.GONE);
                tv_moreReply.setVisibility(View.GONE);

            }else if (replyList.size() == 2){

                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));
                tv_firstReply.setMovementMethod(LinkMovementMethod.getInstance());
                tv_firstReply.setVisibility(View.VISIBLE);

                //必须要设置 setMovementMethod 才能响应点击事件
                tv_secondReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(1)));
                tv_secondReply.setMovementMethod(LinkMovementMethod.getInstance());
                tv_secondReply.setVisibility(View.VISIBLE);

                tv_moreReply.setVisibility(View.GONE);
            }else{
               // tv_firstReply.setText(replyList.get(0).getTestMsg());
                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));
                tv_firstReply.setMovementMethod(LinkMovementMethod.getInstance());
                tv_firstReply.setVisibility(View.VISIBLE);

                tv_secondReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(1)));
                tv_secondReply.setMovementMethod(LinkMovementMethod.getInstance());
                tv_secondReply.setVisibility(View.VISIBLE);

                tv_moreReply.setText("更多"+(replyList.size()-2)+"条回复");
                tv_moreReply.setVisibility(View.VISIBLE);

                tv_moreReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMoreReplyClickListener != null){
                            Toast.makeText(mContext,"查看更多回复",Toast.LENGTH_SHORT).show();
                            onMoreReplyClickListener.onMoreReplyClick(v);
                        }

                    }
                });
            }
        }

    }

    public interface OnMoreReplyClickListener{
        void onMoreReplyClick(View v);
    }

    private OnMoreReplyClickListener onMoreReplyClickListener;

    public void setOnMoreReplyClickListener(OnMoreReplyClickListener listener) {
        onMoreReplyClickListener = listener;
    }


}
