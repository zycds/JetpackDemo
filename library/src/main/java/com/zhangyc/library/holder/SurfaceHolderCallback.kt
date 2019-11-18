package com.zhangyc.library.holder

import android.media.MediaPlayer
import android.view.SurfaceHolder
import com.zhangyc.jetpackdemo.utils.Lg

class SurfaceHolderCallback(mediaPlayer: MediaPlayer) : SurfaceHolder.Callback {

    private val tag = SurfaceHolderCallback::class.java.simpleName

    private var mMediaPlayer: MediaPlayer = mediaPlayer

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Lg.debug(tag, "surfaceCreated...")
        mMediaPlayer.setDisplay(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Lg.debug(tag, "surfaceChanged...")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Lg.debug(tag, "surfaceDestroyed...")
    }

}