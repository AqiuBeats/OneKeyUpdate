package com.aqiu.onekey.request;


import com.aqiu.onekey.bean.Update;
import com.aqiu.onekey.http.RetrofitClient;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 网络请求
 * Created by aqiu on 2017/3/10.
 */

public class NetRequest {
    /**
     * 1.获得更新json
     */
    public static Observable<Update> getObservable(String name) {
        GetUpdateService getUpdateService = RetrofitClient.getInstance().create(GetUpdateService
                .class);
        return getUpdateService.getUpdateService(name);
    }

    //http://192.168.10.39:8080/update.json
    private interface GetUpdateService {
        @GET("{name}")
        Observable<Update> getUpdateService(@Path("name") String name);
    }
}
