package com.zhangyc.jetpackdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    var name : MutableLiveData<String>? = null


    var password : String? = null
        get() {
            if (field == null) return ""
            return field
        }
        set(value) {
            if (value == null) field = "haha"
            field = value
        }


}