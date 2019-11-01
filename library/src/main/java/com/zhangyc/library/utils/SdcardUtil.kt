package com.zhangyc.library.utils

import android.os.Environment

class SdcardUtil {

    companion object {
        val instance : SdcardUtil by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SdcardUtil() }
    }


    fun isExistSdcard() : Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun getSdcardPath() : String? {
        if (isExistSdcard()) {
            return Environment.getExternalStorageDirectory().path
        }
        return null
    }

}