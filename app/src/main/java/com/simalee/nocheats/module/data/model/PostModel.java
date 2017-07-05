package com.simalee.nocheats.module.data.model;

import com.google.gson.Gson;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.post.AllPostsGson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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
     * 加载所有的帖子 根据 pageIndex
     * @param pageIndex
     * @param callback
     */
    @Override
    public void loadPosts(int pageIndex, final LoadPostsCallback callback) {
        if (callback == null){
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String last_time = "";
        last_time = dateFormat.format(new Date());

        LogUtils.d(TAG,"load posts: in page "+ pageIndex + " current time: "+ last_time);

        OkHttpUtils.get()
                .url(Constant.Url.URL_LOAD_POST)
                .addParams("type",pageIndex+"")
                .addParams("last_time",last_time)
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
}
