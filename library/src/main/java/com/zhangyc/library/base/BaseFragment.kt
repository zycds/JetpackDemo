package com.zhangyc.library.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Debug
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import com.zhangyc.library.event.RxHelper
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.proxy.ProxyFragment
import io.reactivex.disposables.Disposable

abstract class BaseFragment<P : IBasePresenter> : ProxyFragment() {
    lateinit var bTag : String

    private var subscribe : Disposable? = null

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
//        val traceName = "trace".plus(bTag)
//        Debug.startMethodTracing(traceName)
        subscribe = RxBus.instance.toObservable(this, MsgEvent::class.java)
            .filter(object : io.reactivex.functions.Predicate<MsgEvent> {
                override fun test(t: MsgEvent): Boolean {
                    if (t.code == MsgEvent.REFRESH_DATA_FRAGMENT && bTag == t.msg) {
                        Lg.debug(bTag, "true code : ${t.code}")
                        return true
                    }
                    return false
                }
            }).compose(RxHelper.handlerResultIO()).subscribe {
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
//        Debug.stopMethodTracing()
        if (subscribe?.isDisposed!!) subscribe?.dispose()
        unInit()
    }

    protected abstract fun init()

    protected abstract fun getLayoutResId() : Int

    protected abstract fun initData()

    protected abstract fun refreshData()

    protected abstract fun unInit()

    fun back() : Boolean {
        return false
    }

    override fun showLoadingDialog() {

    }

    override fun dismissLoadingDialog() {

    }

}