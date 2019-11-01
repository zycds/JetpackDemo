package com.zhangyc.jetpackdemo

import android.os.Handler
import android.os.Looper
import com.tencent.smtt.sdk.QbSdk
import com.zhangyc.library.utils.AppUtil
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseApp
import kotlin.properties.Delegates

class App : BaseApp(){

    private lateinit var mHandler : Handler

    companion object {
        var instance : App by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppUtil.syncIsDebug(applicationContext)
        mHandler = Handler(Looper.getMainLooper())
        QbSdk.initX5Environment(instance, cb)
    }

    private var cb = object : QbSdk.PreInitCallback{
        override fun onCoreInitFinished() {
            Lg.debug("app", "onCoreInitFinished")
        }

        override fun onViewInitFinished(p0: Boolean) {
            //p0 true x5 init success.  fail auto switch kernel
            Lg.debug("app", "init x5 : $p0")
        }

    }

    open fun getMainHandler() : Handler {
        if (mHandler == null) mHandler = Handler(Looper.getMainLooper())
        return mHandler
    }

}