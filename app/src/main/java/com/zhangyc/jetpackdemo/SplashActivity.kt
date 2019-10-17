package com.zhangyc.jetpackdemo

import android.content.Context
import android.widget.ImageView
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseActivity
import com.zhangyc.jetpackdemo.mvp.SplashContact
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashContact.SplashPresenter>(), SplashContact.ISplashView {

    @InjectPresenter
    lateinit var mPresenter : SplashContact.SplashPresenter

    //http://b-ssl.duitang.com/uploads/item/201209/07/20120907181244_tGiNN.jpeg
    override fun init() {
        setContentView(R.layout.activity_splash)
    }

    override fun initData() {
        getImageView().setImageResource(R.mipmap.splash)
        mPresenter.getNewSplashImage()
    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context {
        return this
    }

    override fun getImageView(): ImageView {
        return image_splash
    }

}