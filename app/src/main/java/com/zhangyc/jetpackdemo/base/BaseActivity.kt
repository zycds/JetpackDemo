package com.zhangyc.jetpackdemo.base

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.proxy.ProxyActivity

abstract class BaseActivity<P : IBasePresenter> : ProxyActivity() {

    protected lateinit var tag : String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
            R.id.action_bar_refresh->{
                refreshData()
            }
            R.id.action_bar_more->{

            }
        }
        return true
    }

    override fun onClick(p0: View?) {
        handlerClickListener(p0?.id)
    }

    protected fun setOnClickListeners(vararg views : View) {
        for(v in views) {
            v.setOnClickListener(this)
        }
    }

    open fun handlerClickListener (id : Int?){
        if (id == null) return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tag = this.javaClass.simpleName
        init()
        initData()
    }

    override fun onResume() {
        super.onResume()
        resume()
        refreshData()
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        unInit()
    }

    protected abstract fun init()

    protected abstract fun initData()

    protected abstract fun refreshData()

    protected abstract fun unInit()

    open fun resume(){}

    open fun pause() {}

}