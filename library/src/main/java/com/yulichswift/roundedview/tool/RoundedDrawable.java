package com.yulichswift.roundedview.tool;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;

public class RoundedDrawable extends GradientDrawable {
    private boolean mIsStandard;

    private ColorStateList mSolidColors;
    private int mFillColor;

    public RoundedDrawable(boolean isStandard) {
        mIsStandard = isStandard;
    }

    public void setSolidColorsAndPressedGrayer(int normal) {
        setSolidColors(createColorStateList(normal, grayer(normal)));
    }

    public void setSolidColorsAndPressedDarker(int normal) {
        setSolidColors(createColorStateList(normal, darker(normal)));
    }

    public void setSolidColorsAndPressedColor(int normal, int pressed) {
        setSolidColors(createColorStateList(normal, pressed));
    }

    public void setSolidColors(ColorStateList colors) {
        mSolidColors = colors;
        setColor(colors.getDefaultColor());
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (mIsStandard) {
            RectF rect = new RectF(getBounds());
            setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
        }
    }

    @Override
    public void setColor(int argb) {
        mFillColor = argb;
        super.setColor(argb);
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        if (mSolidColors != null) {
            final int newColor = mSolidColors.getColorForState(stateSet, 0);
            if (mFillColor != newColor) {
                setColor(newColor);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isStateful() {
        return super.isStateful() || (mSolidColors != null && mSolidColors.isStateful());
    }

    private int grayer(int color) {
        int blue = (color & 0x000000FF);
        int green = (color & 0x0000FF00) >> 8;
        int red = (color & 0x00FF0000) >> 16;
        int gray = Math.round(red * 0.299f + green * 0.587f + blue * 0.114f);
        return Color.argb(0xff, gray, gray, gray);
    }

    /**
     *
     * 降低明亮度10%
     *
     * @param color
     * @return
     */
    private int darker(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] -= 0.1f;
        return Color.HSVToColor(hsv);
    }

    private ColorStateList createColorStateList(int normal, int pressed) {
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {}};
        int[] colors = new int[]{pressed, normal};
        return new ColorStateList(states, colors);
    }
}
