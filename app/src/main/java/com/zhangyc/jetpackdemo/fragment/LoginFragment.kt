package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.mvp.LoginContact
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.edit_password
import kotlinx.android.synthetic.main.fragment_login.edit_username

class LoginFragment : BaseFragment<LoginContact.LoginPresenter>(), LoginContact.ILoginView{

    @InjectPresenter
    lateinit var mPresenter : LoginContact.LoginPresenter

    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id) {
            R.id.register->{
                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment)
            }
            R.id.btn_submit->{
                mPresenter.login()
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


    override fun getUserName() : String {
        return edit_username.text.toString().trim()
    }

    override fun getPassword() : String {
        return edit_password.text.toString().trim()
    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

}