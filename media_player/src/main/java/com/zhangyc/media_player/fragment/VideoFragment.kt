package com.zhangyc.media_player.fragment

import android.content.Context
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.media_player.ConstantKey
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.VideoContact
import kotlinx.android.synthetic.main.fragment_music.*

class VideoFragment : BaseFragment<VideoContact.VideoPresenter>(), VideoContact.IVideoView  {

    @InjectPresenter
    lateinit var mPresenter : VideoContact.VideoPresenter

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_video
    }

    override fun initData() {
        setOnClickListeners(skip_next, skip_prev, fast_forward, fast_rewind, playing)
        val position = arguments?.getInt(ConstantKey.KEY_MEDIA_CLICK_POSITION) ?: 0
        mPresenter.playVideo(position)
    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context? {
        return activity
    }


}