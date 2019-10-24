package com.zhangyc.jetpackdemo.http

abstract class BaseData<T>(var data: T) {

    var errorCode = -1
    var errorMsg  = ""

}