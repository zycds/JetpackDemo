package com.zhangyc.jetpackdemo.mvp

import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView

interface MainContact {

    interface MainView : IBaseView {

    }

    class MainPresenter : IBasePresenter {
        override fun <V : IBaseView> attachView(v: V) {

        }

        override fun deAttachView() {

        }
    }

}