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

    fun getLocalBitmap(file : File, reqW : Int, reqH : Int) : Bitmap? {
        return getLocalBitmap(file.path, reqW, reqH)
    }

    fun getLocalBitmap(filePath : String, reqW : Int, reqH : Int) : Bitmap? {
        if (!File(filePath).exists()) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        val wRatio = if (reqW > 0) options.outWidth / reqW else 1
        val hRatio = if (reqW > 0) options.outHeight / reqH else 1
        val ratio = if (wRatio > hRatio) hRatio else wRatio
        options.inSampleSize = ratio
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }

}