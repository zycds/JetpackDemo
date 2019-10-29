package com.zhangyc.jetpackdemo.event

class MsgEvent(var code: Int, var msg: String) {

    companion object {
        const val REFRESH_DATA_FRAGMENT = 101
    }

}
