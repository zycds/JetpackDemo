package com.zhangyc.jetpackdemo.utils

import android.content.Context
import android.widget.Toast

class ToastUtil {

    companion object {

        fun showShortToast(context : Context, text : String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(context : Context, text : String) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

    }

}