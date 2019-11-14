package com.zhangyc.media_player.mvp

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.ProgressBar
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
        fun getCurrentFragment(): MusicFragment
        fun getProgressBar() : ProgressBar
    }

    class MusicPresenter : IBasePresenter {

        private var mMusicView: IMusicView? = null

        private var mBinder: MusicService.MusicBinder? = null

        private var mSubscribeUpdateProgress: Disposable? = null

        private var mStopPredicate: Boolean = false

        var position: Int = 0

        override fun <V : IBaseView> attachView(v: V) {
            mMusicView = v as IMusicView
        }

        override fun deAttachView() {
            disposeUpdateProgress()
            mStopPredicate = true
        }

        override fun requestFinish(success: Boolean) {
        }

        fun checkBindMusicService(position: Int) {
            this.position = getMusicBinder()?.checkPosition(position) ?: 0
            if (!ServiceUtils.isServiceRunning(MusicService::class.java)) {
                ServiceUtils.bindService(MusicService::class.java, conn, Context.BIND_AUTO_CREATE)
            } else {
                getMusicBinder()?.play(this.position)
            }
        }

        private val conn: ServiceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {

            }

            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                mBinder = p1 as MusicService.MusicBinder
                getMusicBinder()?.play(position)
            }

        }

        fun getMusicBinder(): MusicService.MusicBinder? {
            return mBinder
        }

        fun subscribeUpdateProgress() {
            disposeUpdateProgress()
            mStopPredicate = false

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
                    setProgress()
                }, {
                    Lg.debug(tag, "subscribeUpdateProgress exception :  $it")
                    setProgress()
                })
        }

        private fun setProgress() {
            val currentProgress = getMusicBinder()?.getCurrentProgress()?.times(100)
            val duration = getMusicBinder()?.getDuration()
            Lg.debug(tag, "progress : $currentProgress, duration : $duration")
            mMusicView?.getProgressBar()?.progress = duration?.let { it1 -> currentProgress?.div(it1) } ?: 0
            Lg.debug(tag, "progress : ${mMusicView?.getProgressBar()?.progress}")
        }

        fun disposeUpdateProgress() {
            if (mSubscribeUpdateProgress?.isDisposed == true) mSubscribeUpdateProgress?.dispose()
            mSubscribeUpdateProgress = null
            mStopPredicate = true
        }

    }

}