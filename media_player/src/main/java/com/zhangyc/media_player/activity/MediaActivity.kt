package com.zhangyc.media_player.activity

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseActivity
import com.zhangyc.library.base.BaseApp
import com.zhangyc.media_player.R
import com.zhangyc.media_player.bean.Music
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.utils.SdcardUtil
import com.zhangyc.media_player.mvp.MediaContact
import kotlinx.android.synthetic.main.activity_media.*

class MediaActivity : BaseActivity<MediaContact.MediaPresenter>(), MediaContact.IMediaView {

    @SuppressLint("CheckResult")
    override fun init() {
        setContentView(R.layout.activity_media)
        Lg.debug("sendScanSdcardReceive", "isExistSdcard :  ${SdcardUtil.instance.isExistSdcard()}")
        RxBus.instance.toObservable(this, MsgEvent::class.java)
            .subscribe({
                val musicLists = ReadSdMedia.instance.getSdcardMediaLists<Music>()
                Lg.debug(tag, "musicList size : ${musicLists.size}")
            }, {
                Lg.debug(tag, "it : $it")
            })
        ReadSdMedia.instance.registerScanSdcardReceiver()
        ReadSdMedia.instance.sendScanSdcardReceiver()

        recyclerView_media.layoutManager = LinearLayoutManager(this)
        recyclerView_media.adapter =  object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(TextView(BaseApp.instance)) {

                }
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            }

            override fun getItemCount(): Int {
                return 0
            }

        }

        Lg.debug(tag, "init")
    }

    override fun initData() {

    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun back(): Boolean {
        return false
    }

    override fun getActivityContext(): Context? {
        return this
    }

    override fun getRecyclerView(): RecyclerView {
        return recyclerView_media
    }

}
