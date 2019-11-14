package com.zhangyc.media_player.mvp

import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView

interface VideoContact {

    interface IVideoView : IBaseView {

    }

    class VideoPresenter : IBasePresenter {

        private var mVideoView : IVideoView? = null

        private var position : Int = 0

        override fun <V : IBaseView> attachView(v: V) {
            mVideoView = v as IVideoView
        }

        override fun deAttachView() {
        }

        override fun requestFinish(success: Boolean) {

        }

        fun playVideo(position : Int) {
            this.position = checkPosition(position)
            val v = ReadSdMedia.instance.getVideoLists()?.get(this.position)
        }

        private fun checkPosition(position : Int) : Int{
            if (position < ReadSdMedia.instance.getVideoLists()?.size ?: 0) {
                return position
            }
            return 0
        }

    }

}