package com.zhangyc.media_player.mvp

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.blankj.utilcode.util.ServiceUtils
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.db.Music
import com.zhangyc.library.event.RxHelper
import com.zhangyc.library.event.RxTimer
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import com.zhangyc.media_player.fragment.MusicFragment
import com.zhangyc.media_player.service.MusicService
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

interface MusicContact {

    companion object {
        val tag = MusicContact::class.java.simpleName
    }

    interface IMusicView {
        fun refreshMusicInfo(music: Music)
        fun updateProgress(progress : Long)
        fun getCurrentFragment() : MusicFragment
    }

    class MusicPresenter : IBasePresenter {

        private var mMusicView : IMusicView? = null

        private var mBinder : MusicService.MusicBinder? = null

        private var mSubscribeUpdateProgress : Disposable? = null

        private var mStopPredicate : Boolean = false

        override fun <V : IBaseView> attachView(v: V) {
            mMusicView = v as IMusicView
        }

        override fun deAttachView() {
            disposeUpdateProgress()
            mStopPredicate = true
        }

        override fun requestFinish(success: Boolean) {
        }

        fun checkBindMusicService() {
            if (!ServiceUtils.isServiceRunning(MusicService::class.java)) {
                ServiceUtils.bindService(MusicService::class.java, conn, Context.BIND_AUTO_CREATE)
            } else {
                getMusicBinder()?.play()
            }
            subscribeUpdateProgress()
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

        private fun subscribeUpdateProgress() {
            disposeUpdateProgress()
            mSubscribeUpdateProgress = RxTimer.instance.interval(
                mMusicView?.getCurrentFragment()?.activity!!,
                1,
                TimeUnit.SECONDS,
                Long.MAX_VALUE
            )
                .takeWhile {
                    !mStopPredicate
                }.compose(RxHelper.handlerResultIO())
                .subscribe({
                    Lg.debug(tag, "subscribeUpdateProgress :  $it")
                    mMusicView?.updateProgress(getMusicBinder()?.getCurrentProgress()?:0)
                }, {
                    Lg.debug(tag, "subscribeUpdateProgress exception :  $it")
                    mMusicView?.updateProgress(getMusicBinder()?.getCurrentProgress()?:0)
                })
        }

        private fun disposeUpdateProgress() {
            if (mSubscribeUpdateProgress?.isDisposed == true) mSubscribeUpdateProgress?.dispose()
            mSubscribeUpdateProgress = null
        }

    }

}