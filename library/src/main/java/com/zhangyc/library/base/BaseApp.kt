package com.zhangyc.library.base

import android.app.Application
import com.zhangyc.library.utils.AppUtil

abstract class BaseApp : Application() {

    companion object {
        lateinit var instance: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppUtil.syncIsDebug(applicationContext)
    }



}