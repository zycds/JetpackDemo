package com.zhangyc.jetpackdemo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.mvp.WebContact
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseFragment
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
        Lg.debug(bTag, "goBack : ${getWebView().canGoBack()}, goFroward : ${getWebView().canGoForward()}")
    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context? {
        return context
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun getWebView(): WebView {
        if (initWebView) {
            webView.webViewClient = WebViewClient()
            val settings = webView.settings
            settings.setSupportZoom(true)//支持缩放
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.mediaPlaybackRequiresUserGesture = true
            }
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.loadWithOverviewMode = true //预览模式
            initWebView = false
        }
        return webView
    }

}