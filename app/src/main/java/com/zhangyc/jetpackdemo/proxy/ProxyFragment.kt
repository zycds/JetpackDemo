package com.zhangyc.jetpackdemo.proxy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhangyc.jetpackdemo.base.IBaseView

abstract class ProxyFragment : Fragment(), IBaseView {

    lateinit var proxyPresenter: IProxyPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        proxyPresenter = ProxyPresenterImpl()
        proxyPresenter.bindPresenter(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        proxyPresenter.unBindPresenter()
    }


}