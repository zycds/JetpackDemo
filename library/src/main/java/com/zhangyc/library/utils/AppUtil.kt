package com.zhangyc.library.utils

import android.content.Context
import android.content.pm.ApplicationInfo

class AppUtil {

    companion object {
        private var isDebug : Boolean? = null
        fun isDebug() : Boolean {
            return if(isDebug == null) false else isDebug!!
        }
        fun syncIsDebug(context: Context) {
            if (isDebug == null)
                isDebug = context.applicationInfo != null && (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        }

    }

}