package com.zhangyc.jetpackdemo.activity

import android.content.Context
import android.webkit.WebView
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseActivity
import com.zhangyc.jetpackdemo.mvp.WebActivityContact
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : BaseActivity<WebActivityContact.WebPresenter>(), WebActivityContact.IWebActivityView {

    @InjectPresenter
    lateinit var mPresenter : WebActivityContact.WebPresenter

    override fun init() {
        setContentView(R.layout.activity_webview)
    }

    override fun initData() {
        mPresenter.loadUrl()
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun back(): Boolean {
        return false
    }

    override fun getActivityContext(): Context? {
        return this
    }

    override fun getWebView(): WebView {
        return webView2
    }

    override fun getTenCentWebView(): com.tencent.smtt.sdk.WebView {
        return webView
    }
}