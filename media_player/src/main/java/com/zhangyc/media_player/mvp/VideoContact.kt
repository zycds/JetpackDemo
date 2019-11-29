package com.zhangyc.media_player.mvp

import android.annotation.TargetApi
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.TimeUtils
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.holder.SurfaceHolderCallback
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

interface VideoContact {

    interface IVideoView : IBaseView {
        fun surfaceViewAddCallback()
    }

    class VideoPresenter : IBasePresenter, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

        private val tag = VideoPresenter::class.java.simpleName

        private var mVideoView: IVideoView? = null

        var position: Int = 0

        lateinit var mMediaPlayer: MediaPlayer
        lateinit var mMediaPlayer2: MediaPlayer

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun <V : IBaseView> attachView(v: V) {
            mVideoView = v as IVideoView
            initMediaPlayer()
        }

        fun getSurfaceCallback(mediaPlayer: MediaPlayer) : SurfaceHolderCallback {
            return SurfaceHolderCallback(mediaPlayer)
        }

        private fun initMediaPlayer() {
            mMediaPlayer = createMediaPlayer(getPositionPath(position))
            mMediaPlayer2 = createMediaPlayer(getPositionPath(position + 1))
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        private fun createMediaPlayer(path: String?): MediaPlayer {
            if (path == null) throw NullPointerException("mediaPlayer path is NULL.")
            if (!FileUtils.isFileExists(path)) throw IllegalArgumentException("mediaPlayer path : $path is not exists.")
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build())
            mediaPlayer.setOnCompletionListener(this)
            mediaPlayer.setOnPreparedListener(this)
            mediaPlayer.setOnErrorListener(this)
            prepareMediaPlayer(mediaPlayer, path)
            return mediaPlayer
        }

        override fun deAttachView() {

        }

        override fun requestFinish(success: Boolean) {

        }

        fun playVideo() {
            Lg.debug(tag, "isPlaying ：${mMediaPlayer.isPlaying}")
            if (mMediaPlayer.isPlaying) {
                mMediaPlayer.pause()
                mMediaPlayer2.pause()
                return
            }
            mMediaPlayer.start()
            mMediaPlayer2.start()
        }

        fun playNext() {
            pause()
            prepareMediaPlayer(mMediaPlayer, getPositionPath(++position))
            prepareMediaPlayer(mMediaPlayer2, getPositionPath(position + 1))
        }

        fun playPrev() {

        }

        fun pause() {
            if (mMediaPlayer.isPlaying) mMediaPlayer.pause()
            if (mMediaPlayer2.isPlaying) mMediaPlayer2.pause()
            mMediaPlayer.reset()
            mMediaPlayer2.reset()
        }

        private fun prepareMediaPlayer(mediaPlayer: MediaPlayer, path: String?) {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
        }

        private fun getPositionPath(position: Int) : String?{
            return ReadSdMedia.instance.getVideoLists()?.get(checkPosition(position))?.path
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