package com.simalee.nocheats.module.data.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.simalee.nocheats.common.config.Constant;
import com.simalee.nocheats.common.util.DateUtils;
import com.simalee.nocheats.common.util.LogUtils;
import com.simalee.nocheats.module.data.entity.topic.AllTopicGson;
import com.simalee.nocheats.module.data.entity.topic.MyTopicGson;
import com.simalee.nocheats.module.data.entity.topic.TopicDetailGson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Lee Sima on 2017/7/9.
 */

public class TopicModel implements ITopicModel {

    private static final String TAG = TopicModel.class.getSimpleName();


    private Gson mGsonParser;

    public TopicModel(){
        mGsonParser = new Gson();
    }


    @Override
    public void loadTopics(String lastTimeStr, final LoadTopicsCallback callback) {
        if(callback == null){
            return ;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_LOAD_TOPIC)
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
                            AllTopicGson topicGson = mGsonParser.fromJson(response,AllTopicGson.class);
                            LogUtils.d(TAG,"topicGson : " + topicGson.toString());
                            String msg = topicGson.getMsg();

                            if ("0".equals(msg)){
                                callback.onLoadTopicsSuccess(topicGson.getTopicEntityList());
                            }else if ("1".equals(msg)){
                                callback.onLoadTopicsFailure();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void releaseTopic(String userId, String topicTitle, String topicContent, String topicPicUrls, final IPostModel.ReleasePostCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_RELEASE_TOPIC)
                .addParams("u_id",userId)
                .addParams("title",topicTitle)
                .addParams("content",topicContent)
                .addParams("pic",topicPicUrls)
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
                            JSONObject jsonObject  = new JSONObject(response);

                            String msg = jsonObject.getString("msg");

                            if ("0".equals(msg)){
                                callback.onPostReleasedSuccess();
                            }else if ("1".equals(msg)){
                                callback.onPostReleasedFailure("用户不存在");
                            }else if ("2".equals(msg)){
                                callback.onPostReleasedFailure("更新楼层失败");
                            }else if ("3".equals(msg)){
                                callback.onPostReleasedFailure("系统维护中");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void loadTopicDetail(final String topicId, String lastTimeStr, final LoadTopicDetailCallback callback) {
        if (callback == null){
            return;
        }


        OkHttpUtils.post()
                .url(Constant.Url.URL_GET_TOPIC_DETAIL)
                .addParams("id",topicId)
                .addParams("last_time",lastTimeStr)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callback.onError(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.d(TAG,"onResponse: "+response);
                        TopicDetailGson topicDetailGson = mGsonParser.fromJson(response,TopicDetailGson.class);
                        LogUtils.d(TAG,"topicDetailGson : " + topicDetailGson.toString());
                        String msg = topicDetailGson.getMsg();

                        if ("0".equals(msg)){
                            callback.onLoadTopicDetailSuccess(topicDetailGson.getFloorsWrapper());
                        }else if ("1".equals(msg)){
                            callback.onLoadTopicDetailFailure();
                        }
                    }
                });

    }

    @Override
    public void loadMyTopics(String userId, String lastTimeStr, final LoadMyTopicCallback callback) {
        if (callback == null){
            return;
        }

        OkHttpUtils.post()
                .url(Constant.Url.URL_LOAD_MY_TOPIC)
                .addParams("u_id",userId)
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

                            MyTopicGson myTopicGson = mGsonParser.fromJson(response,MyTopicGson.class);
                            String msg = myTopicGson.getMsg();

                            if ("0".equals(msg)){
                                callback.onLoadTopicsSuccess(myTopicGson.getTopicEntityList());
                            }else if("1".equals(msg)){
                                callback.onLoadTopicFailure();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


    }


}
