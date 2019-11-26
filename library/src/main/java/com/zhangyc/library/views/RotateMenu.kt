package com.zhangyc.library.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout
import java.util.*

class RotateMenu : RelativeLayout {

    companion object {
        val TAG = RotateMenu::class.java.simpleName
        const val MOVE_STEP = 300f
    }

    private var downX: Float = 0f
    private var downY: Float = 0f
    private var downTime: Long = 0
    private val clickSpaceTime = 500
    private var per = 0f

    private var coordinatorsX: IntArray? = null
    private var coordinatorsY: IntArray? = null
    private var provX: Int = 0
    private var provY: Int = 0

    private var currentArea = DOWNAREA.DEFAULT
    private var currentOrientation = ORIENTATION.DEFAULT

    private var isSingleClick = true
    private var isLongTouch = true
    private var isDoubleClick = false


    private enum class DOWNAREA {
        DEFAULT, LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
    }

    private enum class ORIENTATION {
        DEFAULT, ANTICLOCKWISE, CLOCKWISE
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        Log.d(TAG, "constructor childCount : $childCount")
    }

    //coordinate
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        provX = measuredWidth / 2
        provY = measuredHeight / 2
        Log.d(TAG, "provX : $provX, provY : $provY")

        if (coordinatorsX == null) {
            Log.d(TAG, "childCount : $childCount")
            coordinatorsX = IntArray(childCount)
            coordinatorsY = IntArray(childCount)
            for (index in 0 until childCount) {
                Log.d(TAG, "childCount : $childCount, index : $index")
                val itemMenu = getChildAt(index) as ItemMenu
                coordinatorsX?.set(index, itemMenu.coordinateX)
                coordinatorsY?.set(index, itemMenu.coordinateY)
                if (childCount > 0 && itemMenu.next == null) {
                    if (index == 0) itemMenu.prev = getChildAt(childCount - 1) as ItemMenu
                    if (index < childCount - 1) itemMenu.next = getChildAt(index + 1) as ItemMenu
                    if (index > 0) itemMenu.prev = getChildAt(index - 1) as ItemMenu
                    if (childCount - 1 == index) itemMenu.next = getChildAt(0) as ItemMenu
                }
            }
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        super.onLayout(changed, l, t, r, b)
        if (childCount > 0) {
            for (index in 0 until childCount) {
                val itemMenu = getChildAt(index) as ItemMenu
                var x = itemMenu.coordinateX
                var y = itemMenu.coordinateY
                if (itemMenu.next != null) {
                    val minusX = itemMenu.next?.coordinateX?.minus(itemMenu.coordinateX)!!
                    val minusY = itemMenu.next?.coordinateY?.minus(itemMenu.coordinateY)!!
                    x = minusX.times(per).toInt().plus(itemMenu.coordinateDefaultX)
                    y = minusY.times(per).toInt().plus(itemMenu.coordinateDefaultY)
                }
                Log.d(TAG, "onLayout coordinateX :${itemMenu.coordinateX}, coordinateY : ${itemMenu.coordinateY}")
                itemMenu.layout(
                    x,
                    y,
                    x + itemMenu.measuredWidth,
                    y + itemMenu.measuredHeight
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTime = System.currentTimeMillis()
                downX = event.x
                downY = event.y
                currentArea = getCurrentArea(downX, downY)
            }
            MotionEvent.ACTION_MOVE -> {
                isSingleClick(event.x, event.y)
                val currentArea1 = getCurrentArea(event.x, event.y)
                if (currentArea1 != currentArea) {
//                    downX = event.x
//                    downY = event.y
                    currentArea = currentArea1
                }
                per = (Math.abs(event.y - downY) / MOVE_STEP + Math.abs(event.x - downX) / MOVE_STEP) / 2
                val orientation = getOrientation(event.x)
                if (currentOrientation != ORIENTATION.DEFAULT && currentOrientation != orientation) {
                    Log.d(TAG, "orientation : $orientation, currentOrientation : $currentOrientation")
                }
                currentOrientation = orientation
                if (per >= 1f) {
                    setCoordinateDefault()
                    per = 0f
                }
                requestLayout()
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "isSingleClick : $isSingleClick")
                if (isSingleClick) return super.onTouchEvent(event)
            }
        }
        return true
    }

    private fun getCurrentArea(x: Float, y: Float): DOWNAREA {
        var area: DOWNAREA = DOWNAREA.DEFAULT
        if (x > provX && y > provY) {
            area = DOWNAREA.RIGHT_BOTTOM
        } else if (x > provX && y <= provY) {
            area = DOWNAREA.RIGHT_TOP
        } else if (x <= provX && y > provY) {
            area = DOWNAREA.LEFT_BOTTOM
        } else if (x > provX && y <= provY) {
            area = DOWNAREA.LEFT_TOP
        }
        return area
    }

    private fun getOrientation(x: Float): ORIENTATION {
        var orientation = ORIENTATION.DEFAULT
        when (currentArea) {
            DOWNAREA.LEFT_TOP -> {
                orientation = if (x > downX) ORIENTATION.CLOCKWISE else ORIENTATION.ANTICLOCKWISE
            }
            DOWNAREA.LEFT_BOTTOM -> {
                orientation = if (x > downX) ORIENTATION.ANTICLOCKWISE else ORIENTATION.CLOCKWISE
            }
            DOWNAREA.RIGHT_TOP -> {
                orientation = if (x > downX) ORIENTATION.CLOCKWISE else ORIENTATION.ANTICLOCKWISE
            }
            DOWNAREA.RIGHT_BOTTOM -> {
                orientation = if (x > downX) ORIENTATION.ANTICLOCKWISE else ORIENTATION.CLOCKWISE
            }
            DOWNAREA.DEFAULT -> {
                orientation = ORIENTATION.DEFAULT
            }
        }
        return orientation
    }

    private fun isSingleClick(x: Float, y: Float): Boolean {
        if (isSingleClick) {
            if ((Math.abs(x - downX) > 30 || Math.abs(y - downY) > 30) && (System.currentTimeMillis() - downTime < clickSpaceTime)) isSingleClick =
                false
        }
        return isSingleClick
    }

    private fun setCoordinateDefault() {
        val tempX = (getChildAt(0) as ItemMenu).coordinateDefaultX
        val tempY = (getChildAt(0) as ItemMenu).coordinateDefaultY
        for (index in 0 until childCount) {
            val itemMenu = getChildAt(index) as ItemMenu
            if (index == childCount - 1) {
                itemMenu.coordinateDefaultX = tempX
                itemMenu.coordinateDefaultY = tempY
                itemMenu.coordinateX = tempX
                itemMenu.coordinateY = tempY
            } else {
                itemMenu.coordinateDefaultX = itemMenu.next?.coordinateDefaultX ?: itemMenu.coordinateDefaultX
                itemMenu.coordinateDefaultY = itemMenu.next?.coordinateDefaultY ?: itemMenu.coordinateDefaultY
                itemMenu.coordinateX = itemMenu.next?.coordinateX ?: itemMenu.coordinateX
                itemMenu.coordinateY = itemMenu.next?.coordinateY ?: itemMenu.coordinateY
            }
        }
    }

}
