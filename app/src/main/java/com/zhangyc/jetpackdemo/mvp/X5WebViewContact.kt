package com.zhangyc.jetpackdemo.mvp

import android.os.Bundle
import android.os.Environment
import androidx.core.view.size
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsReaderView
import com.tencent.smtt.sdk.ValueCallback
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import java.io.File

interface X5WebViewContact {

    interface IX5WebView : IBaseView {
        fun getTbsReaderView() : TbsReaderView
    }

    class X5Presenter : IBasePresenter {

        private lateinit var mIX5WebView : IX5WebView

        override fun <V : IBaseView> attachView(v: V) {
            mIX5WebView = v as IX5WebView
        }

        override fun deAttachView() {
            mIX5WebView.getTbsReaderView().onStop()
        }

        override fun requestFinish(success: Boolean) {
        }

        fun displayFile(file : File) {
           /* val params = HashMap<String, String>()
            params["style"] = "0"
            params["local"] = "false"
            params["topBarBgColor"] = "#ff8b3d"
            QbSdk.openFileReader(mIX5WebView.getActivityContext(),"/sdcard/c.pdf", params, object :
                ValueCallback<String> {
                override fun onReceiveValue(p0: String?) {
                    Lg.debug("X5Presenter", "p0 : $p0")
                }
            })*/

            val bundle = Bundle()
            bundle.putString("filePath", file.path)
            bundle.putString("tempPath", Environment.getExternalStorageDirectory().path.plus("/TbsReaderTemp"))
            val result = mIX5WebView.getTbsReaderView().preOpen("pdf" , false)
            Lg.debug("mIX5WebView", "result : $result,  ${Environment.getExternalStorageDirectory().path} ,   ${file.path}")
            if (result) mIX5WebView.getTbsReaderView().openFile(bundle)

            Lg.debug("mIX5WebView", "mIX5WebView.getTbsReaderView().size : ${mIX5WebView.getTbsReaderView().size}")
        }

    }

}