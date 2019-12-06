package com.zhangyc.jetpackdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.R
import com.zhangyc.library.base.BaseAdapter
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.library.base.BaseViewHolder
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.item_history.view.*

@Module
class HistoryAdapter : BaseAdapter<HistoryAdapter.ViewHolder, Entities.PublicAHistory>() {

    @Provides
    fun providesHistoryAdapter(): HistoryAdapter {
        return HistoryAdapter()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(App.instance.applicationContext).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun itemCount(): Int {
        return if (mData == null) 0 else mData?.size!!
    }

    override fun binderViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_pub_address_history.text = mData?.get(position)?.title
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView)
}