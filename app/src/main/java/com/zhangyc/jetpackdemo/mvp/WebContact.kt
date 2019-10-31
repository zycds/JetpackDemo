package com.zhangyc.jetpackdemo.mvp

import android.webkit.WebView
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView

interface WebContact {

    interface IWebView : IBaseView{
        fun getWebView() : WebView
    }

    class WebPresenter : IBasePresenter{
        override fun <V : IBaseView> attachView(v: V) {

        }

        override fun deAttachView() {

        }

        override fun requestFinish(success: Boolean) {

        }

    }

}