package com.zhangyc.media_player.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.zhangyc.jetpackdemo.utils.Lg

class MusicService : Service() {

    companion object {
        val tag = MusicService::class.java.simpleName
    }

    override fun onBind(p0: Intent?): IBinder? {
        return MusicBinder()
    }

    inner class MusicBinder : Binder() {

        fun play() {
            Lg.debug(tag, "play...")
        }

        fun skipNext() {
            Lg.debug(tag, "skipNext...")
        }

        fun skipPrev() {
            Lg.debug(tag, "skipPrev...")
        }

        fun fastForward() {
            Lg.debug(tag, "fastForward...")
        }

        fun fastRewind() {
            Lg.debug(tag, "fastRewind...")
        }

        fun getCurrentProgress() : Long {
            return 0
        }
    }
}