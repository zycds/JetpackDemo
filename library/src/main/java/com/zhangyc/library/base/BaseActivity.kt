package com.zhangyc.library.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.zhangyc.library.R
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.proxy.ProxyActivity

abstract class BaseActivity<P : IBasePresenter> : ProxyActivity() {

    protected lateinit var tag : String

    private lateinit var progressBar: ProgressBar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                back()
            }
            R.id.action_bar_refresh->{
                refreshData()
            }
            R.id.action_bar_more->{
//                HttpApi.instance.logout()
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

    override fun onSupportNavigateUp(): Boolean {
        return back()
    }

    override fun onBackPressed() {
        if (onSupportNavigateUp()) {
            return
        }
        super.onBackPressed()
    }

    protected abstract fun init()

    protected abstract fun initData()

    protected abstract fun refreshData()

    protected abstract fun unInit()

    override fun showLoadingDialog() {
    }

    override fun dismissLoadingDialog() {
    }

    protected abstract fun back() : Boolean

    open fun resume(){}

    open fun pause() {}

}