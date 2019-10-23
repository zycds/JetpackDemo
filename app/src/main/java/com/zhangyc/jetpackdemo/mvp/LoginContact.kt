package com.zhangyc.jetpackdemo.mvp

import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView

interface LoginContact {

    interface ILoginView : IBaseView {

    }

    class LoginPresenter : IBasePresenter{
        override fun <V : IBaseView> attachView(v: V) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun deAttachView() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}