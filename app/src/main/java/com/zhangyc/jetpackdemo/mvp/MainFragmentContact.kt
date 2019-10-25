package com.zhangyc.jetpackdemo.mvp

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.adapters.MainViewPagerAdapter
import com.zhangyc.jetpackdemo.adapters.PubAddressListAdapter
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface MainFragmentContact {

    interface IMainFragmentView : IBaseView {
        fun getCurrentFragment() : Fragment
        fun getViewPager() : ViewPager
        fun getRecyelerView() : RecyclerView
    }

    class MainFragmentPresenter : IBasePresenter {

        private lateinit var iMainView : IMainFragmentView

        private var mSubscribe : Disposable? = null
        private var mSubscribe2 : Disposable? = null

        override fun <V : IBaseView> attachView(v: V) {
            iMainView = v as IMainFragmentView
        }

        override fun deAttachView() {
            if (mSubscribe?.isDisposed!!)mSubscribe?.dispose()
            if (mSubscribe2?.isDisposed!!) mSubscribe2?.dispose()
            mSubscribe = null
            mSubscribe2 = null
        }


        fun requestBanners(){
            mSubscribe = HttpApi.instance.getBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (iMainView.getViewPager().adapter as MainViewPagerAdapter).setData(it as MutableList<Entities.Banner>)
                }, {
                    ToastUtil.showShortToast(App.instance.applicationContext, "it : $it")
                })

        }

        fun requestPublicAddressList() {
            mSubscribe2 = HttpApi.instance.getPubAddressLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (iMainView.getRecyelerView().adapter as PubAddressListAdapter).setData(it)
                }, {

                })
        }

    }

}