package com.zhangyc.jetpackdemo.daggers

import com.zhangyc.jetpackdemo.utils.Lg
import dagger.Module
import dagger.Provides

@Module
class TestModule {
    @Provides
    fun provideTest() : TestModule{
        return TestModule()
    }

    fun testString() {
        Lg.debug("dagger", "testModule.")
    }
}