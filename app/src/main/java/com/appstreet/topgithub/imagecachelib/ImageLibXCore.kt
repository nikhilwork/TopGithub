package com.appstreet.topgithub.imagelib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import com.appstreet.topgithub.imagecachelib.AppExecutors
import com.jakewharton.disklrucache.DiskLruCache
import java.io.*
import java.lang.ref.WeakReference
import java.net.URL
import java.util.concurrent.Semaphore
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ImageLibXCore constructor(private val context: Context) {

    private lateinit var memoryCache: LruCache<String, Bitmap>
    private val DISK_CACHE_SIZE = 1024 * 1024 * 10 // 10MB
    val IO_BUFFER_SIZE = 8 * 1024
    private val DISK_CACHE_SUBDIR = "thumbnails"
    private var diskLruCache: DiskLruCache? = null
    private val lock = ReentrantLock()
    private val diskCacheLockCondition: Condition = lock.newCondition()
    private var diskCacheStarting = true
    private val semaphore = Semaphore(1)

    init {
        // Initializing LRU cache
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, value: Bitmap): Int {
                return value.byteCount / 1024
            }
        }

        //Initializing DiskLRU cache
        val cacheDir = getDiskCacheDir(DISK_CACHE_SUBDIR)
        diskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE.toLong())

    }

    fun loadBitmap(urlPath: String, imageView: ImageView, placeholderRes: Int) {
        //val key = urlPath.substringAfterLast("/").toLowerCase().substringBefore(".")
        val key = urlPath.substringAfterLast("/").toLowerCase()
        var bitmap: Bitmap?
        bitmap = getBitmapFromMemCache(key)
        if (bitmap == null) {
            bitmap = getBitmapFromDiskCache(key)
            if (bitmap == null) {
                bitmapWorkerTask(urlPath, imageView, placeholderRes)
            } else {
                addBitmapToMemoryCache(key, bitmap)
                imageView.setImageBitmap(bitmap)
            }
        } else {
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun getDiskCacheDir(uniqueName: String): File {
        val path = context.cacheDir.path
        return File(path + File.separator + uniqueName)
    }

    private fun getBitmapFromMemCache(key: String): Bitmap? =
        lock.withLock {
            return memoryCache.get(key)
        }


    private fun getBitmapFromDiskCache(key: String): Bitmap? =
        lock.withLock {
            val snapshot = diskLruCache?.get(key)
            val inputStream = snapshot?.getInputStream(0)
            return BitmapFactory.decodeStream(inputStream)
        }

    private fun bitmapWorkerTask(urlPath: String, imageView: ImageView, placeholderRes: Int) {

        val weakReference = WeakReference<ImageView>(imageView)
        weakReference.get()?.setImageResource(placeholderRes)
        AppExecutors.getInstance().networkIO().execute {
            var stream: InputStream? = null
            try {
                stream = URL(urlPath).openConnection().getInputStream()
                if (stream != null) {

                    val options = BitmapFactory.Options().apply {
                        inJustDecodeBounds = true
                        BitmapFactory.decodeStream(stream, null, this)
                        inSampleSize = calculateInSampleSize(this, 80, 80)
                        inJustDecodeBounds = false
                    }
                    stream.close()
                    stream = URL(urlPath).openConnection().getInputStream()
                    val bitmap = BitmapFactory.decodeStream(stream, null, options)

                    if (bitmap != null) {
                        val key = urlPath.substringAfterLast("/").toLowerCase()
                        addBitmapToCache(key, bitmap)

                        AppExecutors.getInstance().mainThread().execute {
                            weakReference.get()?.setImageBitmap(bitmap)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                stream?.close()
            }
        }
    }

    private fun addBitmapToCache(key: String, bitmap: Bitmap) {
        // Add to memory cache as before
        if (getBitmapFromMemCache(key) == null) {
            addBitmapToMemoryCache(key, bitmap)
        }
        addBitmapToDiskCache(key, bitmap)
    }

    private fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        memoryCache.put(key, bitmap)
    }

    private fun addBitmapToDiskCache(key: String, bitmap: Bitmap) {
        synchronized(lock) {
            diskLruCache?.apply {
                val editor = edit(key)
                var out: OutputStream? = null
                try {
                    out = BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out)
                    diskLruCache?.flush()
                    editor?.commit()
                } finally {
                    out?.close()
                    //editor?.abort()
                }
            }
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}