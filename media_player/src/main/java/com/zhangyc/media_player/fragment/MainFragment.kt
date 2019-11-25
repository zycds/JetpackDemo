package com.zhangyc.media_player.fragment

import android.content.Context
import com.zhangyc.library.base.BaseFragment
import com.zhangyc.media_player.R
import com.zhangyc.media_player.mvp.MainContact

class MainFragment : BaseFragment<MainContact.MainPresenter>(), MainContact.IMainView {

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
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