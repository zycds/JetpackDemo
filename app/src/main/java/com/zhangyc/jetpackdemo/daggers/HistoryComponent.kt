package com.zhangyc.jetpackdemo.daggers

import com.zhangyc.jetpackdemo.adapters.HistoryAdapter
import com.zhangyc.jetpackdemo.fragment.HistoryFragment
import dagger.Component

@Component(modules = [HistoryAdapter::class])
interface HistoryComponent {
    fun inject(historyFragment: HistoryFragment)
}