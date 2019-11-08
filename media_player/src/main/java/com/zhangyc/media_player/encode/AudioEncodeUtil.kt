package com.zhangyc.media_player.encode

import android.media.MediaCodec
import android.media.MediaFormat
import android.os.Build

class AudioEncodeUtil {

    fun encode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val createAudioFormat = MediaFormat.createAudioFormat("aac", 44100, 2)
            createAudioFormat.setInteger(MediaFormat.KEY_BIT_RATE, 96000)

            val createEncoderByType = MediaCodec.createEncoderByType("aac")
            createEncoderByType.configure(createAudioFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)

        }


    }

}