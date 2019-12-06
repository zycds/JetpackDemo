package com.zhangyc.jetpackdemo.daggers

import com.zhangyc.jetpackdemo.fragment.MainFragment
import dagger.Component

@Component
interface MainFragmentComponent {
    fun inject(mainFragment: MainFragment)
}