package com.zhangyc.library.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle2.LifecycleProvider
import com.zhangyc.jetpackdemo.utils.Lg
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxTimer {

    companion object {
        val tag = RxTimer::class.java.simpleName
        val instance: RxTimer by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RxTimer()
        }
    }

    fun interval(owner : LifecycleOwner, period : Long, timeUnit : TimeUnit, stopPredicate : Long) : Observable<Long> {
        return Observable.interval(0, period, timeUnit)
            .takeUntil { t ->
                Lg.error(tag, "t $t")
                stopPredicate == t
            }.compose(getProvider(owner)?.bindToLifecycle())
    }

    fun <T> timerDelay (owner : LifecycleOwner, observable: Observable<T>, delay : Long, timeUnit: TimeUnit) : Observable<T> {
        return observable.delay(delay, timeUnit).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).compose(getProvider(owner)?.bindToLifecycle())
    }

    private fun  getProvider(owner : LifecycleOwner) : LifecycleProvider<Lifecycle.Event>? {
        return AndroidLifecycle.createLifecycleProvider(owner)
    }
}