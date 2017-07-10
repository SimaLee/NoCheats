package com.simalee.nocheats.module.data.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.comment.AllReplyGson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Lee Sima on 2017/7/10.
 */

public class CommentModel implements ICommentModel {


    private static final String TAG = CommentModel.class.getSimpleName();

    private Gson mGsonParser;


    public CommentModel(){

        mGsonParser = new Gson();
    }

    /**
     * floorId 为楼层的id
     * @param userId
     * @param floorId
     * @param content
     * @param photoUrls
     * @param callback
     */
    @Override
    public void releaseComment(String userId, String floorId, String content, String photoUrls, final ReleaseCommentCallback callback) {
        if (callback == null){
            return;
        }
        OkHttpUtils.post()
                .url(Constant.Url.URL_RELEASE_COMMENT)
                .addParams("u_id",userId)
                .addParams("f_id",floorId)
                .addParams("content",content)
                .addParams("pic",photoUrls)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse : "+ response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if ("0".equals(msg)){
                                callback.onReleaseSuccess();
                            }else{
                                callback.onReleaseFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 删除评论（楼层）
     * @param userId
     * @param commentId
     * @param callback
     */
    @Override
    public void deleteComment(String userId, String commentId, final DeleteCommentCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_DEL_COMMENT)
                .addParams("u_id",userId)
                .addParams("id",commentId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse: "+ response);

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if ("0".equals(msg)){
                                callback.onCommentDeleteSuccess();
                            }else{
                                callback.onCommentDeleteFailure();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 回复楼层 楼中楼开始
     * @param floorId
     * @param userId
     * @param content
     * @param callback
     */
    @Override
    public void replyComment(String floorId, String userId, String content, final ReleaseCommentCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_REPLY_COMMENT)
                .addParams("id",floorId)
                .addParams("u_id",userId)
                .addParams("content",content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse: "+ response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if ("0".equals(msg)){
                                callback.onReleaseSuccess();
                            }else{
                                callback.onReleaseFailure();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 回复某一 楼中楼
     * @param floorId
     * @param subjectUserId
     * @param objectUserId
     * @param objectUserName
     * @param content
     * @param callback
     */
    @Override
    public void replyReply(String floorId, String subjectUserId, String objectUserId, String objectUserName, String content, final ReplyReplyCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_REPLY_REPLY)
                .addParams("id",floorId)
                .addParams("u_id",subjectUserId)
                .addParams("r_id",objectUserId)
                .addParams("r_name",objectUserName)
                .addParams("content",content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            if ("0".equals(msg)){
                                callback.onReplyReplySuccess();
                            }else{
                                callback.onReplyReplyFailure();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 获取指定楼层的所有回复
     * @param floorId
     * @param callback
     */
    @Override
    public void loadFloorReply(String floorId, final LoadFloorReplyCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_LOAD_ALL_REPLY)
                .addParams("id",floorId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse : "+ response);
                        try {
                            AllReplyGson allReplyGson = mGsonParser.fromJson(response,AllReplyGson.class);
                            LogUtils.d(TAG,"allReplyGson: " + allReplyGson.toString());

                            if ("0".equals(allReplyGson.getMsg())){
                                callback.onLoadFloorReplySuccess(allReplyGson.getReplyReplyEntityList());
                            }else if ("1".equals(allReplyGson.getMsg())){
                                callback.onLoadFloorReplyFailure();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


}
