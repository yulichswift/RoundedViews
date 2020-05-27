package com.yulichswift.roundedview.tool

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import kotlin.math.roundToInt

class RoundedDrawable(private val mIsStandard: Boolean) : GradientDrawable() {

    companion object {
        const val INT_ZERO = 0
        const val INT_DARKER = -1
        const val INT_GRAYER = -2
    }

    private var mSolidColors: ColorStateList? = null
    private var mFillColor: Int = 0

    fun setSolidColorsAndPressedColor(normal: Int, pressed: Int, selected: Int, disable: Int) {
        val lastPressed = getColor(normal, pressed)
        val lastSelected = getColor(normal, selected)
        val lastDisable = getColor(normal, disable)

        setSolidColors(createColorStateList(normal, lastPressed, lastSelected, lastDisable))
    }

    fun getColor(default: Int, color: Int) =
            when (color) {
                INT_ZERO -> default
                INT_DARKER -> darker((default))
                INT_GRAYER -> grayer((default))
                else -> color
            }


    fun setSolidColors(colors: ColorStateList) {
        mSolidColors = colors
        setColor(colors.defaultColor)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        if (mIsStandard) {
            val rect = RectF(getBounds())
            cornerRadius = when {
                rect.height() > rect.width() -> rect.width()
                else -> rect.width() / 2
            }
        }
    }

    override fun setColor(argb: Int) {
        mFillColor = argb
        super.setColor(argb)
    }

    override fun onStateChange(stateSet: IntArray): Boolean {
        if (mSolidColors != null) {
            val newColor = mSolidColors!!.getColorForState(stateSet, 0)
            if (mFillColor != newColor) {
                setColor(newColor)
                return true
            }
        }
        return false
    }

    override fun isStateful(): Boolean {
        return super.isStateful() || mSolidColors != null && mSolidColors!!.isStateful
    }

    private fun grayer(color: Int): Int {
        val blue = color and 0x000000FF
        val green = color and 0x0000FF00 shr 8
        val red = color and 0x00FF0000 shr 16
        val gray = (red * 0.299f + green * 0.587f + blue * 0.114f).roundToInt()
        return Color.argb(0xff, gray, gray, gray)
    }

    /**
     *
     * 降低明亮度10%
     *
     * @param color
     * @return
     */
    private fun darker(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] -= 0.1f
        return Color.HSVToColor(hsv)
    }

    private fun createColorStateList(normal: Int, pressed: Int, selected: Int, disable: Int): ColorStateList {
        val states = arrayOf(
                intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_selected, android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf())
        val colors = intArrayOf(pressed, selected, normal, disable)
        return ColorStateList(states, colors)
    }
}
