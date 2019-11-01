package com.zhangyc.library.mvp

interface IBasePresenter {

    fun <V : IBaseView> attachView(v : V)

    fun deAttachView()

    fun requestFinish(success: Boolean)
}