package com.zhangyc.jetpackdemo.http

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class RxHelper {

    companion object {

        fun <T> handlerResult() : ObservableTransformer<BaseData<T>, T> {

            return object :ObservableTransformer<BaseData<T>, T> {
                override fun apply(upstream: Observable<BaseData<T>>): Observable<T> {
                    return upstream.flatMap(object : Function<BaseData<T>, Observable<T>> {
                        override fun apply(t: BaseData<T>): Observable<T> {
                            if (t.errorCode == -1) return Observable.error(Throwable(t.errorMsg))
                            return createDataObservable(t.data)
                        }
                    })
                }
            }
        }

        fun <T> handlerResultIO() : ObservableTransformer<T, T>{
            return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }

        private fun <T> createDataObservable(data : T?) : Observable<T> {
            return Observable.create { emitter ->
                try {
                    if (data != null) {
                        emitter.onNext(data)
                        emitter.onComplete()
                    } else {
                        emitter.onError(Throwable("-1"))
                    }
                } catch (e : Exception) {
                    emitter.onError(e)
                }
            }
        }
    }

}
