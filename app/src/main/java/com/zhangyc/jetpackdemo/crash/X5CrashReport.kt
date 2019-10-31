package com.zhangyc.jetpackdemo.crash

class X5CrashReport {

    companion object {
        val instance: X5CrashReport by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            X5CrashReport()
        }
    }

    init {


    }

}