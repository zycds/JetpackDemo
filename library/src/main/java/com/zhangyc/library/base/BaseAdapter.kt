package com.zhangyc.library.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zhangyc.jetpackdemo.utils.Lg

abstract class BaseAdapter<VH : BaseViewHolder, Data> : RecyclerView.Adapter<VH>() {

    var mData : MutableList<Data>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return itemCount()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        binderViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.itemClick(position, mData?.get(position))
        }
    }

    fun setData(data: MutableList<Data>){
        Lg.debug(tag = "baseAdapter", msg = "setData")
        mData = data
        notifyDataSetChanged()
    }

    abstract fun itemCount(): Int
    abstract fun binderViewHolder(holder: VH, position: Int)

    private var onItemClickListener : OnItemClickListener<Data>? = null
    fun setOnRecyclerOnItemClickListener(onItemClickListener: OnItemClickListener<Data>) {
        this.onItemClickListener = onItemClickListener
    }
    interface OnItemClickListener<Data> {
        fun  itemClick(position: Int, data : Data?)
    }
}
