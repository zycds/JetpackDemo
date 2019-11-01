package com.zhangyc.jetpackdemo.mvp

import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView


interface MainContact {

    interface MainView : IBaseView {

    }

    class MainPresenter : IBasePresenter {
        override fun requestFinish(success: Boolean) {

        }

        override fun <V : IBaseView> attachView(v: V) {

        }

        override fun deAttachView() {

        }
    }

}