package com.zhangyc.imageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import com.zhangyc.imageloader.java.DiskLruCache
import java.io.File

class RequestHelper : ImageCache {

    companion object {
        val instance: RequestHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RequestHelper() }
        val cacheDir = ImageLoader.instance.context?.cacheDir
        const val CACHE_DIR_SIZE = 10 * 1024 * 1024L
    }

    private var cacheMaps = object : LruCache<String, Bitmap>((Runtime.getRuntime().maxMemory() / 8).toInt()) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            return value?.byteCount ?: 1
        }
    }
    private var diskCaches : DiskLruCache? = null

    private fun getDiskLruCache() : DiskLruCache {
        if (diskCaches == null) diskCaches = DiskLruCache.open(cacheDir, 1, 1, CACHE_DIR_SIZE)
        return diskCaches!!
    }

    override fun put(url: String, bitmap: Bitmap, saveDisk: Boolean) {
        cacheMaps.put(url, bitmap)
        if (saveDisk) cacheDisk(url, bitmap)
    }

    override fun get(url: String): Bitmap? {
        Log.d("RequestHelper", "maxMemory : ${Runtime.getRuntime().maxMemory()}")
        if (cacheMaps[url] != null) return cacheMaps[url]
        return getDisk(url)
    }

    private fun cacheDisk (url : String, bitmap: Bitmap) {
        val edit = getDiskLruCache().edit(url)
        if (edit != null) {
            val newOutputStream = edit.newOutputStream(0)
            bitmap.compress(getImageType(url), 100, newOutputStream)
            edit.commit()
            getDiskLruCache().flush()
        }
    }

    private fun getImageType(url : String) : Bitmap.CompressFormat {
        val substring = url.substring(url.lastIndexOf(".") + 1)
        return when {
            "png".toUpperCase() == substring.toUpperCase() -> Bitmap.CompressFormat.PNG
            "webp".toUpperCase() == substring.toUpperCase() -> Bitmap.CompressFormat.WEBP
            else -> Bitmap.CompressFormat.JPEG
        }
    }

    private fun getDisk (url : String) : Bitmap? {
        val inputStream = getDiskLruCache().get(url)?.getInputStream(0) ?: return null
        return BitmapFactory.decodeStream(inputStream)
    }

    /*private fun cacheDisk(url: String, bitmap: Bitmap) {
        val cacheDir = ImageLoader.instance.context?.cacheDir
        if (cacheDir != null) {
            var outputStream: OutputStream? = null
            try {
                val file = File(cacheDir.path.plus(url.substring(url.lastIndexOf("/"))))
                outputStream = file.outputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            } catch (e: Exception) {
                Log.e(ImageLoader.tag, "exception : ${e.printStackTrace()}")
            } finally {
                outputStream?.close()
            }

        }
    }

    private fun getDisk(url: String): Bitmap? {
        val cacheDir = ImageLoader.instance.context?.cacheDir
        var bitmap: Bitmap? = null
        if (cacheDir != null) {
            try {
                val fileName = cacheDir.path.plus(url.substring(url.lastIndexOf("/")))
                val file = File(fileName)
                Log.d(ImageLoader.tag, "getDisk : ${file.path}, ${file.exists()}")
                if (file.exists()) {
                    val byteArrays = file.readBytes()
                    bitmap = BitmapFactory.decodeByteArray(byteArrays, 0, byteArrays.size)
                }
            } catch (e: Exception) {
                Log.e(ImageLoader.tag, "getDisk exception : ${e.printStackTrace()}")
            }
        }
        Log.d(ImageLoader.tag, "bitmap : $bitmap")
        return bitmap
    }*/

    fun getLocalBitmap(file: File, reqW: Int, reqH: Int): Bitmap? {
        return getLocalBitmap(file.path, reqW, reqH)
    }

    fun getLocalBitmap(filePath: String, reqW: Int, reqH: Int): Bitmap? {
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