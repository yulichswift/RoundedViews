package com.yulichswift.roundedview.widget

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity

import com.yulichswift.roundedview.R
import com.yulichswift.roundedview.tool.RoundedDrawable

class RoundedTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {

        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val typed = context.obtainStyledAttributes(attrs, R.styleable.RoundedTextView)

            var solidColor = typed.getColorStateList(R.styleable.RoundedTextView_btn_solid_color)
            val pressedColor = typed.getColor(R.styleable.RoundedTextView_btn_pressed_color, -1)
            val cornerRadius = typed.getLayoutDimension(R.styleable.RoundedTextView_btn_corner_radius, 0)

            val strokeColor = typed.getColor(R.styleable.RoundedTextView_btn_stroke_color, 0x0)
            val strokeWidth = typed.getDimensionPixelSize(R.styleable.RoundedTextView_btn_stroke_width, 0)
            val strokeDashWidth = typed.getDimensionPixelSize(R.styleable.RoundedTextView_btn_stroke_dash_width, 0)
            val strokeDashGap = typed.getDimensionPixelSize(R.styleable.RoundedTextView_btn_stroke_dash_gap, 0)

            typed.recycle()

            isSingleLine = true
            gravity = Gravity.CENTER

            val drawable = RoundedDrawable(cornerRadius == -1)
            drawable.cornerRadius = when (cornerRadius) {
                -1 -> 0F
                else -> cornerRadius.toFloat()
            }

            drawable.setStroke(strokeWidth, strokeColor, strokeDashWidth.toFloat(), strokeDashGap.toFloat())

            if (solidColor == null) {
                solidColor = ColorStateList.valueOf(0)
            }
            if (solidColor.isStateful) {
                drawable.setSolidColors(solidColor)
            } else {
                when (pressedColor) {
                    -1 -> drawable.setSolidColorsAndPressedDarker(solidColor.defaultColor)
                    -2 -> drawable.setSolidColorsAndPressedGrayer(solidColor.defaultColor)
                    else -> drawable.setSolidColorsAndPressedColor(solidColor.defaultColor, pressedColor)
                }
            }

            background = drawable
        }
    }
}
