package com.zhangyc.library.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.zhangyc.library.R

class ItemMenu : RelativeLayout {

    var coordinateX : Int = 0
    var coordinateY : Int = 0
    var coordinateDefaultX : Int = 0
    var coordinateDefaultY : Int = 0

    var next : ItemMenu? = null
    var prev : ItemMenu? = null

    constructor(context: Context) :this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.ItemMenu)
        coordinateX = attrs.getInt(R.styleable.ItemMenu_coordinateX, 0)
        coordinateY = attrs.getInt(R.styleable.ItemMenu_coordinateY, 0)
        coordinateDefaultX = attrs.getInt(R.styleable.ItemMenu_coordinateDefaultX, 0)
        coordinateDefaultY = attrs.getInt(R.styleable.ItemMenu_coordinateDefaultY, 0)
        Log.d(RotateMenu.TAG, "x : $coordinateX, y : $coordinateY")
        attrs.recycle()
    }

    //coordinate
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

}