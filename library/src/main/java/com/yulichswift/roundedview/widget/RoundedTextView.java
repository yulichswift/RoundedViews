package com.yulichswift.roundedview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.yulichswift.roundedview.R;
import com.yulichswift.roundedview.tool.RoundedDrawable;

public class RoundedTextView extends AppCompatTextView {
    public RoundedTextView(Context context) {
        super(context);

        init(context, null);
    }

    public RoundedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public RoundedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.RoundedTextView);

            ColorStateList solidColor = typed.getColorStateList(R.styleable.RoundedTextView_btn_solid_color);
            int pressedColor = typed.getColor(R.styleable.RoundedTextView_btn_pressed_color, -1);
            int cornerRadius = typed.getLayoutDimension(R.styleable.RoundedTextView_btn_corner_radius, 0);

            int strokeColor = typed.getColor(R.styleable.RoundedTextView_btn_stroke_color, 0x0);
            int strokeWidth = typed.getDimensionPixelSize(R.styleable.RoundedTextView_btn_stroke_width, 0);
            int strokeDashWidth = typed.getDimensionPixelSize(R.styleable.RoundedTextView_btn_stroke_dash_width, 0);
            int strokeDashGap = typed.getDimensionPixelSize(R.styleable.RoundedTextView_btn_stroke_dash_gap, 0);

            typed.recycle();

            setSingleLine(true);
            setGravity(Gravity.CENTER);

            RoundedDrawable drawable = new RoundedDrawable(cornerRadius == -1);
            drawable.setCornerRadius(cornerRadius == -1 ? 0 : cornerRadius);
            drawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap);

            if (solidColor == null) {
                solidColor = ColorStateList.valueOf(0);
            }
            if (solidColor.isStateful()) {
                drawable.setSolidColors(solidColor);
            } else {
                if (pressedColor == -1) {
                    drawable.setSolidColorsAndPressedDarker(solidColor.getDefaultColor());
                } else if (pressedColor == -2) {
                    drawable.setSolidColorsAndPressedGrayer(solidColor.getDefaultColor());
                } else {
                    drawable.setSolidColorsAndPressedColor(solidColor.getDefaultColor(), pressedColor);
                }
            }

            setBackground(drawable);
        }
    }
}
