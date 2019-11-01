package com.zhangyc.jetpackdemo.activity

import android.content.Context
import android.widget.LinearLayout
import com.tencent.smtt.sdk.TbsReaderView
import com.zhangyc.jetpackdemo.R
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.mvp.X5WebViewContact
import com.zhangyc.library.base.BaseActivity
import kotlinx.android.synthetic.main.activity_x5.*
import java.io.File

class X5WebViewActivity : BaseActivity<X5WebViewContact.X5Presenter>(), X5WebViewContact.IX5WebView, TbsReaderView.ReaderCallback {

    @InjectPresenter
    lateinit var mPresenter : X5WebViewContact.X5Presenter

    private lateinit var tbsReaderView: TbsReaderView

    override fun init() {
        setContentView(R.layout.activity_x5)
        tbsReaderView = TbsReaderView(this, this)
        layout_content_x5.addView(tbsReaderView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

    }


    override fun initData() {
        val file = File("/sdcard/c.pdf")
        mPresenter.displayFile(file)
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return this
    }

    override fun back(): Boolean {
        return false
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

    }

    override fun getTbsReaderView(): TbsReaderView {
        return tbsReaderView
    }

}