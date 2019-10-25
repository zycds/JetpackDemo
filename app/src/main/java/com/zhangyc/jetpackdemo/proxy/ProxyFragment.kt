package com.zhangyc.jetpackdemo.proxy

import androidx.fragment.app.Fragment
import com.zhangyc.jetpackdemo.base.IBaseView

abstract class ProxyFragment : Fragment(), IBaseView {

    private var proxyPresenter: IProxyPresenter? = null
    override fun onStart() {
        super.onStart()
        proxyPresenter = ProxyPresenterImpl()
        proxyPresenter?.bindPresenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        proxyPresenter?.unBindPresenter()
    }


}