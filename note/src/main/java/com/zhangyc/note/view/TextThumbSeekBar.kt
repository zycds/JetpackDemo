package com.zhangyc.note.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import com.zhangyc.note.R

class TextThumbSeekBar: SeekBar{

    private lateinit var mPaint : TextPaint
    private var mProgressText = StringBuffer()
    private lateinit var mRect: Rect
    private val mDefaultThumbResId = R.drawable.img_thumb
    private val tag = TextThumbSeekBar::class.java.simpleName
    private var mTextSize = 24f

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init() {
        thumb = resources.getDrawable(mDefaultThumbResId)

        mRect = Rect()
        mPaint = TextPaint()
        mPaint.color = Color.BLACK
        mPaint.textSize = 24f
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.typeface = Typeface.DEFAULT_BOLD
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mProgressText.setLength(0)
        mProgressText.append(progress)
        mProgressText.append("/")
        mProgressText.append(max)
        val processText = mProgressText.toString()
        Log.d(tag,"processText:  $processText, paddingLeft : $paddingLeft, thumbOffset : $thumbOffset,  ${thumb.minimumWidth}")

        mPaint.getTextBounds(processText, 0 , processText.length, mRect)

        val thumbX : Float = ((width -  thumbOffset) * progress / max  - thumb.minimumWidth / 2 + (mRect.right - mRect.left) / 2).toFloat()
        val thumbY = thumb.minimumHeight / 2f

        canvas?.drawText(processText, thumbX, thumbY, mPaint)
    }

    fun setTextProgressSize(size: Float) {
        mTextSize = size
    }
}