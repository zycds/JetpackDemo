package com.zhangyc.jetpackdemo.event

import androidx.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.ConcurrentHashMap


class RxBus {

    private var mStickyEvents : ConcurrentHashMap<Class<*>, Any>
    companion object {

        private lateinit var mBus : Subject<Any>

        private lateinit var mBusSticky : PublishSubject<Any>

        val instance : RxBus by lazy ( mode = LazyThreadSafetyMode.SYNCHRONIZED ){
            RxBus()
        }
    }

    init {
        mBus = PublishSubject.create()
        mStickyEvents = ConcurrentHashMap()
    }

    fun post (event : Any) {
        mBus.onNext(event)
    }

    fun <T> toObservable(owner : LifecycleOwner,  eventType : Class<T>) : Observable<T> {
        val provider  = AndroidLifecycle.createLifecycleProvider(owner)
        return mBus.ofType(eventType).compose(provider.bindToLifecycle())
    }

    fun postSticky(event : Any) {
        synchronized(mStickyEvents){
            mStickyEvents.put(event.javaClass, event)
        }
        post(event)
    }

    fun <T> toObservableSticky (owner: LifecycleOwner, eventType : Class<T>) : Observable<T> {
        synchronized(mStickyEvents) {
            val provider = AndroidLifecycle.createLifecycleProvider(owner)
            val observable = mBus.ofType(eventType).compose(provider.bindToLifecycle())
            val eventLast = mStickyEvents[eventType]
            if (eventLast != null){
                return observable.mergeWith(Observable.create {
                    eventType.cast(eventLast)?.let { it1 -> it.onNext(it1) }
                })
            }
            return observable
        }
    }

}