package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.simalee.nocheats.R;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.common.util.SpannableStringUtils;
import com.simalee.nocheats.common.util.TypeUtils;
import com.simalee.nocheats.module.data.entity.post.PostDetailFloorEntity;
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.entity.post.PostDetailMainFloorConverter;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;

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
            ((HeaderViewHolder) holder).bindData((PostDetailMainFloorConverter) commentEntityList.get(position));
        }else if (holder instanceof CommentViewHolder){
            ((CommentViewHolder) holder).bindData((PostDetailFloorEntity) commentEntityList.get(position));
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
            tv_level.setVisibility(View.GONE);
            tv_postType = (TextView) rootView.findViewById(R.id.tv_post_type);
            tv_postContent = (TextView) rootView.findViewById(R.id.tv_post_content);
            iv_postPic = (ImageView) rootView.findViewById(R.id.iv_pic);
            iv_postPic.setVisibility(View.GONE);
        }

        public void bindData(PostDetailMainFloorConverter data){
            if (data == null){
                return;
            }
            tv_postTitle.setText(data.getPostTitle());

            Glide.with(mContext)
                    .load(Constant.Url.BASE_URL + data.getCommentUserAvatar())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image_user.setImageDrawable(glideDrawable);
                        }
                    });

            if ((data.getPicUrlList() != null) && (data.getPicUrlList().size() > 0)){
                iv_postPic.setVisibility(View.VISIBLE);

                //目前只有一张图片
                String picUrl = Constant.Url.BASE_URL + data.getPicUrlList().get(0);
                Glide.with(mContext)
                        .load(picUrl.replace(Constant.CODE.FOLDER_THUMBNAIL,Constant.CODE.FOLDER_ORIGINAL))
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                iv_postPic.setImageDrawable(glideDrawable);
                            }
                        });
            }

            tv_userName.setText(data.getCommentUserName());
            //tv_isHost 不用设置
            //tv_level.setText(data.getCommentUserPoint()); 没有返回
            tv_postType.setText(TypeUtils.getTypeString(data.getPostType()));

            tv_postContent.setText(data.getCommentContent());

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

        TextView tv_operation_type;

        RelativeLayout rl_user;
        RelativeLayout rl_comment_reply;

        public CommentViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View rootView){
            image_user = (CircleImageView) rootView.findViewById(R.id.image_user);
            tv_userName = (TextView) rootView.findViewById(R.id.tv_user_name);
            tv_isHost = (TextView) rootView.findViewById(R.id.tv_is_host);
            tv_level = (TextView) rootView.findViewById(R.id.tv_level);
            tv_level.setVisibility(View.GONE);
            tv_postComment = (TextView) rootView.findViewById(R.id.tv_post_comment);

            tv_postStorey = (TextView) rootView.findViewById(R.id.tv_post_storey);
            tv_firstReply = (TextView) rootView.findViewById(R.id.tv_first_reply);
            tv_secondReply = (TextView) rootView.findViewById(R.id.tv_second_reply);
            tv_moreReply = (TextView) rootView.findViewById(R.id.tv_more_replies);

            tv_operation_type = (TextView) rootView.findViewById(R.id.tv_operation_type);

            rl_user = (RelativeLayout) rootView.findViewById(R.id.rl_user);
            rl_comment_reply = (RelativeLayout) rootView.findViewById(R.id.rl_comment_reply);
        }

        public void bindData(final PostDetailFloorEntity data){


            Glide.with(mContext)
                    .load(Constant.Url.BASE_URL + data.getCommentUserAvatar())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image_user.setImageDrawable(glideDrawable);
                        }
                    });
            tv_userName.setText(data.getCommentUserName());

            if (data.isHost()){
                tv_isHost.setVisibility(View.VISIBLE);
            }else{
                tv_isHost.setVisibility(View.GONE);
            }
            //TODO 计算方法
            //tv_level.setText(data.getCommentUserPoint());//没有返回
            tv_postStorey.setText("第"+data.getCommentStorey()+"楼");
            tv_postComment.setText(data.getCommentContent());

            //为了用户交互做的调整
            rl_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnCommentClickListener != null){

                        mOnCommentClickListener.onCommentClick(v,data.getCommentUserName(),data.getCommentId());
                    }
                }
            });

            tv_postComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnCommentClickListener != null){
                        mOnCommentClickListener.onCommentClick(v,data.getCommentUserName(),data.getCommentId());
                    }
                }
            });

            setCommentReply(data.getCommentId(),data.getRepliesList());

            tv_operation_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //data.getCommentId()
                    LogUtils.d(TAG,"点击了："+data.toString());
                    if(mOnCommentClickListener != null){
                        //TODO :这里错了 ID
                        mOnCommentClickListener.onCommentClick(v,data.getCommentUserName(),data.getCommentId());
                    }
                }
            });
        }

        private void setCommentReply(final String floorId,final List<ReplyReplyEntity> replyList){
            if (replyList == null || replyList.size() == 0){
                setNoReplyView();
                return;
            }
            if (replyList.size() == 1){
                tv_firstReply.setVisibility(View.VISIBLE);
                tv_secondReply.setVisibility(View.GONE);
                tv_moreReply.setVisibility(View.GONE);

                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));
                //tv_firstReply.setMovementMethod(LinkMovementMethod.getInstance());

            }else if (replyList.size() == 2){

                tv_firstReply.setVisibility(View.VISIBLE);
                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));
                //tv_firstReply.setMovementMethod(LinkMovementMethod.getInstance());

                //必须要设置 setMovementMethod 才能响应点击事件
                tv_secondReply.setVisibility(View.VISIBLE);
                tv_secondReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(1)));
                //tv_secondReply.setMovementMethod(LinkMovementMethod.getInstance());

                tv_moreReply.setVisibility(View.GONE);

            }else{
               // tv_firstReply.setText(replyList.get(0).getTestMsg());
                tv_firstReply.setVisibility(View.VISIBLE);
                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));
                //tv_firstReply.setMovementMethod(LinkMovementMethod.getInstance());


                tv_secondReply.setVisibility(View.VISIBLE);
                tv_secondReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(1)));
                //tv_secondReply.setMovementMethod(LinkMovementMethod.getInstance());

                tv_moreReply.setVisibility(View.VISIBLE);
                tv_moreReply.setText("更多"+(replyList.size()-2)+"条回复");


                /*tv_moreReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMoreReplyClickListener != null){
                           // Toast.makeText(mContext,"查看更多回复",Toast.LENGTH_SHORT).show();
                            onMoreReplyClickListener.onMoreReplyClick(floorId);
                        }

                    }
                });*/
            }

            //为了用户交互做的调整
            rl_comment_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMoreReplyClickListener != null){
                        onMoreReplyClickListener.onMoreReplyClick(floorId);
                    }
                }
            });

        }

        /**
         * 设置没有评论时的状态。
         */
        private void setNoReplyView(){
            tv_firstReply.setVisibility(View.GONE);
            tv_secondReply.setVisibility(View.GONE);
            tv_moreReply.setVisibility(View.GONE);
        }

    }


    /**
     * 替换数据
     * @param data
     */
    public void replaceData(List<ICommentEntity> data){
        commentEntityList.clear();
        commentEntityList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 附加数据
     * @param moreData
     */
    public void appendData(List<ICommentEntity> moreData){
        commentEntityList.addAll(moreData);
        notifyDataSetChanged();
    }

    /**
     * 此处需要处理没有回复的问题
     * @return
     */
    public ICommentEntity getLastEntity(){
        if (commentEntityList == null){
            throw new NullPointerException("Attend to get last entity in an empty object");

        }
        return  commentEntityList.get(commentEntityList.size()-1);
    }



    public interface OnMoreReplyClickListener{
        void onMoreReplyClick(String floorId);
    }

    public void setOnMoreReplyClickListener(OnMoreReplyClickListener listener) {
        onMoreReplyClickListener = listener;
    }

    public interface OnCommentClickListener{
        void onCommentClick(View v,String userName,String floorId);
    }

    public void setOnCommentClickListener(OnCommentClickListener mOnCommentClickListener) {
        this.mOnCommentClickListener = mOnCommentClickListener;
    }

    private OnMoreReplyClickListener onMoreReplyClickListener;
    private OnCommentClickListener mOnCommentClickListener;


}
