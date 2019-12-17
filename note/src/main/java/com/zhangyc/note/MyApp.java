package com.zhangyc.note;

import android.app.Application;
import android.util.Log;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

public class MyApp extends Application {

    public static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d(TAG, "onViewInitFinished: " + b);
            }
        };
        QbSdk.initX5Environment(this, cb);


        QbSdk.setDownloadWithoutWifi(true);

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d(TAG, "onDownloadFinish: ");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d(TAG, "onInstallFinish: ");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d(TAG, "onDownloadProgress: ");
            }
        });
    }
}
