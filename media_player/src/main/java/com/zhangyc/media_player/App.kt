package com.zhangyc.media_player

import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.db.ReadSdMedia

class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        ReadSdMedia.instance.update(this)
    }

}