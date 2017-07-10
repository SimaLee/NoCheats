package com.simalee.nocheats.module.experiencesquare.presenter;

import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.comment.ReplyReplyEntity;
import com.simalee.nocheats.module.data.model.CommentModel;
import com.simalee.nocheats.module.data.model.ICommentModel;
import com.simalee.nocheats.module.experiencesquare.contract.ReplyDetailContract;

import java.util.List;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public class ReplyDetailPresenter  implements ReplyDetailContract.Presenter{

    private static final String TAG = ReplyDetailPresenter.class.getSimpleName();

    private ReplyDetailContract.View mReplyDetailView;

    private ICommentModel mCommentModel;

    public ReplyDetailPresenter(ReplyDetailContract.View replyDetailView){

        if (replyDetailView == null){
            throw new NullPointerException("replyDetailView is empty！");
        }
        mReplyDetailView = replyDetailView;
        mCommentModel = new CommentModel();
    }

    /**
     * 获取楼层回复数据
     * @param floorId
     */
    @Override
    public void loadFloorReply(String floorId) {
        if (mReplyDetailView != null){
            mReplyDetailView.showLoadingProgress();
        }

        mCommentModel.loadFloorReply(floorId, new ICommentModel.LoadFloorReplyCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());
            }

            @Override
            public void onLoadFloorReplySuccess(List<ReplyReplyEntity> replyReplyEntityList) {
                if (mReplyDetailView != null){
                    mReplyDetailView.hideLoadingProgress();
                    mReplyDetailView.showFloorReply(replyReplyEntityList);
                }
            }

            @Override
            public void onLoadFloorReplyFailure() {
                if (mReplyDetailView != null){
                    mReplyDetailView.hideLoadingProgress();
                    mReplyDetailView.showLoadFloorReplyFailed();
                }
            }
        });
    }

    /**
     * 回复评论
     * @param floorId
     * @param userId
     * @param content
     */
    @Override
    public void replyComment(String floorId, String userId, String content) {
        mCommentModel.replyComment(floorId, userId, content, new ICommentModel.ReleaseCommentCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: " + e.toString());
            }

            @Override
            public void onReleaseSuccess() {
                if (mReplyDetailView != null){
                    mReplyDetailView.showCommentSuccess();
                }
            }

            @Override
            public void onReleaseFailure() {
                if (mReplyDetailView != null){
                    mReplyDetailView.showCommentFailure();
                }
            }
        });
    }

    /**
     * 回复 ->  回复
     * @param floorId
     * @param subjectUserId
     * @param objectUserId
     * @param objectUserName
     * @param content
     */
    @Override
    public void replyReply(String floorId, String subjectUserId, String objectUserId, String objectUserName, String content) {

        mCommentModel.replyReply(floorId, subjectUserId, objectUserId, objectUserName, content, new ICommentModel.ReplyReplyCallback() {
            @Override
            public void onError(Exception e) {
                LogUtils.e(TAG,"error: "+ e.toString());
            }

            @Override
            public void onReplyReplySuccess() {
                if (mReplyDetailView != null){
                    mReplyDetailView.showCommentSuccess();
                }
            }

            @Override
            public void onReplyReplyFailure() {
                if (mReplyDetailView != null){
                    mReplyDetailView.showCommentFailure();
                }
            }
        });
    }


    @Override
    public void start() {
        //do nothing
    }
}
