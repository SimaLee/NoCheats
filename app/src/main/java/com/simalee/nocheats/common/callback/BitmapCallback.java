package com.simalee.nocheats.common.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Lee Sima on 2017/6/27.
 */

public abstract class BitmapCallback extends Callback<Bitmap> {

    @Override
    public Bitmap parseNetworkResponse(Response response, int id) throws Exception {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }


}
