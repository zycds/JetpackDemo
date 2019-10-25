package com.zhangyc.jetpackdemo.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseActivity
import com.zhangyc.jetpackdemo.fragment.WebFragment
import com.zhangyc.jetpackdemo.mvp.MainContact
import com.zhangyc.jetpackdemo.utils.Lg
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity<MainContact.MainPresenter>(), MainContact.MainView {

    @InjectPresenter
    lateinit var mPresenter : MainContact.MainPresenter

    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun init() {
        Lg.debug(tag,"init.")
        setContentView(R.layout.activity_main)
        val toolbarView : Toolbar? = toolbar
        toolbarView?.title = "MainToolBar"
//        toolbarView?.navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_50dp)
        toolbarView?.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_40dp)
        supportActionBar?.setHomeButtonEnabled(true)
        setSupportActionBar(toolbarView)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        if (username != null && password != null) {
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putString("password", password)
            main_container.findNavController().navigate(R.id.loginFragment, bundle)
        } else {
            main_container.findNavController().navigate(R.id.registerFragment)
        }
    }

    override fun initData() {

    }

    override fun refreshData() {

    }

    override fun unInit() {

    }

    override fun back(): Boolean {
        Lg.debug(tag, "back.")


        if (supportFragmentManager.findFragmentById(R.id.main_container) is WebFragment) {
            Lg.debug(tag, "targetFragment is WebFragment.")
        }
        return true
    }

    override fun getActivityContext(): Context? {
        return this
    }

}
