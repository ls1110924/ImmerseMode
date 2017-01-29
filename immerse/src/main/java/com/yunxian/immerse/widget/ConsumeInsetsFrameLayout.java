package com.yunxian.immerse.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.yunxian.immerse.R;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;


/**
 * 额外处理Insets事件的控件，用于解决开启状态栏沉浸时，adjustResize模式下软键盘弹出错误的问题
 *
 * @author AShuai
 * @email ls1110924@gmail.com
 * @date 2017/1/29 12:42
 */
public class ConsumeInsetsFrameLayout extends FrameLayout {

    private boolean mConsumeInsets = false;

    private final Rect mTempInsets = new Rect();

    private final List<OnInsetsChangeListener> mOnInsetsChangeListeners = new ArrayList<>();

    public ConsumeInsetsFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public ConsumeInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConsumeInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.immerse_ConsumeInsetsLayout, defStyleAttr, defStyleRes);
        mConsumeInsets = a.getBoolean(R.styleable.immerse_ConsumeInsetsLayout_immerse_consumeInsets, false);
        a.recycle();
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (mConsumeInsets && SDK_INT >= KITKAT) {
            mTempInsets.left = insets.left;
            mTempInsets.right = insets.right;
            mTempInsets.top = insets.top;
            mTempInsets.bottom = insets.bottom;

            // Intentionally do not modify the bottom inset. For some reason,
            // if the bottom inset is modified, window resizing stops working.
            // maybe: insets.bottom has value(eg. Rect(0, 50 - 0, 597)) when keyboard is showing.
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;

            for (OnInsetsChangeListener listener : mOnInsetsChangeListeners) {
                listener.onInsetsChanged(mTempInsets);
            }
        }
        return super.fitSystemWindows(insets);
    }

    /**
     * 设置是否消费Insets事件。
     * <p>沉浸Activity中包含EditText时，请设置为true，否则为false</p>
     * <p>一个Activity只包含一个fitSystemWindows为ture的控件，因此别的控件再行设置无效。
     * 需使用{@link OnInsetsChangeListener}进行监听</p>
     *
     * @param consumeInsets true为消费，否则不消费
     */
    public void setConsumeInsets(boolean consumeInsets) {
        if (mConsumeInsets != consumeInsets) {
            mConsumeInsets = consumeInsets;
            if (SDK_INT >= KITKAT) {
                setFitsSystemWindows(consumeInsets);
            }
            requestLayout();
        }
    }

    public void addOnInsetsChangeListener(@Nullable OnInsetsChangeListener listener) {
        if (listener != null && !mOnInsetsChangeListeners.contains(listener)) {
            mOnInsetsChangeListeners.add(listener);
        }
    }

    public void removeOnInsetsChangeListener(@Nullable OnInsetsChangeListener listener) {
        if (listener != null && mOnInsetsChangeListeners.contains(listener)) {
            mOnInsetsChangeListeners.remove(listener);
        }
    }

    public interface OnInsetsChangeListener {

        void onInsetsChanged(Rect insets);

    }

}
