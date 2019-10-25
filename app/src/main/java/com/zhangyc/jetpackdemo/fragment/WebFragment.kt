package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.mvp.WebContact
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : BaseFragment<WebContact.WebPresenter>(), WebContact.IWebView {

    private var initWebView = true


    override fun init() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_web
    }

    override fun initData() {
        getWebView().loadUrl(arguments?.getString("url"))
    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context? {
        return context
    }

    override fun getWebView(): WebView {
        if (initWebView) {
            webView.webViewClient = WebViewClient()
            initWebView = false
        }
        return webView
    }

}