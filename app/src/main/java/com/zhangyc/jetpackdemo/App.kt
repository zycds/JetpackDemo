package com.zhangyc.jetpackdemo

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.zhangyc.jetpackdemo.utils.AppUtil
import kotlin.properties.Delegates

class App : Application(){

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
    }


    open fun getMainHandler() : Handler {
        if (mHandler == null) mHandler = Handler(Looper.getMainLooper())
        return mHandler
    }

}