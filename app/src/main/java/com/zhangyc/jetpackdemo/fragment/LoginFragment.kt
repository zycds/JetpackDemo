package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.activity.SplashActivity
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.mvp.LoginContact
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.edit_password
import kotlinx.android.synthetic.main.fragment_login.edit_username
import kotlinx.android.synthetic.main.fragment_register.*

class LoginFragment : BaseFragment<LoginContact.LoginPresenter>(){

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.register->{
                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment)
            }
            R.id.btn_submit->{
                getUserName()
                getPassword()
                startActivity(Intent(getActivityContext(), SplashActivity::class.java))
            }
        }
    }

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_login
    }

    override fun initData() {
        btn_submit.setOnClickListener(this)
        register.setOnClickListener(this)
        edit_username.setText(arguments?.getString("username"))
        edit_password.setText(arguments?.getString("password"))
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return context
    }

    fun getUserName() : String {
        return edit_username.text.toString().trim()
    }

    fun getPassword() : String {
        return edit_password.text.toString().trim()
    }

}