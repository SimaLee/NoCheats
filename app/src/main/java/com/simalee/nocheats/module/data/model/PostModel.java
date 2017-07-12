package com.simalee.nocheats.module.data.model;

import com.google.gson.Gson;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.AllPostsGson;
import com.simalee.nocheats.module.data.entity.post.MyPostsGson;
import com.simalee.nocheats.module.data.entity.post.PostDetailGson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Lee Sima on 2017/7/5.
 */

public class PostModel implements IPostModel{

    private static final String TAG = PostModel.class.getSimpleName();


    private Gson mGsonParser;

    public PostModel() {

        mGsonParser = new Gson();
    }

    /**
     * 发布帖子
     * @param userId
     * @param postTitle
     * @param postType
     * @param postContent
     * @param postPicUrl
     * @param callback
     */
    @Override
    public void releasePost(String userId, String postTitle, String postType, String postContent, String postPicUrl, final ReleasePostCallback callback) {
        if (callback == null){
            return ;
        }
        OkHttpUtils.post()
                .url(Constant.Url.URL_RELEASE_POST)
                .addParams("u_id",userId)
                .addParams("title",postTitle)
                .addParams("type",postType)
                .addParams("content",postContent)
                .addParams("pic",postPicUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.e(TAG,e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse: "+ response);
                        /*0：成功
                        1：用户不存在
                        2：更新楼层失败
                        3：其他*/
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String msg = jsonObject.getString("msg");
                            String postId = jsonObject.getString("id");
                            if ("0".equals(msg)){
                                callback.onPostReleasedSuccess();
                            }else if ("1".equals(msg)){
                                callback.onPostReleasedFailure("系统正在维护中");
                            }else if ("2".equals(msg)){
                                callback.onPostReleasedFailure("系统正在维护中");
                            }else if ("3".equals(msg)){
                                callback.onPostReleasedFailure("系统正在维护中");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    /**
     * 加载帖子 根据pageIndex和  当前时间/帖子时间 加载
     * @param pageIndex
     * @param lastTimeStr
     * @param callback
     */
    @Override
    public void loadPosts(int pageIndex,String lastTimeStr, final LoadPostsCallback callback) {
        if (callback == null){
            return;
        }

        LogUtils.d(TAG,"load posts: in page "+ pageIndex + " current time: "+ lastTimeStr);

        OkHttpUtils.get()
                .url(Constant.Url.URL_LOAD_POST)
                .addParams("type",pageIndex+"")
                .addParams("last_time",lastTimeStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse :"+ response);
                        try {

                            AllPostsGson postsGson = mGsonParser.fromJson(response,AllPostsGson.class);
                            LogUtils.d(TAG,"postGson content: "+ postsGson);

                            String msg = postsGson.getMsg();

                            if ("0".equals(msg)){
                                callback.onLoadPostsSuccess(postsGson.getPostEntityList());

                            }else if ("1".equals(msg)){
                                callback.onLoadPostsFailure();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    /**
     * 获取我的帖子
     * 主页是 0
     * @param userId
     * @param postType
     * @param lastTimeStr
     * @param callback
     */
    @Override
    public void loadMyPosts(String userId, int postType, String lastTimeStr, final LoadMyPostCallback callback) {
        if (callback == null){
            return;
        }
        OkHttpUtils.post()
                .url(Constant.Url.URL_LOAD_MY_POST)
                .addParams("u_id",userId)
                .addParams("type",postType+"")
                .addParams("last_time",lastTimeStr)
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
                            MyPostsGson postsGson = mGsonParser.fromJson(response,MyPostsGson.class);
                            String msg = postsGson.getMsg();

                            if ("0".equals(msg)){
                                callback.onLoadPostsSuccess(postsGson.getPostEntityList());
                            }else if ("1".equals(msg)){
                                callback.onLoadPostsFailure();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 加载帖子详情
     * @param postId
     * @param lastTimeStr
     * @param callback
     */
    @Override
    public void loadPostDetail(String postId, String lastTimeStr, final LoadPostDetailCallback callback) {
        if (callback == null){
            return;
        }
        OkHttpUtils.post()
                .url(Constant.Url.URL_GET_POST_DETAIL)
                .addParams("id",postId)
                .addParams("last_time",lastTimeStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse: postDetail: " + response);

                        PostDetailGson postDetailGson = mGsonParser.fromJson(response,PostDetailGson.class);
                        LogUtils.d(TAG,"postDetailGson is : "+ postDetailGson.toString());

                        if ("0".equals(postDetailGson.getMsg())){
                            callback.onLoadPostDetailSuccess(postDetailGson.getFloorsWrapper());
                        }else{
                            callback.onLoadPostDetailFailure();
                        }

                    }
                });
    }

    /**
     * 删除帖子
     * @param userId
     * @param postId
     * @param callback
     */
    @Override
    public void deletePost(String userId, String postId, final DeletePostCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_DELETE_POST)
                .addParams("u_id",userId)
                .addParams("p_id",postId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");

                            if ("0".equals(msg)){
                                callback.onPostDeletedSuccess();
                            }else{
                                callback.onPostDeletedFailure("删除帖子失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
