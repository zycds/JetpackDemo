package com.zhangyc.library.proxy

import androidx.fragment.app.Fragment
import com.zhangyc.library.mvp.IBaseView

abstract class ProxyFragment : Fragment(), IBaseView {

    private var proxyPresenter: IProxyPresenter? = null
    override fun onStart() {
        super.onStart()
        proxyPresenter = ProxyPresenterImpl()
        proxyPresenter?.bindPresenter(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        proxyPresenter?.unBindPresenter()
    }


}