package com.zhangyc.media_player.fragment

import android.content.Context
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.PicContact

class PicFragment : BaseFragment<PicContact.PicPresenter>(), PicContact.IPicView {

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_pic
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