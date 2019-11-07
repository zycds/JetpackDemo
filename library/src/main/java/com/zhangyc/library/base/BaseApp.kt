package com.zhangyc.library.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.smtt.sdk.QbSdk
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.BuildConfig
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.utils.AppUtil

abstract class BaseApp : Application() {

    companion object {
        lateinit var instance: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        QbSdk.initX5Environment(instance, cb)
        AppUtil.syncIsDebug(applicationContext)
        if (BuildConfig.DEBUG) ARouter.openDebug()
        ARouter.init(this)
        registerAllReceiver()
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
        unregisterAllReceiver()
    }

    private var cb = object : QbSdk.PreInitCallback {
        override fun onCoreInitFinished() {
            Lg.debug("app", "onCoreInitFinished")
        }

        override fun onViewInitFinished(p0: Boolean) {
            //p0 true x5 init success.  fail auto switch kernel
            Lg.debug("app", "init x5 : $p0")
        }

    }

    private fun registerAllReceiver() {
        ReadSdMedia.instance.registerScanSdcardReceiver()
    }

    private fun unregisterAllReceiver() {
        ReadSdMedia.instance.unRegisterScanSdcardReceiver()
    }

}