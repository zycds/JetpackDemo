package com.zhangyc.jetpackdemo.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseActivity
import com.zhangyc.jetpackdemo.mvp.SplashContact
import com.zhangyc.jetpackdemo.room.AppDataBase
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.jetpackdemo.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashContact.SplashPresenter>(), SplashContact.ISplashView {

    private val loggerTag = SplashActivity::class.java.simpleName

    @InjectPresenter
    lateinit var mPresenter: SplashContact.SplashPresenter

    private val nameObservable = Observer<String> {

        Log.d(loggerTag, "nameObservable : $it")
    }

    private val url = "http://b-ssl.duitang.com/uploads/item/201209/07/20120907181244_tGiNN.jpeg"

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun init() {
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
    }

    override fun initData() {
        getImageView().setImageResource(R.mipmap.splash)
        mPresenter.getNewSplashImage()

        val get = ViewModelProviders.of(this).get(TestViewModel::class.java)
        get.name = MutableLiveData()
        get.name!!.observe(this, nameObservable)
        get.name!!.value = "haha123"

        get.password = ""




    }

    override fun refreshData() {
        Glide.with(this).load(url).into(getImageView())
    }

    override fun resume() {
        super.resume()
        (application as App).getMainHandler().postDelayed({
            val userDao = AppDataBase.getDBInstance().getUserDao()
            val allUsers = userDao.getAllUsers()
            for(user in allUsers) {
                Lg.info(tag, "user : ${user.username}")
            }
            val intent = Intent(this, MainActivity::class.java)
            if (allUsers.size > 0) {
                intent.putExtra("username", allUsers[0].username)
                intent.putExtra("password", allUsers[0].password)
            }
            startActivity(intent)
        }, 3000)
    }

    override fun unInit() {

    }

    override fun back(): Boolean {
        return true
    }

    override fun getActivityContext(): Context {
        return this
    }

    override fun getImageView(): ImageView {
        return image_splash
    }

}