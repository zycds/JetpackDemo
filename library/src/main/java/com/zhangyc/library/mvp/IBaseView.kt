package com.zhangyc.library.mvp

import android.content.Context
import android.view.View


interface IBaseView : View.OnClickListener {

    fun showLoadingDialog()

    fun dismissLoadingDialog()

    fun getActivityContext() : Context?

}