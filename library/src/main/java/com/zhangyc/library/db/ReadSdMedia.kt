package com.zhangyc.library.db

import android.content.Intent
import android.content.IntentFilter
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.receiver.ScanSdReceiver
import com.zhangyc.library.utils.SdcardUtil

@Suppress("UNCHECKED_CAST")
class ReadSdMedia {

    companion object {
        val instance: ReadSdMedia by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ReadSdMedia() }

        val tag = ReadSdMedia::class.java.simpleName
    }

    fun sendScanSdcardReceiver() {
        Lg.debug(tag, "isExistSdcard :  ${SdcardUtil.instance.isExistSdcard()}")
        Lg.debug(tag, "sdcard path : ${SdcardUtil.instance.getSdcardPath()}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(BaseApp.instance, arrayOf(SdcardUtil.instance.getSdcardPath().toString()), null
            ) { p0, p1 ->
                Lg.debug(tag, "p0 : $p0, p1 : $p1")
                RxBus.instance.post(MsgEvent(ScanSdReceiver.ACTION_MEDIA_SCANNER_FINISHED, "MediaScannerConnection scanFile."))
            }
        } else {
            BaseApp.instance.sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(SdcardUtil.instance.getSdcardPath())))
        }
    }

    fun registerScanSdcardReceiver() {
        val scanSdReceiver = ScanSdReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED)
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED)
        BaseApp.instance.registerReceiver(scanSdReceiver, intentFilter)
    }

    fun <D : Media> getSdcardMediaLists() : MutableList<D> {
        val mutableListOf = mutableListOf<D>()
        val query = BaseApp.instance.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME
            ), null, null, null
        )
        val moveToNext = query?.moveToNext()
        while (moveToNext!!) {
            val title = query.getString(query.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val artist = query.getString(query.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val id = query.getInt(query.getColumnIndex(MediaStore.Audio.Media._ID))
            val displayName = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
            val media = Media(id, title, artist, displayName)
            mutableListOf.add(media as D )
        }
        Lg.debug(tag, "size : ${mutableListOf.size}")
        return mutableListOf
    }

}