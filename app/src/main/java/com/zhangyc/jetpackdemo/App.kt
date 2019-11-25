package com.zhangyc.jetpackdemo

import com.zhangyc.library.base.BaseApp
import kotlin.properties.Delegates

class App : BaseApp(){

    companion object {
        var instance : App by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }



}