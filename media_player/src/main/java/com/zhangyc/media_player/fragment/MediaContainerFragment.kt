package com.zhangyc.media_player.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseAdapter
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.library.db.Media
import com.zhangyc.library.db.Music
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.MediaContainerContact
import kotlinx.android.synthetic.main.fragment_media_container.*

class MediaContainerFragment : BaseFragment<MediaContainerContact.MediaContainerPresenter>(),
    MediaContainerContact.IMediaContainerView {

    @InjectPresenter
    lateinit var mPresenter : MediaContainerContact.MediaContainerPresenter


    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id!!) {
            R.id.image_music->{
                setAdapterAndClickListener<Music>()
                val bundle = Bundle()
                bundle.putInt("position", 1)
                NavHostFragment.findNavController(this).navigate(R.id.musicFragment, bundle)
            }
            R.id.image_video->{
                val bundle = Bundle()
                bundle.putInt("position", 1)
                NavHostFragment.findNavController(this).navigate(R.id.videoFragment, bundle)
            }
            R.id.image_pic->{

            }
        }
    }

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_media_container
    }

    override fun initData() {
        setOnClickListeners(image_music, image_video, image_pic)
        mPresenter.registerObservableSdScan()
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return activity
    }

    override fun getRecyclerView(): RecyclerView {
        if (recyclerView_media.adapter == null) {
            recyclerView_media.layoutManager = LinearLayoutManager(context)
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

        }
        return recyclerView_media
    }

    private fun <D : Media> setAdapterAndClickListener() {
        recyclerView_media.adapter = mPresenter.getAdapter<D>()
        mPresenter.getAdapter<D>().setOnRecyclerOnItemClickListener(object : BaseAdapter.OnItemClickListener<D> {
            override fun itemClick(position: Int, data: D?) {

            }
        })
    }
}