package com.zhangyc.jetpackdemo.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhangyc.jetpackdemo.event.MsgEvent
import com.zhangyc.jetpackdemo.event.RxBus
import com.zhangyc.jetpackdemo.proxy.ProxyFragment
import com.zhangyc.jetpackdemo.utils.Lg

abstract class BaseFragment<P : IBasePresenter> : ProxyFragment() {

    lateinit var bTag : String

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

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bTag = this::class.java.simpleName
        RxBus.instance.toObservable(this, MsgEvent::class.java).filter(object : io.reactivex.functions.Predicate<MsgEvent> {
            override fun test(t: MsgEvent): Boolean {
                if (t.code == MsgEvent.REFRESH_DATA_FRAGMENT && bTag == t.msg) {
                    Lg.debug(bTag, "true code : ${t.code}")
                    return true
                }
                return false
            }
        }).subscribe {
            Lg.debug(bTag, "refreshData : ${it.msg}")
            refreshData()
        }
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

    override fun showLoadingDialog() {

    }

    override fun dismissLoadingDialog() {

    }

}