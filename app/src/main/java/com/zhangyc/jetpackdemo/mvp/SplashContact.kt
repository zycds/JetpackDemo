package com.zhangyc.jetpackdemo.mvp

import android.util.Log
import android.widget.ImageView
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView

interface SplashContact {

    interface ISplashView : IBaseView {
        fun getImageView() : ImageView
    }

    class SplashPresenter : IBasePresenter{
        private val tag  = SplashPresenter::class.java.simpleName
        private var splashView : ISplashView? = null
        override fun <V : IBaseView> attachView(v: V) {
            if (v is ISplashView) splashView = v
        }
        override fun deAttachView() {
            splashView = null
        }

        fun getNewSplashImage(){
            Log.d(tag, "getNewSplashImage")
            splashView?.getImageView()?.postDelayed({

            }, 3000)
        }

    }

}