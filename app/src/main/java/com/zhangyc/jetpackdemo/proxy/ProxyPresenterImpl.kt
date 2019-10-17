package com.zhangyc.jetpackdemo.proxy

import android.util.Log
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView

class ProxyPresenterImpl : IProxyPresenter{
    private val tag : String = "ProxyPresenterImpl"
    private lateinit var mPresenters : MutableList<IBasePresenter>

    override fun bindPresenter(baseView: IBaseView) {
        mPresenters = mutableListOf()
        val declaredFields = baseView.javaClass.declaredFields
        for(index in 0 until declaredFields.size) {
            Log.d(tag,"simpleName:${declaredFields[index].type.simpleName} , declaredFields size : ${declaredFields.size}")
            val annotation : InjectPresenter? = declaredFields[index].getAnnotation(InjectPresenter::class.java)
            if (annotation != null) {
                val newInstance = declaredFields[index].type.newInstance()
                var basePresenter : IBasePresenter? = null
                if (newInstance is IBasePresenter) {
                    basePresenter = newInstance
                }
                basePresenter?.attachView(baseView)
                declaredFields[index].set(baseView, basePresenter)
                basePresenter?.let { mPresenters.add(it) }
            }
        }
    }

    override fun unBindPresenter() {
        for (index in 0 until mPresenters.size) {
            mPresenters[index].deAttachView()
        }
    }


}
