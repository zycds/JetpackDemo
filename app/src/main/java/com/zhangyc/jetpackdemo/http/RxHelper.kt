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
                override fun apply(upstream: Observable<BaseData<T>>): ObservableSource<T> {
                    return upstream.flatMap(object : Function<BaseData<T>, ObservableSource<T>> {
                        override fun apply(t: BaseData<T>): Observable<T> {
                            if (t.errorCode == -1) return Observable.error(Throwable(t.errorMsg))
                            return createDataObservable(t.data)
                        }
                    })
                }
            }
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
