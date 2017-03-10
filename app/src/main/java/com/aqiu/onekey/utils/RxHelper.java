package com.aqiu.onekey.utils;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * RxJava辅助类的封装
 * Created by aqiu on 2017/3/10.
 */
public abstract class RxHelper<T> {
    public Observable.Transformer<T, T> io_main() {
        return tObservable -> {
            @SuppressWarnings("unchecked")
            Observable<T> observable = tObservable
                    .compose(RxSchedulersHelper.io_main())//子线程运行，主线程回调
                    .doOnSubscribe(() -> doMain())
                    .subscribeOn(AndroidSchedulers.mainThread());
            return observable;
        };
    }

    /**
     * 主线程初始操作
     */
    public abstract void doMain();
}
