package com.aqiu.onekey.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 对Rxjava异步线程的简单封转工具类
 * Created by aqiu on 2017/3/10.
 */

class RxSchedulersHelper {

    static <T> Observable.Transformer<T, T> io_main() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
