package com.zhangyc.media_player.mvp

import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView

interface MediaContact {

    interface IMediaView : IBaseView {
        fun getRecyclerView() : RecyclerView
    }

    class MediaPresenter : IBasePresenter {
        override fun <V : IBaseView> attachView(v: V) {

        }

        override fun deAttachView() {
        }

        override fun requestFinish(success: Boolean) {
        }

    }

}