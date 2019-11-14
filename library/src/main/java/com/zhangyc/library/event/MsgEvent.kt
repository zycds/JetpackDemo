package com.zhangyc.library.event

class MsgEvent(var code: Int, var msg: String) {

    companion object {
        const val REFRESH_DATA_FRAGMENT = 101
        const val REFRESH_MUSIC_PLAY_STATUS_PLAYING = 201
        const val REFRESH_MUSIC_PLAY_STATUS_PAUSE = 202
        const val REFRESH_MUSIC_INFO = 203
    }

}
