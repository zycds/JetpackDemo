package com.zhangyc.jetpackdemo.mvp

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.activity.MainActivity
import com.zhangyc.jetpackdemo.adapters.MainViewPagerAdapter
import com.zhangyc.jetpackdemo.adapters.PubAddressListAdapter
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.fragment.MainFragment
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.http.RxHelper
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.jetpackdemo.utils.ToastUtil
import com.zhangyc.library.event.RxTimer
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import io.reactivex.disposables.Disposable
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
        private var mStopTimer = false

        private var mSubscribe : Disposable? = null
        private var mSubscribe2 : Disposable? = null
        private var mTimerDisposable : Disposable? = null


        override fun <V : IBaseView> attachView(v: V) {
            iMainView = v as IMainFragmentView
        }

        override fun deAttachView() {
            mStopTimer = true
            Lg.debug(tag, "deAttachView....")
            if (mTimerDisposable?.isDisposed!!)mTimerDisposable?.dispose()
            if (mSubscribe?.isDisposed!!)mSubscribe?.dispose()
            if (mSubscribe2?.isDisposed!!) mSubscribe2?.dispose()
            mSubscribe = null
            mSubscribe2 = null
            mTimerDisposable = null
        }


        fun requestBanners(){
            Lg.debug(tag, "requestBanners...")
            mSubscribe = HttpApi.instance.getBanners()
                .subscribe({
                    val mutableList = it as MutableList<Entities.Banner>
                    (iMainView.getViewPager().adapter as MainViewPagerAdapter).setData(mutableList)
                    mTimerDisposable = RxTimer.instance.interval(
                        (iMainView as MainFragment).activity as MainActivity,
                        2,
                        TimeUnit.SECONDS,
                        Long.MAX_VALUE )
                        .compose(RxHelper.handlerResultIO())
                        .takeWhile {
                            Lg.debug(tag, "stop timer : $mStopTimer")
                            !mStopTimer
                        }
                        .subscribe {
                            Lg.debug(tag, "currentItem : ${iMainView.getViewPager().currentItem}")
                            iMainView.getViewPager().currentItem ++
                        }
                }, {
                    ToastUtil.showShortToast(App.instance.applicationContext, "it : $it")
                })

        }

        fun requestPublicAddressList() {
            iMainView.showLoadingDialog()
            mSubscribe2 = HttpApi.instance.getPubAddressLists()
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