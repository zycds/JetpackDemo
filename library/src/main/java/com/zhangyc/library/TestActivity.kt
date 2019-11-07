package com.zhangyc.library

import android.os.Bundle
import android.os.PersistableBundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


@Route(path = RouterConstants.ACTIVITY_URL_TEST)
class TestActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_test)
    }

}