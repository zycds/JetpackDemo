package com.zhangyc.jetpackdemo.fragment

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.adapters.HistoryAdapter
import com.zhangyc.library.annotations.InjectPresenter
import com.zhangyc.library.base.BaseAdapter
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.jetpackdemo.mvp.HistoryContact
import com.zhangyc.library.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : BaseFragment<HistoryContact.HistoryPresenter>() , HistoryContact.IHistoryView {

    @InjectPresenter
    lateinit var mPresenter : HistoryContact.HistoryPresenter

    override fun init() {

    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_history
    }

    override fun initData() {
        refreshData()
    }

    override fun refreshData() {
        arguments?.getInt("id")?.let { mPresenter.getPublicAddressList(it) }
    }

    override fun unInit() {
    }

    override fun getActivityContext(): Context? {
        return context
    }

    override fun getRecyclerView(): RecyclerView {
        if(recyclerView_history.adapter == null) {
            recyclerView_history.layoutManager = LinearLayoutManager(getActivityContext())
            recyclerView_history.addItemDecoration(DividerItemDecoration(getActivityContext(), DividerItemDecoration.VERTICAL))
            val historyAdapter = HistoryAdapter()
            historyAdapter.setOnRecyclerOnItemClickListener(object : BaseAdapter.OnItemClickListener<List<Entities.PublicAHistory>>{
                override fun itemClick(position: Int, data: List<Entities.PublicAHistory>?) {
                    val bundle = Bundle()
                    bundle.putString("url", data?.get(position)?.link)
                    NavHostFragment.findNavController(this@HistoryFragment).navigate(R.id.action_historyFragment_to_webFragment, bundle)
                }
            })
            recyclerView_history.adapter = historyAdapter
        }
        return recyclerView_history
    }

}