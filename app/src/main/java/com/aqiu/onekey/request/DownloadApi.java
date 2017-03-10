package com.aqiu.onekey.request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * 获取最新apk
 * Created by aqiu on 2017/3/10.
 */

public interface DownloadApi {
    @Streaming
    @GET("/22.apk")
    Call<ResponseBody> retrofitDownload();
}
