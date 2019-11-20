package com.zhangyc.imageloader

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RequestManager {

    companion object {
        val instance : RequestManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RequestManager() }
    }

    private var mExecutorService: ExecutorService = Executors.newCachedThreadPool()

    fun submit(request: Request) {
        mExecutorService.execute(request)
    }

    fun createHttpRequest(builder : ImageRequest.Builder) : ImageRequest {
        return ImageRequest(builder)
    }

}