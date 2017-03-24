package com.aqiu.onekey.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.aqiu.onekey.R;
import com.aqiu.onekey.bean.Update;
import com.aqiu.onekey.http.WebApi;
import com.aqiu.onekey.request.DownloadApi;
import com.aqiu.onekey.request.NetRequest;
import com.aqiu.onekey.utils.AppUtils;
import com.aqiu.onekey.utils.NotificationUtil;
import com.aqiu.onekey.utils.ProgressDialogUtil;
import com.aqiu.onekey.utils.RxHelper;
import com.aqiu.onekey.utils.Tutils;
import com.ljd.retrofit.progress.DownloadProgressHandler;
import com.ljd.retrofit.progress.ProgressHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

public class SecondActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    private NotificationUtil notificationUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_second;
    }

    @Override
    public void initDatas() {
        netRequestForUpdate("update.json");
        notificationUtil = new NotificationUtil(SecondActivity.this);
        //        retrofitDownload();
    }

    @Override
    public void reDatas() {

    }

    private void netRequestForUpdate(String update) {
        NetRequest.getObservable(update)
                .compose(new RxHelper<Update>() {
                    @Override
                    public void doMain() {
                        ProgressDialogUtil.showProgress(SecondActivity.this, "数据加载中...");
                    }
                }.io_main())
                .subscribe(new Subscriber<Update>() {
                    @Override
                    public void onCompleted() {
                        Tutils.showToast(SecondActivity.this, "正常");
                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Tutils.showToast(SecondActivity.this, "错误");
                        ProgressDialogUtil.dismiss();
                    }

                    @Override
                    public void onNext(Update update) {
                        //                        tv.setText("当前版本号==" + update.getServerVersion());
                        tv.setText("当前版本号==" + AppUtils.getVersionName(SecondActivity.this));
                        float versionNum = Float.parseFloat(AppUtils.getVersionName(SecondActivity.this));
                        if (update.getServerVersion() > versionNum) {
                            showUpdateDialog();
                        }
                    }
                });
    }

    private void showUpdateDialog() {
        new MaterialDialog.Builder(SecondActivity.this)
                .title("新版本来袭!!")
                .content("更新说明:\n1.新增数据库检索\n2.新增关于界面\n3.修复若干bug")
                .iconRes(R.mipmap.push)
                .positiveText("立即更新")
                .negativeText("暂不更新")
                .cancelable(false)
                .positiveColor(Color.BLUE)
                .backgroundColor(getResources().getColor(R.color.paleturquoise))
                .negativeColor(Color.RED)
                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        switch (which) {
                            case POSITIVE://确定按钮
                                //                                notificationUtil.showNotification(1);
                                notificationUtil.showNotification(1);
                                retrofitDownload();
                                break;
                            case NEGATIVE://否定按钮

                                break;
                            default:
                                break;
                        }
                    }
                })
                //CheckBox
                .checkBoxPrompt("不再提醒", false, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            Tutils.showToast(SecondActivity.this, "不再提醒");
                        } else {
                            Tutils.showToast(SecondActivity.this, "会再次提醒");
                        }
                    }
                }).show();
    }

    private void retrofitDownload() {
        //        //监听下载进度
        //        final ProgressDialog dialog = new ProgressDialog(this);
        //        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        //        dialog.setTitle("下载");
        //        dialog.setMessage("正在下载，请稍后...");
        //        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //        dialog.setCancelable(false);
        //        dialog.show();

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
                                              @Override
                                              protected void onProgress(long bytesRead, long contentLength, boolean done) {
                                                  Log.d("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                                                  Log.d("onProgress", String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                                                  Log.d("done", "--->" + String.valueOf(done));
                                                  float max = (int) (contentLength / 1024);
                                                  float now = (int) (bytesRead / 1024);
//                                                  L.e((int) (100 * (now / max)) + "");
                                                  notificationUtil.updateProgress(1, (int) (100 * (now / max)));
                                                  //                                dialog.setMax((int) (contentLength / 1024));
                                                  //                                dialog.setProgress((int) (bytesRead / 1024));

                                                  if (done) {
                                                      //                                    dialog.dismiss();
                                                      notificationUtil.notifyBuilder(1);

                                                  }
                                              }
                                          }
        );

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WebApi.BASE_URL);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        DownloadApi retrofit = retrofitBuilder.client(builder.build()).build().create(DownloadApi.class);
        Call<ResponseBody> call = retrofit.retrofitDownload();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> {
                        try {
                            InputStream is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory(), "update.apk");
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                fos.flush();
                            }
                            fos.close();
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            String fileName = Environment.getExternalStorageDirectory() + "/update.apk";
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                            startActivity(intent);
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
