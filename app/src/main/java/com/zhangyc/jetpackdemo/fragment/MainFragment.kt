package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import android.util.Log
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.mvp.MainFragmentContact
import com.zhangyc.jetpackdemo.utils.Lg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentContact.MainFragmentPresenter>() {


    @InjectPresenter
    lateinit var mPresenter: MainFragmentContact.MainFragmentPresenter


    override fun handlerClickListener(id: Int?) {
        super.handlerClickListener(id)
        when(id){
            R.id.text_main_container->{
                NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_loginFragment)
            }
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun init() {

        val register = arguments?.getBoolean("register")
        Lg.info(bTag,"register : $register")

    }

    override fun initData() {
        setOnClickListeners(text_main_container)
        refreshData()

    }

    override fun refreshData() {

    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return context
    }


}