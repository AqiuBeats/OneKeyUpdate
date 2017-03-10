package com.aqiu.onekey.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 因为使用TCP通讯,此类暂时无用
 * (1)设置log日志
 * (2)设置超时
 * (3)设置缓存
 * (4)为以后直接使用OkHttpClient创建单例
 */
public class OkHttpFactory {
    private static OkHttpClient okHttpClient;//与聚合数据相关
    private static final int TIMEOUT_READ = 5;
    private static final int TIMEOUT_CONNECTION = 5;

    public synchronized static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //            Cache cache = new Cache(MyApplication.mContext.getCacheDir(), 10 * 1024 * 1024);//设置缓存为10M
            okHttpClient = new OkHttpClient.Builder()
                    //打印请求log
                    .addInterceptor(interceptor)
                    //必须是设置Cache目录
                    //                    .cache(cache)
                    //本程序不走缓存
                    //                    .addInterceptor(new CachedInterceptor())
                    //                    .addNetworkInterceptor(new CachedInterceptor())
                    //失败重连
                    .retryOnConnectionFailure(true)
                    //time out
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)   //连接超时时间为5秒
                    .build();
        }
        return okHttpClient;
    }
}
