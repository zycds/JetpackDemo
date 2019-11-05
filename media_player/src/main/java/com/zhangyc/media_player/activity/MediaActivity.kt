package com.zhangyc.media_player.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ProcessUtils
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseActivity
import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.db.Media
import com.zhangyc.media_player.R
import com.zhangyc.media_player.bean.Music
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.event.RxHelper
import com.zhangyc.library.receiver.ScanSdReceiver
import com.zhangyc.library.utils.SdcardUtil
import com.zhangyc.media_player.mvp.MediaContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_media.*
import kotlinx.android.synthetic.main.item_music.view.*

class MediaActivity : BaseActivity<MediaContact.MediaPresenter>(), MediaContact.IMediaView {

    private lateinit var musicLists : MutableList<Media>

    @SuppressLint("CheckResult")
    override fun init() {
        setContentView(R.layout.activity_media)
        Lg.debug(tag, "isExistSdcard :  ${SdcardUtil.instance.isExistSdcard()}")
        RxBus.instance.toObservable(this, MsgEvent::class.java)
            .compose(RxHelper.handlerResultIO())
            .subscribe({
                Lg.debug(tag, "code :  ${it.code}")
                when(it.code) {
                    ScanSdReceiver.ACTION_MEDIA_SCANNER_FINISHED->{
                        musicLists = ReadSdMedia.instance.getSdcardMediaLists()
                        Lg.debug(tag, "musicList size : ${musicLists.size}")
                        getRecyclerView().adapter?.notifyDataSetChanged()
                    }
                }
            }, {
                Lg.debug(tag, "it : $it")
            })
        ReadSdMedia.instance.registerScanSdcardReceiver()
        ReadSdMedia.instance.sendScanSdcardReceiver()
        Lg.debug(tag, "init")
    }

    override fun initData() {
        Lg.debug(tag, "bar height : ${BarUtils.getActionBarHeight()}")
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
        if (recyclerView_media.adapter == null) {
            recyclerView_media.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView_media.addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = 10
                    outRect.bottom = 10
                }
            })
            recyclerView_media.adapter = Adapter()
        }
        return recyclerView_media
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(BaseApp.instance.applicationContext).inflate(R.layout.item_music, parent, false))
        }

        override fun getItemCount(): Int {
            return musicLists.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Lg.debug(tag, "size : ${musicLists.size}, position : $position . title : ${musicLists[position].title}")
            holder.itemView.text_title.text = musicLists[position].title
            holder.itemView.text_date.text = musicLists[position].dateModify
        }

    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}
