package com.simalee.nocheats.module.topicsquare.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.simalee.nocheats.module.data.entity.comment.ICommentEntity;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;
import com.simalee.nocheats.module.data.entity.topic.TopicDetailMainFloorConverter;
import com.simalee.nocheats.module.data.entity.topic.TopicDetailFloorEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lee Sima on 2017/6/22.
 */

public class TopicDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final  String TAG = TopicDetailAdapter.class.getSimpleName();


    public static enum ITEM_TYPE{
        ITEM_TYPE_HEAD,
        ITEM_TYPE_COMMENT
    }

    private final Context mContext;
    private final LayoutInflater inflater;
    private List<ICommentEntity> commentEntityList;

    public TopicDetailAdapter(Context context,List<ICommentEntity> commentEntities){
        mContext = context;
        inflater = LayoutInflater.from(context);
        commentEntityList = commentEntities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEAD.ordinal()){
            return new HeaderViewHolder(inflater.inflate(R.layout.item_topic_main,parent,false));
        }else{
            return new CommentViewHolder(inflater.inflate(R.layout.item_topic_reply,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).bindData((TopicDetailMainFloorConverter) commentEntityList.get(position));
        }else if (holder instanceof CommentViewHolder){
            ((CommentViewHolder) holder).bindData((TopicDetailFloorEntity) commentEntityList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.ITEM_TYPE_HEAD.ordinal();
        } else{
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

        TextView tv_topicTitle;
        CircleImageView image_user;
        TextView tv_userName;
        TextView tv_isHost;
        TextView tv_level;
        TextView tv_topicType;
        TextView tv_topicContent;
        ImageView iv_topicPic;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View rootView){
            tv_topicTitle = (TextView) rootView.findViewById(R.id.tv_topic_title);
            image_user = (CircleImageView) rootView.findViewById(R.id.image_user);
            tv_userName = (TextView) rootView.findViewById(R.id.tv_user_name);
            tv_isHost = (TextView) rootView.findViewById(R.id.tv_is_host);
            tv_level = (TextView) rootView.findViewById(R.id.tv_level);

            tv_level.setVisibility(View.GONE);

            tv_topicType = (TextView) rootView.findViewById(R.id.tv_topic_type);
            tv_topicContent = (TextView) rootView.findViewById(R.id.tv_topic_content);
            iv_topicPic = (ImageView) rootView.findViewById(R.id.iv_pic);

            iv_topicPic.setVisibility(View.GONE);

        }

        public void bindData(TopicDetailMainFloorConverter data){

            if (data == null){
                return;
            }

            tv_topicTitle.setText(data.getTopicTitle());

            Glide.with(mContext)
                    .load(Constant.Url.BASE_URL + data.getCommentUserAvatar())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image_user.setImageDrawable(glideDrawable);
                        }
                    });

           if (data.getPicUrlList() != null && data.getPicUrlList().size() > 0){
               iv_topicPic.setVisibility(View.VISIBLE);
               LogUtils.d(TAG,data.getPicUrlList().toString());

               String picUrl = Constant.Url.BASE_URL + data.getPicUrlList().get(0);
               //目前只有一张图片
               Glide.with(mContext)
                       .load(picUrl.replace(Constant.CODE.FOLDER_THUMBNAIL,Constant.CODE.FOLDER_ORIGINAL))
                       .into(new SimpleTarget<GlideDrawable>() {
                           @Override
                           public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                               iv_topicPic.setImageDrawable(glideDrawable);
                           }
                       });
           }

            tv_userName.setText(data.getCommentUserName());
            //tv_ishost
            //tv_level.setText(""+IntegralUtils.getLevel(data.getCommentUserPoint()));//没有返回
            // tv_topicType.setText();
            tv_topicContent.setText(data.getCommentContent());

        }
    }

    /**
     *  其他回复的view
     */
    public class CommentViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image_user;
        TextView tv_userName;
        TextView tv_isHost;
        TextView tv_level;
        TextView tv_topicComment;
        TextView tv_topicStorey;
        TextView tv_firstReply;
        TextView tv_secondReply;
        TextView tv_moreReply;

        TextView tv_operation_type;

        public CommentViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View rootView) {
            image_user = (CircleImageView) rootView.findViewById(R.id.image_user);
            tv_userName = (TextView) rootView.findViewById(R.id.tv_user_name);
            tv_isHost = (TextView) rootView.findViewById(R.id.tv_is_host);
            tv_level = (TextView) rootView.findViewById(R.id.tv_level);
            tv_topicComment = (TextView) rootView.findViewById(R.id.tv_topic_comment);

            tv_topicStorey = (TextView) rootView.findViewById(R.id.tv_topic_storey);
            tv_firstReply = (TextView) rootView.findViewById(R.id.tv_first_reply);
            tv_secondReply = (TextView) rootView.findViewById(R.id.tv_second_reply);
            tv_moreReply = (TextView) rootView.findViewById(R.id.tv_more_replies);

            tv_operation_type = (TextView) rootView.findViewById(R.id.tv_operation_type);
        }

        public void bindData(final TopicDetailFloorEntity data) {

            Glide.with(mContext)
                    .load(Constant.Url.BASE_URL + data.getCommentUserAvatar())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            image_user.setImageDrawable(glideDrawable);
                        }
                    });

            tv_userName.setText(data.getCommentUserName());

            if (data.isHost()) {
                tv_isHost.setVisibility(View.VISIBLE);
            } else {
                tv_isHost.setVisibility(View.GONE);
            }
            //TODO 计算方法
            //tv_level.setText(""+IntegralUtils.getLevel(data.getCommentUserPoint()));//没有返回
            tv_topicStorey.setText("第" + data.getCommentStorey() + "楼");
            tv_topicComment.setText(data.getCommentContent());

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

        private void setCommentReply(final String floorId,List<ReplyReplyEntity> replyList) {
            if (replyList == null || replyList.size() == 0) {
                setNoReplyView();
                return;
            }
            if (replyList.size() == 1) {
                tv_firstReply.setVisibility(View.VISIBLE);
                tv_secondReply.setVisibility(View.GONE);
                tv_moreReply.setVisibility(View.GONE);

                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));

            } else if (replyList.size() == 2) {
                tv_firstReply.setVisibility(View.VISIBLE);
                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));

                tv_secondReply.setVisibility(View.VISIBLE);
                tv_secondReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(1)));

                tv_moreReply.setVisibility(View.GONE);
            } else {
                tv_firstReply.setVisibility(View.VISIBLE);
                tv_firstReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(0)));

                tv_secondReply.setVisibility(View.VISIBLE);
                tv_secondReply.setText(SpannableStringUtils.getReplySpannableString(replyList.get(1)));

                tv_moreReply.setVisibility(View.VISIBLE);
                tv_moreReply.setText("更多" + (replyList.size() - 2) + "条回复");

                tv_moreReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "查看更多回复", Toast.LENGTH_SHORT).show();
                        if (mOnMoreReplyClickListener != null){
                            mOnMoreReplyClickListener.onMoreReplyClick(floorId);
                        }
                    }
                });
            }
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
        mOnMoreReplyClickListener = listener;
    }

    public interface OnCommentClickListener{
        void onCommentClick(View v,String userName,String floorId);
    }

    public void setOnCommentClickListener(OnCommentClickListener mOnCommentClickListener) {
        this.mOnCommentClickListener = mOnCommentClickListener;
    }

    private OnMoreReplyClickListener mOnMoreReplyClickListener;
    private OnCommentClickListener mOnCommentClickListener;

}
