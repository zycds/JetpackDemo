package com.zhangyc.jetpackdemo.base

import android.os.Bundle
import com.zhangyc.jetpackdemo.proxy.ProxyActivity

abstract class BaseActivity<P : IBasePresenter> : ProxyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        unInit()
    }

    protected abstract fun init()

    protected abstract fun initData()

    protected abstract fun refreshData()

    protected abstract fun unInit()


}