package com.zhangyc.jetpackdemo.http

import com.zhangyc.jetpackdemo.entities.Entities
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username: String, @Field("password") password: String,
                 @Field("repassword") repassword: String): Observable<BaseData<Entities.User>>

}