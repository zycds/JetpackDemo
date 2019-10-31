package com.zhangyc.jetpackdemo.base

interface IBasePresenter {
    fun <V : IBaseView> attachView(v: V)
    fun deAttachView()
    fun requestFinish(success: Boolean)
}