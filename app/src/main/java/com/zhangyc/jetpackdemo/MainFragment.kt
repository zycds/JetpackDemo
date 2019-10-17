package com.zhangyc.jetpackdemo

import android.content.Context
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.http.HttpApi
import com.zhangyc.jetpackdemo.mvp.MainFragmentContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentContact.MainFragmentPresenter>() {

    private var mDisposable : Disposable? = null

    companion object {
        val TAG = MainFragment::class.java.simpleName
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun init() {

    }

    override fun initData() {
        text_main_container.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_splashActivity)
        }

        refreshData()
    }

    override fun refreshData() {
        mDisposable = HttpApi.instance.register("hahahaha123", "123123321321", "123123321321")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    Log.d(TAG,"it : $it")
            }, {
                Log.d(TAG,"it : $it")
            })
    }

    override fun unInit() {
        mDisposable?.dispose()
        mDisposable = null
    }

    override fun getActivityContext(): Context? {
        return context
    }


}