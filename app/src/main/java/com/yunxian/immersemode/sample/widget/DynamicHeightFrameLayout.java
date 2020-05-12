package com.yunxian.immersemode.sample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.TintTypedArray;

import com.yunxian.immersemode.sample.R;

/**
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 17/1/27 下午9:14
 */
public class DynamicHeightFrameLayout extends FrameLayout {

    private float mHeightWidthRatio = 0;

    public DynamicHeightFrameLayout(Context context) {
        this(context, null);
    }

    public DynamicHeightFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicHeightFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.DynamicHeightView, defStyleAttr, defStyleRes);
        mHeightWidthRatio = Math.max(a.getFloat(R.styleable.DynamicHeightView_heightWidthRatio, 0), 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Float.compare(mHeightWidthRatio, 0) == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int newMeaHeight = (int) (getMeasuredWidth() * mHeightWidthRatio);
            int newMeasureSpec = MeasureSpec.makeMeasureSpec(newMeaHeight, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, newMeasureSpec);
        }
    }

    /**
     * 返回高宽比
     *
     * @return 高宽比，0为默认宽高，非0正数为动态设置高度
     */
    public float getHeightWidthRatio() {
        return mHeightWidthRatio;
    }

    /**
     * 设置新的的高宽比
     * <p>如果高宽比为0表示不动态设置高度，否则动态设置高度</p>
     *
     * @param heightWidthRatio 新的高宽比，不可为负数
     * @return 如果新的高宽比和旧的相同，则返回false，否则返回true，表示采纳了新的高宽比
     */
    public boolean setHeightWidthRatio(float heightWidthRatio) {
        heightWidthRatio = Math.max(heightWidthRatio, 0);
        if (Float.compare(this.mHeightWidthRatio, heightWidthRatio) != 0) {
            this.mHeightWidthRatio = heightWidthRatio;
            requestLayout();
            return true;
        }
        return false;
    }

}
