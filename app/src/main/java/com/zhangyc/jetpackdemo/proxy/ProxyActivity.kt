package com.zhangyc.jetpackdemo.proxy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhangyc.jetpackdemo.base.IBaseView

abstract class ProxyActivity : AppCompatActivity(), IBaseView {

    lateinit var proxyPresenter: IProxyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        proxyPresenter = ProxyPresenterImpl()
        proxyPresenter.bindPresenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        proxyPresenter.unBindPresenter()
    }


}