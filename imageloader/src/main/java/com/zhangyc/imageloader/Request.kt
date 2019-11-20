package com.zhangyc.imageloader

abstract class Request : Runnable {
    abstract fun buildRequest(builder : Builder) : Request
    interface Builder
}