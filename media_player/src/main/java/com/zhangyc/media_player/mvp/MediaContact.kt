package com.zhangyc.media_player.mvp

import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView

interface MediaContact {

    companion object {
        val tag = MediaContact::class.java.simpleName
    }

    interface IMediaView : IBaseView

    class MediaPresenter : IBasePresenter {

        private var mMediaView: IMediaView? = null



        override fun <V : IBaseView> attachView(v: V) {
            mMediaView = v as IMediaView
        }

        override fun deAttachView() {

        }

        override fun requestFinish(success: Boolean) {
        }


    }

}