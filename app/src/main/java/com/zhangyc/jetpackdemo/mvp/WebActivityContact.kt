package com.zhangyc.jetpackdemo.mvp

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView

interface WebActivityContact {

    interface IWebActivityView : IBaseView {
        fun getWebView() : WebView
    }

    class WebPresenter : IBasePresenter {

        private lateinit var mWebView : IWebActivityView

        override fun <V : IBaseView> attachView(v: V) {
            mWebView = v as IWebActivityView
        }

        override fun deAttachView() {

        }

        override fun requestFinish(success: Boolean) {
        }

        fun loadUrl() {
            val url = "https://www.baidu.com"
            mWebView.getWebView().loadUrl(url)
        }

        @SuppressLint("SetJavaScriptEnabled")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        fun getWebClient() : WebViewClient{
            val setting = mWebView.getWebView().settings
            setting.javaScriptEnabled = true
            return WebViewClient()
        }


    }

}