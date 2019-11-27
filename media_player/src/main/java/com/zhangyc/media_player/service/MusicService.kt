package com.zhangyc.media_player.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.db.Music
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus

class MusicService : Service() {

    companion object {
        val tag = MusicService::class.java.simpleName
        const val SPACE_TIME = 5000
    }

    override fun onBind(p0: Intent?): IBinder? {
        return MusicBinder()
    }

    inner class MusicBinder : Binder() {

        private var mediaPlayer : MediaPlayer? = null

        private fun initMediaPlayer(music : Music?) {
            mediaPlayer?.reset()
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(music?.path)
            mediaPlayer?.isLooping = false
            mediaPlayer?.prepare()
            mediaPlayer?.setOnCompletionListener { skipNext() }
        }

        fun play(position : Int) {
            Lg.debug(tag, "play... $position")
            initMediaPlayer(ReadSdMedia.instance.getMusicLists()?.get(checkPosition(position)))
            playPause()
        }

        fun checkPosition(position : Int) : Int{
            if (position < ReadSdMedia.instance.getMusicLists()?.size ?: 0) {
                return position
            }
            return 0
        }

        fun playPause() {
            if(mediaPlayer!!.isPlaying){
                mediaPlayer?.pause()
                RxBus.instance.post(MsgEvent(MsgEvent.REFRESH_MUSIC_PLAY_STATUS_PAUSE, "pause"))
            } else {
                mediaPlayer?.start()
                RxBus.instance.post(MsgEvent(MsgEvent.REFRESH_MUSIC_PLAY_STATUS_PLAYING, "playing"))
            }
        }

        fun skipNext() {
            Lg.debug(tag, "skipNext...")
            RxBus.instance.post(MsgEvent(MsgEvent.REFRESH_MUSIC_INFO, "skipNext"))
        }

        fun skipPrev() {
            Lg.debug(tag, "skipPrev...")
            RxBus.instance.post(MsgEvent(MsgEvent.REFRESH_MUSIC_INFO, "skipPrev"))
        }

        fun fastForward() {
            Lg.debug(tag, "fastForward...")
            val p : Int = getCurrentProgress() + SPACE_TIME
            if (p > getDuration()) {
                skipNext()
                return
            }
            mediaPlayer?.seekTo(p)
        }

        fun fastRewind() {
            Lg.debug(tag, "fastRewind...")
            var p : Int = getCurrentProgress() - SPACE_TIME
            if (p < 0) p = 0
            mediaPlayer?.seekTo(p)
        }

        fun getCurrentProgress() : Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        fun getMediaPlayerStatus() : Boolean {
            return mediaPlayer!!.isPlaying
        }

        fun getDuration() : Int {
            return mediaPlayer?.duration ?: 0
        }
    }
}