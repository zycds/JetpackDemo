package com.zhangyc.jetpackdemo.views

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText : AppCompatEditText, TextWatcher {

    private var hasFocused : Boolean? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, android.R.attr.editTextStyle)

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initEditText()
    }

    private fun initEditText() {
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], null, null, null)
        addTextChangedListener(this)
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        left?.setBounds(0, 0, 50, 50)
        right?.setBounds(0, 0, 50, 50)
        top?.setBounds(0, 0, 100, 100)
        bottom?.setBounds(0, 0, 100, 100)
        setCompoundDrawables(left, top, right, bottom)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        this.hasFocused = focused
        setImageVisible(focused && (text?.length!! > 0))
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    private fun setImageVisible(visibility : Boolean){

    }
}