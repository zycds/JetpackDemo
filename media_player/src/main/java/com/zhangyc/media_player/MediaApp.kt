package com.zhangyc.media_player

import android.util.Log
import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.db.ReadSdMedia
import io.vov.vitamio.Vitamio

class MediaApp : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        ReadSdMedia.instance.update(this)
        Vitamio.initialize(this)
        Log.d("MediaApp", "onCreate. ${Vitamio.isInitialized(this)}")
    }

}