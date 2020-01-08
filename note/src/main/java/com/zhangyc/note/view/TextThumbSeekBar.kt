package com.zhangyc.note.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import com.zhangyc.note.R

class TextThumbSeekBar: SeekBar{

    private lateinit var mPaint : TextPaint
    private var mProgressText = StringBuffer()
    private var mProgressText2 : String? = null
    private lateinit var mRect: Rect
    private var mDefaultThumbResId = R.drawable.img_thumb
    private var mProgressDrawableResId = R.drawable.progress
    private var mTextSize = 24f

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    private fun init() {
        thumb = resources.getDrawable(mDefaultThumbResId)
        progressDrawable = resources.getDrawable(mProgressDrawableResId)

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
        if (mProgressText2 != null) {
            mProgressText.append(mProgressText2)
        } else {
            mProgressText.append(progress)
            mProgressText.append("/")
            mProgressText.append(max)
        }
        val processText = mProgressText.toString()
        mPaint.getTextBounds(processText, 0 , processText.length, mRect)

        val thumbX : Float = 1.0f * width * progress / max + thumb.minimumWidth * (0.5f - 1.0f * progress / max)
        val thumbY = thumb.minimumHeight / 2f + resources.displayMetrics.density * 3 + 0.5f
        canvas?.drawText(processText, thumbX, thumbY, mPaint)
    }

    fun setTextProgressSize(size: Float) {
        mTextSize = size
    }

    fun setProgressDrawable(resId : Int) {
        mProgressDrawableResId = resId
        progressDrawable = resources.getDrawable(resId)
    }

    fun setProgressText(progressText : String) {
        mProgressText2 = progressText
    }

}