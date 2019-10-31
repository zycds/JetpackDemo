package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.adapters.MainViewPagerAdapter
import com.zhangyc.jetpackdemo.adapters.PubAddressListAdapter
import com.zhangyc.jetpackdemo.annotations.InjectPresenter
import com.zhangyc.jetpackdemo.base.BaseAdapter
import com.zhangyc.jetpackdemo.base.BaseFragment
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.mvp.MainFragmentContact
import com.zhangyc.jetpackdemo.utils.Lg
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentContact.MainFragmentPresenter>(), MainFragmentContact.IMainFragmentView, SwipeRefreshLayout.OnRefreshListener {

    @InjectPresenter
    lateinit var mPresenter: MainFragmentContact.MainFragmentPresenter
    private var lastVisibilityItem : Int = 0

    override fun getLayoutResId(): Int {
        return R.layout.fragment_main
    }

    override fun init() {

    }

    override fun initData() {
        swipeRefreshLayout.setOnRefreshListener(this)
        refreshData()
    }

    override fun refreshData() {
        mPresenter.requestBanners()
        mPresenter.requestPublicAddressList()
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return context
    }


    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getViewPager(): ViewPager {
        if (viewPager_main.adapter == null) viewPager_main.adapter = MainViewPagerAdapter()
        return viewPager_main
    }

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout {
        return swipeRefreshLayout
    }

    override fun getRecyclerView(): RecyclerView {
        if (recyclerView_main.adapter == null) {
            recyclerView_main.layoutManager = LinearLayoutManager(getActivityContext())
            val dividerItemDecoration = DividerItemDecoration(getActivityContext(), DividerItemDecoration.VERTICAL)
            recyclerView_main.addItemDecoration(dividerItemDecoration)
            val pubAddressListAdapter = PubAddressListAdapter()
            pubAddressListAdapter.setOnRecyclerOnItemClickListener(object :
                BaseAdapter.OnItemClickListener<List<Entities.PubAddress>> {
                override fun itemClick(position: Int, data: List<Entities.PubAddress>?) {
                    val bundle = Bundle()
                    data?.get(position)?.id?.let { bundle.putInt("id", it) }
                    NavHostFragment.findNavController(getCurrentFragment())
                        .navigate(R.id.action_mainFragment_to_historyFragment, bundle)
                }
            })

            recyclerView_main.adapter = pubAddressListAdapter
            recyclerView_main.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when(newState) {
                        RecyclerView.SCROLL_STATE_IDLE->{
                            if (lastVisibilityItem + 1 == pubAddressListAdapter.itemCount){
                                Lg.debug(bTag, "loading more...")
                            }
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    lastVisibilityItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                }
            })
        }
        return recyclerView_main
    }

    override fun onRefresh() {
        Lg.debug(bTag, "onRefresh.")
        mPresenter.requestPublicAddressList()
    }


    override fun showLoadingDialog() {
        super.showLoadingDialog()
        progressbar_loading.visibility = View.VISIBLE
    }

    override fun dismissLoadingDialog() {
        super.dismissLoadingDialog()
        progressbar_loading.visibility = View.GONE
    }

}