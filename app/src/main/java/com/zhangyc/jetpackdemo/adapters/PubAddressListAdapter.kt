package com.zhangyc.jetpackdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.R
import com.zhangyc.library.base.BaseAdapter
import com.zhangyc.jetpackdemo.entities.Entities
import com.zhangyc.library.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_public_address.view.*

class PubAddressListAdapter : BaseAdapter<PubAddressListAdapter.ViewHolder, List<Entities.PubAddress>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewResId: Int = if (viewType == 1) R.layout.item_foot_view else R.layout.item_public_address
        return ViewHolder(
            LayoutInflater.from(App.instance.applicationContext).inflate(
                viewResId,
                parent,
                false
            )
        )
    }

    override fun itemCount(): Int {
        return if (mData == null) 0 else mData?.size!! + 1
    }

    override fun binderViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) return
        holder.itemView.item_pub_address.text = mData?.get(position)?.name
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mData != null && position == mData?.size) 1 else 2
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView)

}