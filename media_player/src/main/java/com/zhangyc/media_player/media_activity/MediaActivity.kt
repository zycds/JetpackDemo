package com.zhangyc.media_player.media_activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.RouterConstants
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseActivity
import com.zhangyc.media_player.R
import com.zhangyc.media_player.fragment.MediaContainerFragment
import com.zhangyc.media_player.mvp.MediaContact
import kotlinx.android.synthetic.main.toolbar.*

@Route(path = RouterConstants.ACTIVITY_URL_MEDIA)
open class MediaActivity : BaseActivity<MediaContact.MediaPresenter>(), MediaContact.IMediaView {

    @InjectPresenter
    lateinit var mPresenter : MediaContact.MediaPresenter

    @SuppressLint("CheckResult")
    override fun init() {
        setContentView(R.layout.activity_media)
    }

    override fun initData() {
        Lg.debug(tag, "bar height : ${BarUtils.getActionBarHeight()}")
    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun back(): Boolean {
        val findNavController = supportFragmentManager.findFragmentById(R.id.fragment_media_container)?.findNavController()
        if (MediaContainerFragment::class.java.simpleName == findNavController?.currentDestination?.label) {
            finish()
            return true
        }
        return findNavController?.navigateUp()!!
    }

    override fun getActivityContext(): Context? {
        return this
    }



    override fun getLayoutToolBar(): Toolbar? {
        return toolbar
    }

    override fun setToolBarTitle(): String {
        return "Media"
    }

}
