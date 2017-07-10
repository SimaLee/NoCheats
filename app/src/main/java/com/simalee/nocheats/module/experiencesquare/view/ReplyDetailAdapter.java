package com.simalee.nocheats.module.experiencesquare.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simalee.nocheats.R;
import com.simalee.nocheats.common.util.SpannableStringUtils;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public class ReplyDetailAdapter extends RecyclerView.Adapter<ReplyDetailAdapter.ReplyHolder>{


    private static final String TAG = ReplyDetailAdapter.class.getSimpleName();

    private List<ReplyReplyEntity> replyReplyEntityList;
    private final LayoutInflater inflater;

    public ReplyDetailAdapter(Context context,List<ReplyReplyEntity> replyEntities){
        replyReplyEntityList = replyEntities;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ReplyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holderView = inflater.inflate(R.layout.item_reply_detail,parent,false);
        return new ReplyHolder(holderView);
    }

    @Override
    public void onBindViewHolder(ReplyHolder holder, int position) {
       final ReplyReplyEntity data = replyReplyEntityList.get(position);
        holder.bindData(data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(floorId,data.getSubjectId(),data.getSubjectName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (replyReplyEntityList == null){
            return 0;
        }
        return replyReplyEntityList.size();
    }

    class ReplyHolder extends RecyclerView.ViewHolder{

        TextView tv_reply_content;

        public ReplyHolder(View itemView) {
            super(itemView);
            tv_reply_content = (TextView) itemView.findViewById(R.id.tv_reply_content);
        }

        public void bindData(final ReplyReplyEntity data){
            tv_reply_content.setText(SpannableStringUtils.getReplySpannableString(data));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String floorId,String objectUserId,String objectUserName);
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    /**
     * 当前界面所属楼层Id
     */
    private String floorId;


    /**
     * 替换数据
     * @param data
     */
    public void replaceData(List<ReplyReplyEntity> data){
        replyReplyEntityList.clear();
        replyReplyEntityList.addAll(data);
        notifyDataSetChanged();
    }

}
