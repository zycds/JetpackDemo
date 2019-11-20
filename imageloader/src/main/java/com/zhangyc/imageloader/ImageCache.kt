package com.zhangyc.imageloader

import android.graphics.Bitmap

interface ImageCache {
    fun get(url : String) : Bitmap?
    fun put(url : String, bitmap: Bitmap)
}