package com.zhangyc.media_player.mvp

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.blankj.utilcode.util.ServiceUtils
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import com.zhangyc.media_player.service.MusicService

interface MusicContact {

    interface IMusicView {

    }

    class MusicPresenter : IBasePresenter {

        private var mBinder : MusicService.MusicBinder? = null

        override fun <V : IBaseView> attachView(v: V) {

        }

        override fun deAttachView() {
        }

        override fun requestFinish(success: Boolean) {
        }

        fun checkBindMusicService() {
            if (!ServiceUtils.isServiceRunning(MusicService::class.java)) {
                ServiceUtils.bindService(MusicService::class.java, conn, Context.BIND_AUTO_CREATE)
            } else {
                getMusicBinder()?.play()
            }
        }

        private val conn : ServiceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {

            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                mBinder = p1 as MusicService.MusicBinder
                getMusicBinder()?.play()
            }

        }

        fun getMusicBinder() : MusicService.MusicBinder? {
            return mBinder
        }

    }

}