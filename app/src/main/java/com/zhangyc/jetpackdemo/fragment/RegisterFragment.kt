package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.mvp.RegisterContact
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment<RegisterContact.RegisterPresenter>() {
    override fun onClick(p0: View?) {

    }

    override fun init() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_register
    }

    override fun initData() {
        btn_register.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_mainFragment)
        }
    }

    override fun refreshData() {
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return context
    }
}