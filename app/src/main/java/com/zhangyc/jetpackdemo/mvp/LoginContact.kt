package com.zhangyc.jetpackdemo.mvp

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

interface LoginContact {

    interface ILoginView : IBaseView {
        fun getUserName(): String
        fun getPassword(): String
        fun getCurrentFragment() : Fragment
    }

    class LoginPresenter : IBasePresenter {

        private lateinit var iLoginView: ILoginView
        private var mSubscribe: Disposable? = null

        override fun <V : IBaseView> attachView(v: V) {
            iLoginView = v as ILoginView
        }

        override fun deAttachView() {
            mSubscribe?.dispose()
            mSubscribe = null
        }

        fun login() {
            mSubscribe = HttpApi.instance.login(iLoginView.getUserName(), iLoginView.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    NavHostFragment.findNavController(iLoginView.getCurrentFragment()).navigate(R.id.action_loginFragment_to_mainFragment)
                }, {
                    ToastUtil.showShortToast(iLoginView.getActivityContext()!!, "it : ${it.localizedMessage}")
                })
        }

    }

}