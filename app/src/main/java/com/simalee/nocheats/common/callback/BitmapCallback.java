package com.simalee.nocheats.common.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.simalee.nocheats.common.util.LogUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by Lee Sima on 2017/6/27.
 */

public abstract class BitmapCallback extends Callback<Bitmap> {

    @Override
    public Bitmap parseNetworkResponse(Response response, int id) throws Exception {
        /*LogUtils.d("UserModel","response body: " + response);
        Headers headers = response.headers();
        LogUtils.d("UserModel","loadPcode response:"+"");
        LogUtils.d("UserModel","header: "+ headers);
        List<String> cookies = headers.values("Set-Cookie");
        //String session = cookies.get(0);
        LogUtils.d("UserModel","onResponse-size: "+ cookies);
        //String s = session.substring(0,session.indexOf(";"));
        //LogUtils.d("UserModel","session is: "+ s);*/

        return BitmapFactory.decodeStream(response.body().byteStream());
    }


}
