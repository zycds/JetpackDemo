package com.zhangyc.jetpackdemo.mvp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.base.IBasePresenter
import com.zhangyc.jetpackdemo.base.IBaseView
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.fragment.MainFragment
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.room.AppDataBase
import com.zhangyc.jetpackdemo.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface RegisterContact {

    interface IRegisterView : IBaseView {

        fun getCurrentFragment() : Fragment
        fun getUserName() : String?
        fun getPassword() : String?
        fun getRePassword() : String?

    }

    class RegisterPresenter : IBasePresenter {

        private val tag = RegisterPresenter::class.java.simpleName

        private var mDisposable : Disposable? = null

        private lateinit var iBaseView : IRegisterView

        override fun <V : IBaseView> attachView(v: V) {
            iBaseView = v as IRegisterView
        }

        override fun deAttachView() {
            mDisposable?.dispose()
            mDisposable = null
        }

        fun register() {
            if (check()) return
            mDisposable = HttpApi.instance.register(iBaseView.getUserName()!!, iBaseView.getPassword()!!, iBaseView.getRePassword()!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(tag,"it : $it")
                    val bundle = Bundle()
                    bundle.putString("username", iBaseView.getUserName())
                    bundle.putString("password", iBaseView.getPassword())
                    val user = Entities.User()
                    user.username = iBaseView.getUserName()
                    user.password = iBaseView.getPassword()
                    AppDataBase.getDBInstance().getUserDao().insert(user)
                    NavHostFragment.findNavController(iBaseView.getCurrentFragment()).navigate(R.id.action_registerFragment_to_mainFragment, bundle)
                }, {
                    Log.d(tag, "it : $it")
                    iBaseView.getActivityContext()?.let { it1 -> ToastUtil.showShortToast(it1, "register fail, $it") }
                })
        }

        private fun check() : Boolean {
            return TextUtils.isEmpty(iBaseView.getUserName()) || TextUtils.isEmpty(iBaseView.getPassword()) || TextUtils.isEmpty(iBaseView.getRePassword())
        }

    }

}