package com.zhangyc.jetpackdemo.activity

import android.content.Context
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.base.BaseActivity
import com.zhangyc.jetpackdemo.mvp.MainContact
import com.zhangyc.jetpackdemo.utils.Lg
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainActivity : BaseActivity<MainContact.MainPresenter>() {


    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id){
            R.id.text_main_container->{
                Lg.info(tag, "text_main_container.")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun init() {
        Lg.debug(tag,"init.")
        setContentView(R.layout.activity_main)
        val toolbarView : Toolbar? = toolbar as Toolbar?
        toolbarView?.title = "MainToolBar"
//        toolbarView?.navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_50dp)
        toolbarView?.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_40dp)
        supportActionBar?.setHomeButtonEnabled(true)
        setSupportActionBar(toolbarView)



    }

    override fun initData() {

    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context? {
        return this
    }

}
