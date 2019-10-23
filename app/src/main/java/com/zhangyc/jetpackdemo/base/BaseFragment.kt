package com.zhangyc.jetpackdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhangyc.jetpackdemo.proxy.ProxyActivity
import com.zhangyc.jetpackdemo.proxy.ProxyFragment

abstract class BaseFragment<P : IBasePresenter> : ProxyFragment() {

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
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(getLayoutResId(), null)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        unInit()
    }

    protected abstract fun init()

    protected abstract fun getLayoutResId() : Int

    protected abstract fun initData()

    protected abstract fun refreshData()

    protected abstract fun unInit()


}