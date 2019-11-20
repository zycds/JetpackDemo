package com.zhangyc.imageloader

import android.content.Context
import android.widget.ImageView
import java.io.File
import java.lang.NullPointerException

class ImageLoader {
    companion object {
        val tag = ImageLoader::class.java.simpleName
        val instance: ImageLoader by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ImageLoader()
        }
    }
    var context : Context? = null
    private var mBuilder : ImageRequest.Builder? = null

    fun with(context: Context) : ImageLoader{
        this.context = context
        return this
    }

    fun load(url : String) : ImageLoader{
        checkBuilder().url = url
        return this
    }

    fun load(resId : Int) : ImageLoader{
        checkBuilder().resId = resId
        return this
    }

    fun into(imageView : ImageView)  {
        checkBuilder().imageView = imageView
        RequestManager.instance.submit(RequestManager.instance.createHttpRequest(checkBuilder()))
        mBuilder = null
    }

    fun default(resId : Int) : ImageLoader {
        checkBuilder().resDefaultId = resId
        return this
    }

    fun cacheDisk(cacheDisk : Boolean) : ImageLoader {
        checkBuilder().cacheDisk = cacheDisk
        return this
    }

    private fun checkBuilder() : ImageRequest.Builder {
        if (mBuilder == null) mBuilder = ImageRequest.Builder()
        return mBuilder!!
    }

}