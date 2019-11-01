package com.zhangyc.library.proxy

import com.zhangyc.library.mvp.IBaseView

interface IProxyPresenter {

    fun bindPresenter(baseView: IBaseView)

    fun unBindPresenter()

}