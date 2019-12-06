package com.zhangyc.jetpackdemo.daggers

import com.zhangyc.jetpackdemo.activity.MainActivity
import dagger.Component

@Component(modules = [TestModule::class])
interface TestComponent {
    fun inject(activity: MainActivity)
}