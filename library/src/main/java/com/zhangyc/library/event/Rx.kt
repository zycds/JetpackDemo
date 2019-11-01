package com.zhangyc.library.event

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.function.BiFunction

class Rx {
    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.N)
    fun zip() {
        val create = Observable.create(ObservableOnSubscribe<Int> { })
        val create2 = Observable.create(ObservableOnSubscribe<String> { })

        Observable.zip(create, create2, object :BiFunction<Int, String , String>,
            io.reactivex.functions.BiFunction<Int, String, String> {
            override fun apply(p0: Int, p1: String): String {
                return p1 + p0.toString()
            }

        })

    }

}