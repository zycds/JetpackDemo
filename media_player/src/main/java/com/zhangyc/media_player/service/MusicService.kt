package com.zhangyc.media_player.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return MusicBinder()
    }

    inner class MusicBinder : Binder() {

        fun play() {

        }

        fun skipNext() {

        }

        fun skipPrev() {

        }

        fun fastForward() {

        }

        fun fastRewind() {

        }

    }
}