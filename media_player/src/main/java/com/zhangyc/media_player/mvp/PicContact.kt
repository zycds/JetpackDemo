package com.zhangyc.media_player.mvp

import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView

interface PicContact {

    interface IPicView : IBaseView

    class PicPresenter : IBasePresenter {
        override fun <V : IBaseView> attachView(v: V) {
        }

        override fun deAttachView() {
        }

        override fun requestFinish(success: Boolean) {
        }

    }

}