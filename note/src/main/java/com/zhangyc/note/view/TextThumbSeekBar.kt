package com.zhangyc.note.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.SeekBar

class PageSeekBar: SeekBar{

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}