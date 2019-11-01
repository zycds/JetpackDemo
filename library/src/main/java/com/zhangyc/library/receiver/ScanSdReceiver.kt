package com.zhangyc.library.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus

class ScanSdReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_MEDIA_SCANNER_STARTED = 10001
        const val ACTION_MEDIA_SCANNER_FINISHED = 10002
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        when(p1?.action) {
            Intent.ACTION_MEDIA_SCANNER_STARTED->{
                RxBus.instance.post(MsgEvent(ACTION_MEDIA_SCANNER_STARTED, "action_media_scanner_started"))
            }
            Intent.ACTION_MEDIA_SCANNER_FINISHED->{
                RxBus.instance.post(MsgEvent(ACTION_MEDIA_SCANNER_FINISHED, "action_media_scanner_finished"))
            }
        }

    }

}