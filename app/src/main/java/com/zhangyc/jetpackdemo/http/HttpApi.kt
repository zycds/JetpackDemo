package com.zhangyc.jetpackdemo.http

import com.zhangyc.jetpackdemo.entities.Entities
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpApi private constructor() {
    companion object {
        val instance: HttpApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpApi()
        }
    }
    private val api : Api
    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                    val newBuilder = request.newBuilder()
                    newBuilder.header("Content-Type", "application/json")
                    newBuilder.header("Accept", "application/json")
                    newBuilder.method(request.method, request.body)
                    return chain.proceed(request)
                }
            })
            .addInterceptor(httpLoggingInterceptor)
            .build()
        api = Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(Api::class.java)
    }

    fun register(username : String, password : String, repassword : String) : Observable<Entities.User> {
        return api.register(username, password, repassword).compose(RxHelper.handlerResult())
    }

}