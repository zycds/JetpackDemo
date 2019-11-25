package com.zhangyc.imageloader

import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ImageRequest(builder: Builder) : Request(){

    companion object {
        const val TIME_OUT = 15 * 1000
    }

    private var mBuilder: Builder = builder

    override fun run() {
        if(loadLocal()) return
        request()
    }

    override fun buildRequest(builder : Request.Builder): Request {
        return ImageRequest(builder as Builder)
    }

    private fun loadLocal() : Boolean {
        when {
            mBuilder.resId !=0 -> {
                mBuilder.imageView?.setImageResource(mBuilder.resId)
                return true
            }
            mBuilder.file != null -> {
                RequestHelper.instance.getLocalBitmap(mBuilder.file!!, mBuilder.imageView?.width!!, mBuilder.imageView?.height!!)?.let {
                    mBuilder.imageView?.setImageBitmap(it) }
                return true
            }
            mBuilder.filePath != null -> {
                RequestHelper.instance.getLocalBitmap(mBuilder.filePath!!, mBuilder.imageView?.width!!, mBuilder.imageView?.height!!)?.let {
                    mBuilder.imageView?.setImageBitmap(it) }
                return true
            }
            RequestHelper.instance.get(mBuilder.url) != null -> {
                Log.d(ImageLoader.tag, "use cache...")
                Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageBitmap(RequestHelper.instance.get(mBuilder.url)) }
                return true
            }
        }
        return false
    }

    private fun request() {
        Log.d(ImageLoader.tag, "url : ${mBuilder.url}, ${mBuilder.httpReqMethod.method} , ${mBuilder.timeOut}")
        if(mBuilder.resDefaultId != 0) Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageResource(mBuilder.resDefaultId) }
        val openConnection = if (mBuilder.https) URL(mBuilder.url).openConnection() as HttpsURLConnection else URL(mBuilder.url).openConnection() as HttpURLConnection
        if (mBuilder.https)  {
            HTTPSTrustManager.allowAllSSL()
        }
        openConnection.connectTimeout = mBuilder.timeOut
        openConnection.requestMethod = mBuilder.httpReqMethod.method
        openConnection.readTimeout = mBuilder.timeOut
        openConnection.doInput = true
//        openConnection.doOutput = true
        openConnection.connect()
        Log.e(ImageLoader.tag, "responseCode : ${openConnection.responseCode}, ${openConnection.responseMessage}, ${openConnection.requestMethod} ")
        if (openConnection.responseCode == 200) {
            val rect = Rect(0, 0, (mBuilder.imageView?.width ?: 100), (mBuilder.imageView?.height ?: 100))
            val bmp = BitmapFactory.decodeStream(openConnection.inputStream, rect, BitmapFactory.Options())
            Log.d(ImageLoader.tag, "bitmap : $bmp")
            Handler(Looper.getMainLooper()).post { mBuilder.imageView?.setImageBitmap(bmp) }
            bmp?.let { RequestHelper.instance.put(mBuilder.url, it, mBuilder.cacheDisk) }
        }
        openConnection.disconnect()
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
        var file : File? = null
        var filePath : String? = null
    }

}