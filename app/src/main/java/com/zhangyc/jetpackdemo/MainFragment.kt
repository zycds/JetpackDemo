package com.zhangyc.jetpackdemo

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.mvp.MainFragmentContact
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentContact.MainFragmentPresenter>() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun init() {

    }

    override fun initData() {
        text_main_container.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_splashActivity)
        }
    }

    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unInit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getActivityContext(): Context? {
        return context
    }


}