package com.zhangyc.library.proxy


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zhangyc.library.mvp.IBaseView

abstract class ProxyActivity : RxAppCompatActivity(), IBaseView {

    private lateinit var proxyPresenter: IProxyPresenter

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