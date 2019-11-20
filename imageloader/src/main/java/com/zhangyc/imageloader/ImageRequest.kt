package com.zhangyc.imageloader

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ImageRequest(builder: Builder) : Request(){

    companion object {
        const val TIME_OUT = 15 * 1000
    }

    private var mBuilder: Builder = builder

    override fun run() {
        when {
            mBuilder.resId !=0 -> {
                mBuilder.imageView?.setImageResource(mBuilder.resId)
                return
            }
            RequestHelper.instance.get(mBuilder.url) != null -> {
                Log.d(ImageLoader.tag, "use memory cache...")
                Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageBitmap(RequestHelper.instance.get(mBuilder.url)) }
                return
            }
            mBuilder.cacheDisk ->RequestHelper.instance.getDisk(mBuilder.url)?.let{
                Log.d(ImageLoader.tag, "use sdcard cache...")
                Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageBitmap(it) }
            }
            mBuilder.resDefaultId != 0 -> Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageResource(mBuilder.resDefaultId) }
        }
        val openConnection = URL(mBuilder.url).openConnection()
        val conn = if (!mBuilder.https) openConnection as HttpURLConnection else openConnection as HttpsURLConnection
        conn.connectTimeout = mBuilder.timeOut
        conn.requestMethod = mBuilder.httpReqMethod.method
        conn.doInput = true
        conn.doOutput = true
        if (conn.responseCode == 200) {
            val decodeStream = BitmapFactory.decodeStream(conn.inputStream)
            conn.disconnect()
            Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageBitmap(decodeStream) }
            RequestHelper.instance.put(mBuilder.url, decodeStream)
            if (mBuilder.cacheDisk) RequestHelper.instance.cacheDisk(mBuilder.url, decodeStream)
        } else {
            Log.e(ImageLoader.tag, "responseCode : ${conn.responseCode}, ${conn.responseMessage}")
        }
    }

    override fun buildRequest(builder : Request.Builder): Request {
        return ImageRequest(builder as Builder)
    }

    enum class HttpReqMethod(val method: String) {
        GET("GET"), POST("POST")
    }

    class Builder : Request.Builder{
        var httpReqMethod : HttpReqMethod = HttpReqMethod.GET
        var timeOut : Int = TIME_OUT
        lateinit var url : String
        var https : Boolean = false
        var imageView : ImageView? = null
        var resDefaultId : Int = 0
        var cacheDisk : Boolean = false
        var resId : Int = 0
    }

}