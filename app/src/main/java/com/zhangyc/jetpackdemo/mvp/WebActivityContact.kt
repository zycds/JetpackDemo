package com.zhangyc.jetpackdemo.mvp

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.tencent.smtt.sdk.WebChromeClient
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView

interface WebActivityContact {

    interface IWebActivityView : IBaseView {
        fun getWebView() : WebView
        fun getTenCentWebView() : com.tencent.smtt.sdk.WebView
    }

    class WebPresenter : IBasePresenter{

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
            mWebView.getTenCentWebView().loadUrl(url)
        }

        @SuppressLint("SetJavaScriptEnabled")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        fun getWebClient() : WebViewClient{
            val setting = mWebView.getWebView().settings
            setting.javaScriptEnabled = true
            return WebViewClient()
        }

        fun getWebChromeClient() : WebChromeClient{
            return WebChromeClient()
        }

    }

}