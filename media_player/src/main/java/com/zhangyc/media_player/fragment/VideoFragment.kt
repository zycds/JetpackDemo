package com.zhangyc.media_player.fragment

import android.content.Context
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.VideoContact

class VideoFragment : BaseFragment<VideoContact.VideoPresenter>(), VideoContact.IVideoView  {
    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_video
    }

    override fun initData() {
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return activity
    }


}