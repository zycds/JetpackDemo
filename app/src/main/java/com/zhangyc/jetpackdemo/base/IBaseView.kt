package com.zhangyc.jetpackdemo.base

import android.content.Context
import android.view.View

interface IBaseView : View.OnClickListener{

    fun getActivityContext() : Context?

}