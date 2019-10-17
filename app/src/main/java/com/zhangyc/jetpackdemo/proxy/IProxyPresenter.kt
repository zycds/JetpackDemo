package com.zhangyc.jetpackdemo.proxy

import com.zhangyc.jetpackdemo.base.IBaseView

interface IProxyPresenter {

    fun bindPresenter(baseView: IBaseView)

    fun unBindPresenter()

}