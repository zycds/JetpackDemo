package com.zhangyc.library.db

import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.blankj.utilcode.util.SDCardUtils
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.receiver.ScanSdReceiver

class ReadSdMedia {

    companion object {
        val instance: ReadSdMedia by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ReadSdMedia() }

        val tag = ReadSdMedia::class.java.simpleName
    }

    private var mMusicLists : MutableList<Music>? = null

    fun getMusicLists() : MutableList<Music>?{
        return mMusicLists
    }

    fun sendScanSdcardReceiver() {
        Lg.debug(tag, "isExistSdcard :  ${SDCardUtils.isSDCardEnableByEnvironment()}")
        Lg.debug(tag, "sdcard path : ${SDCardUtils.getSDCardPathByEnvironment()}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(
                BaseApp.instance, arrayOf(SDCardUtils.getSDCardPathByEnvironment()), null
            ) { p0, p1 ->
                Lg.debug(tag, "p0 : $p0, p1 : $p1")
                RxBus.instance.post(
                    MsgEvent(
                        ScanSdReceiver.ACTION_MEDIA_SCANNER_FINISHED,
                        "MediaScannerConnection scanFile."
                    )
                )
            }
        } else {
            BaseApp.instance.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse(SDCardUtils.getSDCardPathByEnvironment())
                )
            )
        }
    }

    private var scanSdReceiver: ScanSdReceiver? = null
    fun registerScanSdcardReceiver() {
        scanSdReceiver = ScanSdReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED)
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED)
        BaseApp.instance.registerReceiver(scanSdReceiver, intentFilter)
    }

    fun unRegisterScanSdcardReceiver() {
        if (scanSdReceiver != null) BaseApp.instance.unregisterReceiver(scanSdReceiver)
    }

    fun querySdcardMusicLists(): MutableList<Music>? {
//        val mutableListOf = mutableListOf<D>()
        mMusicLists = mutableListOf()
        val query: Cursor? = BaseApp.instance.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATE_MODIFIED,
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME
            ), null, null, null
        )
        try {
            while ((query?.moveToNext())!!) {
                val title = query.getString(query.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val artist = query.getString(query.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                val id = query.getInt(query.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                val displayName: String? = query.getString(query.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                val size = query.getLong(query.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                val dateModify = query.getString(query.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED))
                val media = Music(id, title, artist, displayName, size, dateModify)
                mMusicLists?.add(media)
            }
        } catch (e: Exception) {
            Lg.debug(tag, "exception : $e")
        } finally {
            query?.close()
        }
        return mMusicLists
    }

}