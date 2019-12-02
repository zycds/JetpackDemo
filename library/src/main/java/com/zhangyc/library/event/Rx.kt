package com.zhangyc.library.event

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.zhangyc.jetpackdemo.utils.Lg
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Function3
import java.util.function.BiFunction

class Rx {

    companion object {
        private const val TAG = "rx"

        @SuppressLint("CheckResult")
        @RequiresApi(Build.VERSION_CODES.N)
        fun zip() {
            val create = Observable.create(ObservableOnSubscribe<Int> { emitter -> emitter.onNext(1) })
            val create2 = Observable.create(ObservableOnSubscribe<String> { emitter -> emitter.onNext("2") })
            val create3 = Observable.create(ObservableOnSubscribe<String> { emitter -> emitter.onNext("3") })

            Observable.zip(create, create2, create3,
                Function3<Int, String, String, String> { t1, t2, t3 -> t2.plus(t1).plus(t3) }).subscribe({
                Lg.debug(TAG, "it :  $it")
            }, {
                Lg.error(TAG, "exception : $it")
            })


            Observable.zip(create, create2, object : BiFunction<Int, String, String>,
                io.reactivex.functions.BiFunction<Int, String, String> {
                override fun apply(p0: Int, p1: String): String {
                    return p1 + p0.toString()
                }
            }).subscribe({
                Lg.debug(TAG, "it :  $it")
            }, {
                Lg.error(TAG, "exception : $it")
            })

        }
    }


}