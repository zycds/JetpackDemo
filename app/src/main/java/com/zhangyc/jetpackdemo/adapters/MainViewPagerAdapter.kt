package com.zhangyc.jetpackdemo.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.entities.Entities

class MainViewPagerAdapter : PagerAdapter() {

    private var mBanners : MutableList<Entities.Banner>? = null
    fun setData(banners : MutableList<Entities.Banner>){
        mBanners = banners
        notifyDataSetChanged()
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return if (mBanners == null) 0 else Int.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(App.instance.applicationContext)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(App.instance.applicationContext).load(mBanners?.get(position % (mBanners?.size!!))?.imagePath).into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

}