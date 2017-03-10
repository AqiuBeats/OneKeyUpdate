package com.aqiu.onekey.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqiu.onekey.utils.Tutils;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * 公共Activity
 * Created by aqiu on 2017/3/10.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * activity控制
     */
    public static Map<String, WeakReference<Activity>> openedActivitys = new LinkedHashMap<>();// 已经打开的activity
    public Bundle savedInstanceState;
    private long exitTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!openedActivitys.keySet().contains(getClass().getSimpleName())) {
            openedActivitys.put(getClass().getSimpleName(),
                    new WeakReference<Activity>(this));
        }
        this.savedInstanceState = savedInstanceState;
        setContentView(initLayout());//减少反映布局的代码
        ButterKnife.bind(this);
        initDatas();
    }

    @Override
    public void onStart() {
        super.onStart();
        reDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        openedActivitys.remove(getClass().getSimpleName());
    }

    /**
     * 在onCreate初始化Views
     */
    public abstract int initLayout();

    /**
     * 在onCreate初始化数据
     */
    public abstract void initDatas();

    /**
     * 在onResume()生命周期中调用
     */
    public abstract void reDatas();

    @Override
    public void onBackPressed() {
        if (isLoginOut()) {
            //连续按2次返回键退出
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                Tutils.showToast(getApplicationContext(), "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finshAllActivitys();
                System.exit(0);
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 关闭所有打开的activity，同时退出自己
     */
    public void finshAllActivitys() {
        if (!openedActivitys.isEmpty()) {
            for (String clsssName : openedActivitys.keySet()) {
                WeakReference<Activity> weakReference = openedActivitys
                        .get(clsssName);
                Activity activity = weakReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            System.exit(0);
        }
    }

    /**
     * 判断是否是主页面
     * 这儿一般是MainActivity为主页面,如果不是则需要修改变量
     */
    private boolean isLoginOut() {
        return getClass().getSimpleName().equals(
                MainActivity.class.getSimpleName());
    }

}
