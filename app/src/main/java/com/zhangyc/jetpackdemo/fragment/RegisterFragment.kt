package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.mvp.RegisterContact
import com.zhangyc.library.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment<RegisterContact.RegisterPresenter>(), RegisterContact.IRegisterView {

    @InjectPresenter
    lateinit var mPresenter : RegisterContact.RegisterPresenter

    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id){
            R.id.btn_register->{
                mPresenter.register()
            }
        }
    }

    override fun init() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_register
    }

    override fun initData() {
        setOnClickListeners(btn_register)
    }

    override fun refreshData() {
    }

    override fun unInit() {

    }

    override fun getActivityContext(): Context? {
        return context
    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getUserName(): String? {
        return edit_username.text?.toString()?.trim()
    }

    override fun getPassword(): String? {
        return edit_password.text?.toString()?.trim()
    }

    override fun getRePassword(): String? {
        return edit_repassword.text?.toString()?.trim()
    }


}