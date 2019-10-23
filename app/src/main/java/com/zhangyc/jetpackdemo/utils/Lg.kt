package com.zhangyc.jetpackdemo.utils

import android.util.Log


class Lg {

    companion object {

        fun info(tag: String, msg: String) {
            if (AppUtil.isDebug()) Log.i(tag, msg)
        }

        fun warning(tag: String, msg: String) {
            if (AppUtil.isDebug()) Log.w(tag, msg)
        }

        fun debug(tag: String, msg: String) {
            if (AppUtil.isDebug()) Log.d(tag, msg)
        }

        fun error(tag: String, msg: String) {
            Log.e(tag, msg)
        }
    }


}