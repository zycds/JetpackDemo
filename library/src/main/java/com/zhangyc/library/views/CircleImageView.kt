package com.zhangyc.library.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircleImageView : AppCompatImageView{

    private var paint: Paint = Paint()

    private var matrixRect : Matrix

    private var radius : Float = 0f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr)

    init {
        paint.isAntiAlias = true
        matrixRect = Matrix()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        radius = Math.min(measuredWidth, measuredHeight).div(2f)
    }

    override fun onDraw(canvas: Canvas?) {
        if (drawable == null) return
        if (drawable is BitmapDrawable){
            paint.shader = initBitmapShader(drawable as BitmapDrawable)
            canvas?.drawCircle(measuredWidth.div(2f), measuredHeight.div(2f), radius, paint)
            return
        }
        super.onDraw(canvas)
    }


    private fun initBitmapShader(bitmapDrawable: BitmapDrawable) : BitmapShader {
        val bitmapShader = BitmapShader(bitmapDrawable.bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val max = Math.max(measuredWidth.div(bitmapDrawable.bitmap.width), measuredHeight.div(bitmapDrawable.bitmap.height))
        matrixRect.setScale(max.toFloat(), max.toFloat())
        bitmapShader.getLocalMatrix(matrixRect)
        return bitmapShader
    }
}