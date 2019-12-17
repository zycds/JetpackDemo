package com.zhangyc.media_player.mvp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.base.BaseAdapter
import com.zhangyc.library.base.BaseApp
import com.zhangyc.library.base.BaseViewHolder
import com.zhangyc.library.db.Media
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.event.RxHelper
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import com.zhangyc.library.receiver.ScanSdReceiver
import com.zhangyc.media_player.ConstantKey
import com.zhangyc.media_player.R
import com.zhangyc.media_player.fragment.MediaContainerFragment
import com.zhangyc.media_player.media_activity.MediaActivity
import com.zhangyc.media_player.media_activity.VideoActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_music.view.*

interface MediaContainerContact {

    companion object {
        val TAG = MediaContainerContact::class.java.simpleName
    }

    enum class MediaTag {
        MUSIC, VIDEO, PIC
    }

    interface IMediaContainerView : IBaseView {
        fun getRecyclerView(): RecyclerView
        fun getCurrentFragment(): MediaContainerFragment
    }

    class MediaContainerPresenter : IBasePresenter {

        private val tag = MediaContainerPresenter::class.java.simpleName

        private var mMediaContainerView: IMediaContainerView? = null

        private var subscribe: Disposable? = null

        private var mCurrentTag : MediaTag = MediaTag.MUSIC

        override fun <V : IBaseView> attachView(v: V) {
            mMediaContainerView = v as IMediaContainerView
        }

        override fun deAttachView() {
            if (subscribe?.isDisposed!!) subscribe?.dispose()
            subscribe = null
        }

        override fun requestFinish(success: Boolean) {
        }

        fun registerObservableSdScan() {
            subscribe =
                RxBus.instance.toObservable(
                    mMediaContainerView?.getActivityContext() as MediaActivity,
                    MsgEvent::class.java
                )
                    .compose(RxHelper.handlerResultIO())
                    .subscribe({
                        Lg.debug(MediaContact.tag, "code :  ${it.code}")
                        when (it.code) {
                            ScanSdReceiver.ACTION_MEDIA_SCANNER_FINISHED -> {
                                ReadSdMedia.instance.querySdcardMusicLists()
                                ReadSdMedia.instance.querySdcardVideoLists()
                                Lg.debug(
                                    MediaContact.tag,
                                    "musicList size : ${ReadSdMedia.instance.getMusicLists()?.size} , video size : ${ReadSdMedia.instance.getVideoLists()?.size}"
                                )
                                mMediaContainerView?.getRecyclerView()?.adapter?.notifyDataSetChanged()
                            }
                        }
                    }, {
                        Lg.debug(MediaContact.tag, "it : $it")
                    })
            ReadSdMedia.instance.sendScanSdcardReceiver()
        }

        fun <D : Media> setAdapterAndClickListener(tag: MediaTag) {
            Lg.debug(TAG, "c : ${tag.name}")
            mCurrentTag = tag
            val adapter = getAdapter<D>()
            mMediaContainerView?.getRecyclerView()?.adapter = adapter
            mMediaContainerView?.getRecyclerView()?.adapter?.notifyDataSetChanged()
            val mediaContainerFragment = mMediaContainerView?.getCurrentFragment()
            adapter.setOnRecyclerOnItemClickListener(object : BaseAdapter.OnItemClickListener<D> {
                override fun itemClick(position: Int, data: D?) {
                    Lg.debug(TAG, "tag : $tag, position : $position")
                    val bundle = Bundle()
                    bundle.putInt(ConstantKey.KEY_MEDIA_CLICK_POSITION, position)
                    val resId = when (tag) {
                        MediaTag.MUSIC -> R.id.musicFragment
                        MediaTag.VIDEO -> R.id.videoFragment
                        MediaTag.PIC -> R.id.picFragment
                    }
//                    mMediaContainerView?.getActivityContext()?.startActivity(Intent(mMediaContainerView?.getActivityContext(), VideoActivity::class.java))
                    NavHostFragment.findNavController(mediaContainerFragment!!).navigate(resId, bundle)
                }
            })
        }

        private fun <D : Media> getAdapter(): Adapter<D> {
            return Adapter()
        }

        inner class Adapter<D : Media> : BaseAdapter<ViewHolder, D>() {
            override fun itemCount(): Int {
                return if (mCurrentTag == MediaTag.MUSIC) ReadSdMedia.instance.getMusicLists()?.size ?: 0 else ReadSdMedia.instance.getVideoLists()?.size ?: 0
            }

            override fun binderViewHolder(holder: ViewHolder, position: Int) {
                val get = if (mCurrentTag == MediaTag.MUSIC) ReadSdMedia.instance.getMusicLists()?.get(position) else ReadSdMedia.instance.getVideoLists()?.get(position)
                Lg.debug(tag, "item position : $position , $mCurrentTag , ${get?.title}")
                holder.itemView.text_title.text = get?.title
                holder.itemView.text_date.text = get?.dateModify
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val resId = if(mCurrentTag == MediaTag.MUSIC) R.layout.item_music else R.layout.item_music
                return ViewHolder(
                    LayoutInflater.from(BaseApp.instance.applicationContext).inflate(
                        resId,
                        parent,
                        false
                    )
                )
            }
        }

        inner class ViewHolder(itemView: View) : BaseViewHolder(itemView)
    }

}