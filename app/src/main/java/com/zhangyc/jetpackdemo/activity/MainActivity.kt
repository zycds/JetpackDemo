package com.zhangyc.jetpackdemo.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.fragment.LoginFragment
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.mvp.MainContact
import com.zhangyc.jetpackdemo.utils.Lg
import com.zhangyc.library.RouterConstants
import com.zhangyc.library.base.BaseActivity
import com.zhangyc.library.event.MsgEvent
import com.zhangyc.library.event.RxBus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

@Route(path = RouterConstants.ACTIVITY_URL_MAIN)
class MainActivity : BaseActivity<MainContact.MainPresenter>(), MainContact.MainView {

    @InjectPresenter
    lateinit var mPresenter : MainContact.MainPresenter

    @Autowired(name = "username")
    @JvmField
    var username : String? = null
    @Autowired(name = "password")
    @JvmField
    var password : String? = null


    override fun init() {
        setContentView(R.layout.activity_main)
        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("password", password)
        main_container.findNavController().navigate(R.id.loginFragment, bundle)
    }

    override fun initData() {

    }

    override fun refreshData() {
        RxBus.instance.post(MsgEvent(MsgEvent.REFRESH_DATA_FRAGMENT, main_container.findNavController().currentDestination?.label as String))
    }


    override fun unInit() {

    }

    override fun back(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (NavHostFragment.findNavController(fragment!!).currentDestination?.label == LoginFragment::class.java.simpleName) {
            Lg.debug(tag, "current fragment is LoginFragment.")
            finish()
            return true
        }
        return supportFragmentManager.findFragmentById(R.id.main_container)?.let { NavHostFragment.findNavController(it).navigateUp() }!!//JetPack 自己的Fragment栈管理
    }

    override fun getActivityContext(): Context? {
        return this
    }

    override fun getLayoutToolBar(): Toolbar? {
        return toolbar
    }

    override fun setToolBarTitle(): String {
        return "Main"
    }
}
