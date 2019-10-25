package com.zhangyc.jetpackdemo.http

import com.zhangyc.jetpackdemo.entities.Entities
import io.reactivex.Observable
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username: String, @Field("password") password: String,
                 @Field("repassword") repassword: String): Observable<BaseData<Entities.User>>


    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username")username: String, @Field("password")password: String) : Observable<BaseData<Entities.User>>

    @GET("/wxarticle/chapters/json")
    fun getPubAddressLists() : Observable<BaseData<List<Entities.PubAddress>>>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getPubAddressHistoryLists(@Path("id") id : Int, @Path("page") page : Int) : Observable<BaseData<Entities.PublicAHistoryPage>>

    @GET("/banner/json")
    fun getBanners() : Observable<BaseData<List<Entities.Banner>>>
}