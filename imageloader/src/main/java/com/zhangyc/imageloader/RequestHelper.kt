package com.zhangyc.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import java.io.File
import java.io.OutputStream

class RequestHelper : ImageCache{

    companion object {
        val instance : RequestHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RequestHelper() }
    }

    private var cacheMaps = LruCache<String, Bitmap>((Runtime.getRuntime().maxMemory() / 8).toInt())

    override fun put(url: String, bitmap: Bitmap) {
        cacheMaps.put(url, bitmap)
    }

    override fun get(url: String): Bitmap? {
        return cacheMaps[url]
    }

    fun cacheDisk(url: String, bitmap: Bitmap) {
        val cacheDir = ImageLoader.instance.context?.cacheDir
        if(cacheDir != null) {
            var outputStream : OutputStream? = null
            try {
                val file = File(cacheDir.path.plus(url.substring(url.lastIndexOf("/"))))
                outputStream = file.outputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            } catch (e : Exception) {
                Log.e(ImageLoader.tag, "exception : ${e.printStackTrace()}")
            } finally {
                outputStream?.close()
            }

        }
    }

    fun getDisk(url : String) : Bitmap?{
        val cacheDir = ImageLoader.instance.context?.cacheDir
        var bitmap : Bitmap? = null
        if(cacheDir != null) {
            try {
                val fileName = cacheDir.path.plus(url.substring(url.lastIndexOf("/")))
                val file = File(fileName)
                Log.d(ImageLoader.tag, "getDisk : ${file.path}, ${file.exists()}")
                if (file.exists()) {
                    val byteArrays = file.readBytes()
                    bitmap =  BitmapFactory.decodeByteArray(byteArrays, 0 , byteArrays.size)
                }
            } catch (e : Exception) {
                Log.e(ImageLoader.tag, "getDisk exception : ${e.printStackTrace()}")
            }
        }
        Log.d(ImageLoader.tag, "bitmap : $bitmap")
        return bitmap
    }


}