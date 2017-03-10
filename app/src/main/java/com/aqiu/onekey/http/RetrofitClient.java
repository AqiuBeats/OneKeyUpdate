package com.aqiu.onekey.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * description: 初始化Retrofit
 */
public class RetrofitClient {
    private static Retrofit retrofit;

    /**
     * 单例获得Xml数据的Retrofit对象
     * 这里要注意了,如果BaseUrl是一个需要变化的值,那么就不能对retrofit进行非空判断
     */
    public static synchronized Retrofit getInstance() {
        //        System.out.println("retrofit:" + WebApi.BASE_URL);
        if (retrofit == null) {
            return retrofit = new Retrofit.Builder()
                    .baseUrl(WebApi.BASE_URL)
                    .client(OkHttpFactory.getInstance())
                    .addConverterFactory(ScalarsConverterFactory.create())//只进行字符串的操作
                    .addConverterFactory(GsonConverterFactory.create())//进行对象转化操作
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
