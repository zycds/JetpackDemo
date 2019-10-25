package com.zhangyc.jetpackdemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.R
import com.zhangyc.jetpackdemo.base.BaseAdapter
import com.zhangyc.jetpackdemo.base.BaseViewHolder
import com.zhangyc.jetpackdemo.entities.Entities
import kotlinx.android.synthetic.main.item_public_address.view.*

class PubAddressListAdapter : BaseAdapter<PubAddressListAdapter.ViewHolder, List<Entities.PubAddress>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(App.instance.applicationContext).inflate(
                R.layout.item_public_address,
                parent,
                false
            )
        )
    }

    override fun itemCount(): Int {
        return if (mData == null) 0 else mData?.size!!
    }

    override fun binderViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.item_pub_address.text = mData?.get(position)?.name
        holder.itemView.setOnClickListener {

        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder(itemView)

}