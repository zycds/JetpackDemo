package com.zhangyc.media_player.mvp

import android.annotation.TargetApi
import android.graphics.PixelFormat
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.TimeUtils
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.holder.SurfaceHolderCallback
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView

interface VideoContact {

    interface IVideoView : IBaseView {
        fun getSurfaceView(): SurfaceView
        fun getBottomSurfaceView(): SurfaceView
    }

    class VideoPresenter : IBasePresenter, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

        private val tag = VideoPresenter::class.java.simpleName

        private var mVideoView: IVideoView? = null

        private var position: Int = 0

        private lateinit var mMediaPlayer: MediaPlayer
        private lateinit var mMediaPlayer2: MediaPlayer

        private lateinit var mSurfaceHolderCallback: SurfaceHolderCallback
        private lateinit var mSurfaceHolderCallback2: SurfaceHolderCallback

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun <V : IBaseView> attachView(v: V) {
            mVideoView = v as IVideoView
            mMediaPlayer2 = MediaPlayer()
            mMediaPlayer = MediaPlayer()
            mMediaPlayer.setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build())
            mMediaPlayer2.setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build())
            mSurfaceHolderCallback = SurfaceHolderCallback(mMediaPlayer)
            mSurfaceHolderCallback2 = SurfaceHolderCallback(mMediaPlayer2)


            mMediaPlayer2.setOnCompletionListener(this)
            mMediaPlayer2.setOnPreparedListener(this)
            mMediaPlayer2.setOnErrorListener(this)

            mMediaPlayer.setOnCompletionListener(this)
            mMediaPlayer.setOnPreparedListener(this)
            mMediaPlayer.setOnErrorListener(this)
        }

        override fun deAttachView() {
        }

        override fun requestFinish(success: Boolean) {

        }

        fun playVideo(position: Int) {
            if (mMediaPlayer2.isPlaying) {
                mMediaPlayer2.pause()
                return
            }
            if (position > 0) this.position = checkPosition(position)
            val v2 = ReadSdMedia.instance.getVideoLists()?.get(this.position + 1)
//            mVideoView?.getBottomSurfaceView()?.holder?.setFormat(PixelFormat.TRANSPARENT)
            mVideoView?.getBottomSurfaceView()?.setZOrderOnTop(true)
            mVideoView?.getBottomSurfaceView()?.holder?.addCallback(mSurfaceHolderCallback2)
            Lg.debug(tag, "path : ${v2?.path} , ${FileUtils.isFileExists(v2?.path)}")
            mMediaPlayer2.setDataSource(v2?.path)
            mMediaPlayer2.prepareAsync()


            if (mMediaPlayer.isPlaying) {
                mMediaPlayer.pause()
                return
            }
            if (position > 0) this.position = checkPosition(position)
            val v = ReadSdMedia.instance.getVideoLists()?.get(this.position)
            mVideoView?.getSurfaceView()?.holder?.addCallback(mSurfaceHolderCallback)
            Lg.debug(tag, "path : ${v?.path} , ${FileUtils.isFileExists(v?.path)}")
            mMediaPlayer.setDataSource(v?.path)
            mMediaPlayer.prepareAsync()


        }

        fun playNext() {
            mMediaPlayer.reset()
            playVideo(++this.position)
        }

        fun playPrev() {

        }

        fun pause() {
            if (mMediaPlayer.isPlaying) mMediaPlayer.pause()
            if (mMediaPlayer2.isPlaying) mMediaPlayer2.pause()
            mMediaPlayer.reset()
            mMediaPlayer2.reset()
        }

        private fun checkPosition(position: Int): Int {
            if (position < ReadSdMedia.instance.getVideoLists()?.size ?: 0) {
                return position
            }
            return 0
        }

        override fun onPrepared(mp: MediaPlayer?) {
            Lg.debug(tag, "onPrepared...${mp?.isPlaying}")
            mp?.currentPosition?.toLong()?.let { TimeUtils.millis2String(it) }
            mp?.duration?.toLong()?.let { TimeUtils.millis2String(it) }
            if (!mp?.isPlaying!!) {
                mp.start()
            }
            mp.isLooping = true
        }

        override fun onCompletion(mp: MediaPlayer?) {
            playNext()
        }

        override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
            Lg.debug(tag, "what : $what, extra : $extra")
            return false
        }


    }

}