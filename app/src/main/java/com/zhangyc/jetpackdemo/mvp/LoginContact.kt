package com.zhangyc.jetpackdemo.mvp

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.utils.ToastUtil
import com.zhangyc.library.mvp.IBasePresenter
import com.zhangyc.library.mvp.IBaseView
import io.reactivex.disposables.Disposable

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
                .subscribe({
                    NavHostFragment.findNavController(iLoginView.getCurrentFragment()).navigate(R.id.action_loginFragment_to_mainFragment)
                }, {
                    ToastUtil.showShortToast(iLoginView.getActivityContext()!!, "it : ${it.localizedMessage}")
                })
        }

        override fun requestFinish(success: Boolean) {

        }

    }

}