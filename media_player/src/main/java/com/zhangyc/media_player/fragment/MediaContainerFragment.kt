package com.zhangyc.media_player.fragment

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.library.db.Music
import com.zhangyc.library.db.ReadSdMedia
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.MediaContainerContact
import kotlinx.android.synthetic.main.fragment_media_container.*

class MediaContainerFragment : BaseFragment<MediaContainerContact.MediaContainerPresenter>(),
    MediaContainerContact.IMediaContainerView {

    @InjectPresenter
    lateinit var mPresenter: MediaContainerContact.MediaContainerPresenter


    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        val mediaTag = when (id!!) {
            R.id.image_music -> MediaContainerContact.MediaTag.MUSIC
            R.id.image_video -> MediaContainerContact.MediaTag.VIDEO
            R.id.image_pic -> MediaContainerContact.MediaTag.PIC
            else -> MediaContainerContact.MediaTag.MUSIC
        }
        mPresenter.setAdapterAndClickListener<Music>(tag = mediaTag)
    }

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_media_container
    }

    override fun initData() {
        setOnClickListeners(image_music, image_video, image_pic)
        mPresenter.registerObservableSdScan()
        mPresenter.setAdapterAndClickListener<Music>(tag = MediaContainerContact.MediaTag.MUSIC)
    }

    override fun refreshData() {
        ReadSdMedia.instance.sendScanSdcardReceiver()
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return activity
    }

    override fun getRecyclerView(): RecyclerView {
        if (recyclerView_media.adapter == null) {
            recyclerView_media.layoutManager = LinearLayoutManager(context)
            recyclerView_media.addItemDecoration(object : RecyclerView.ItemDecoration() {
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

        }
        return recyclerView_media
    }

    override fun getCurrentFragment(): MediaContainerFragment {
        return this
    }

}