package com.zhangyc.jetpackdemo.mvp

import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.jetpackdemo.adapters.HistoryAdapter
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface HistoryContact {

    interface IHistoryView : IBaseView {
        fun getRecyclerView() : RecyclerView
    }

    class HistoryPresenter : IBasePresenter {

        private var iHistoryView : IHistoryView? = null

        private var mDisposable : Disposable? = null

        override fun <V : IBaseView> attachView(v: V) {
            iHistoryView = v as IHistoryView
        }

        override fun deAttachView() {
            if (mDisposable?.isDisposed!!)mDisposable?.dispose()
            mDisposable = null
        }

        fun getPublicAddressList(id : Int) {
            mDisposable = HttpApi.instance.getPubAddressHistoryLists(id, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.datas?.let { it1 -> (iHistoryView?.getRecyclerView()?.adapter as HistoryAdapter).setData(it1) }
                }, {
                    ToastUtil.showShortToast(iHistoryView?.getActivityContext()!!, "it : $it")
                })
        }

        override fun requestFinish(success: Boolean) {

        }
    }


}