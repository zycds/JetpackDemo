package com.zhangyc.jetpackdemo.mvp

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.activity.MainActivity
import com.zhangyc.jetpackdemo.adapters.MainViewPagerAdapter
import com.zhangyc.jetpackdemo.adapters.PubAddressListAdapter
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.event.RxTimer
import com.zhangyc.jetpackdemo.fragment.MainFragment
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.jetpackdemo.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

interface MainFragmentContact {

    companion object {
        val tag = MainFragmentContact::class.java.simpleName
    }

    interface IMainFragmentView : IBaseView {
        fun getCurrentFragment() : Fragment
        fun getViewPager() : ViewPager
        fun getRecyclerView() : RecyclerView
        fun getSwipeRefreshLayout() : SwipeRefreshLayout
    }

    class MainFragmentPresenter : IBasePresenter {

        private lateinit var iMainView : IMainFragmentView

        private var mSubscribe : Disposable? = null
        private var mSubscribe2 : Disposable? = null
        private var mTimerDisposable : Disposable? = null


        override fun <V : IBaseView> attachView(v: V) {
            iMainView = v as IMainFragmentView
        }

        override fun deAttachView() {
            Lg.debug(tag, "deAttachView....")
            if (mSubscribe?.isDisposed!!)mSubscribe?.dispose()
            if (mSubscribe2?.isDisposed!!) mSubscribe2?.dispose()
            if (mTimerDisposable?.isDisposed!!)mTimerDisposable?.dispose()
            mSubscribe = null
            mSubscribe2 = null
            mTimerDisposable = null
        }


        fun requestBanners(){
            mSubscribe = HttpApi.instance.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val mutableList = it as MutableList<Entities.Banner>
                    (iMainView.getViewPager().adapter as MainViewPagerAdapter).setData(mutableList)
                    mTimerDisposable = RxTimer.instance.interval(
                        (iMainView as MainFragment).activity as MainActivity,
                        2,
                        TimeUnit.SECONDS,
                        Long.MAX_VALUE
                    )
                        .subscribe {
                            iMainView.getViewPager().currentItem++
                        }
                }, {
                    ToastUtil.showShortToast(App.instance.applicationContext, "it : $it")
                })

        }

        fun requestPublicAddressList() {
            iMainView.showLoadingDialog()
            mSubscribe2 = HttpApi.instance.getPubAddressLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (iMainView.getRecyclerView().adapter as PubAddressListAdapter).setData(it)
                    requestFinish(true)
                }, {
                    ToastUtil.showShortToast(App.instance.applicationContext, "it : $it")
                    requestFinish(false)
                })
        }

        override fun requestFinish(success: Boolean) {
            iMainView.dismissLoadingDialog()
            iMainView.getSwipeRefreshLayout().isRefreshing = false
        }

    }

}